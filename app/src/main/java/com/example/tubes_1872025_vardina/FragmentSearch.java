package com.example.tubes_1872025_vardina;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubes_1872025_vardina.adapter.MovieAdapter;
import com.example.tubes_1872025_vardina.databinding.FragmentSearchBinding;
import com.example.tubes_1872025_vardina.entity.Movie;
import com.example.tubes_1872025_vardina.entity.MovieResponse;
import com.example.tubes_1872025_vardina.ui.main.FragmentOne;
import com.example.tubes_1872025_vardina.util.ExtraUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentSearchBinding binding;
    private ArrayList<Movie> movies = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSearch() {
        // Required empty public constructor
    }

    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View root = binding.getRoot();


        MovieAdapter ma = new MovieAdapter(movies,movie->{
            movieDetail(movie);
        });
        binding.button.setOnClickListener(view -> {
            String query = binding.etQuery.getText().toString();
            loadResult(movies,ma,query);
        });

        binding.rvResult.setAdapter(ma);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), manager.getOrientation());
        binding.rvResult.setLayoutManager(manager);
        binding.rvResult.addItemDecoration(decoration);
        return root;
    }

    private void loadResult(List<Movie> data, MovieAdapter ma, String q) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Uri uri = Uri.parse("https://api.themoviedb.org/3/search/movie").buildUpon()
                .appendQueryParameter("api_key", "bbc4098ec459cf75e461c67621c8d389")
                .appendQueryParameter("query", q)
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
}