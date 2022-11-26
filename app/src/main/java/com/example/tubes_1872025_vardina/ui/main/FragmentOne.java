package com.example.tubes_1872025_vardina.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubes_1872025_vardina.DetailActivity;
import com.example.tubes_1872025_vardina.FragmentSearch;
import com.example.tubes_1872025_vardina.R;
import com.example.tubes_1872025_vardina.adapter.MovieAdapter;
import com.example.tubes_1872025_vardina.databinding.FragmentMainBinding;
import com.example.tubes_1872025_vardina.entity.Movie;
import com.example.tubes_1872025_vardina.entity.MovieResponse;
import com.example.tubes_1872025_vardina.util.ExtraUtil;
import com.google.gson.Gson;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentOne extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    private ArrayList<Movie> movies = new ArrayList<>();
    MovieAdapter movieAdapter = new MovieAdapter(movies,movie->{
        movieDetail(movie);
    });

    public static FragmentOne newInstance(int index) {
        FragmentOne fragment = new FragmentOne();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


    }

    @Override
    public void onResume() {
        super.onResume();
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        switch (index){
            case 1:
                loadMoviesNow(movies,movieAdapter);
                break;
            case 2:
                loadMoviesUpcoming(movies,movieAdapter);
                break;
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        loadMovies(movies,movieAdapter);
        binding.rvMovie.setAdapter(movieAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), manager.getOrientation());
        binding.rvMovie.setLayoutManager(manager);
        binding.rvMovie.addItemDecoration(decoration);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public void searchFragment(){
//        Fragment fragment = new FragmentSearch();
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.view_pager,fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//    public void viewFragment(){
//        Fragment fragment = new FragmentOne();
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.view_pager,fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    public void movieDetail(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putString(ExtraUtil.TITLE,movie.getTitle());
        bundle.putString(ExtraUtil.DATE,movie.getDate());
        bundle.putString(ExtraUtil.DESC,movie.getOverview());
        bundle.putString(ExtraUtil.POSTER,movie.getPoster());
        bundle.putDouble(ExtraUtil.RATING,movie.getRating());
        Intent intent= new Intent(getActivity().getApplicationContext(), DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void loadMovies(List<Movie> data, MovieAdapter ma) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/popular").buildUpon()
                .appendQueryParameter("api_key", "bbc4098ec459cf75e461c67621c8d389")
                .build();
        StringRequest stringrequest = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            Gson gson = new Gson();
            MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
            data.clear();
            data.addAll(movieResponse.getMovies());
            ma.notifyDataSetChanged();
        }, error -> {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }) ;
        requestQueue.add(stringrequest);
    }

    private void loadMoviesNow(List<Movie> data, MovieAdapter ma) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/now_playing").buildUpon()
                .appendQueryParameter("api_key", "bbc4098ec459cf75e461c67621c8d389")
                .appendQueryParameter("region", "id")
                .build();
        StringRequest stringrequest = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            Gson gson = new Gson();
            MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
            data.clear();
            data.addAll(movieResponse.getMovies());
            ma.notifyDataSetChanged();
        }, error -> {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }) ;
        requestQueue.add(stringrequest);
    }

    private void loadMoviesUpcoming(List<Movie> data, MovieAdapter ma) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/upcoming").buildUpon()
                .appendQueryParameter("api_key", "bbc4098ec459cf75e461c67621c8d389")
                .build();
        StringRequest stringrequest = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            Gson gson = new Gson();
            MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);
            data.clear();
            data.addAll(movieResponse.getMovies());
            ma.notifyDataSetChanged();
        }, error -> {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }) ;
        requestQueue.add(stringrequest);
    }



}