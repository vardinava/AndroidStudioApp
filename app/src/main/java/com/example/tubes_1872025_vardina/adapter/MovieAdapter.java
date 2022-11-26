package com.example.tubes_1872025_vardina.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tubes_1872025_vardina.R;
import com.example.tubes_1872025_vardina.databinding.MovieItemBinding;
import com.example.tubes_1872025_vardina.entity.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private ArrayList<Movie> movies;
    private ItemClick itemClick;

    public MovieAdapter(ArrayList<Movie> movies,ItemClick itemClick) {
        this.movies = movies;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.setMovieData(movies.get(position));
        holder.itemView.setOnClickListener(view -> itemClick.movieDataClicked(movies.get(position)));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{
        private MovieItemBinding binding;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MovieItemBinding.bind(itemView);
        }
        public void setMovieData(Movie movie){
            binding.tvTitle.setText(movie.getTitle());
            binding.tvDate.setText(movie.getDate());
            binding.tvInfo.setText(movie.getOverview());
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w185" + movie.getPoster())
                    .into(binding.ivMovie);
        }
    }

    public interface ItemClick{
        void movieDataClicked(Movie movie);
    }
}
