package com.example.tubes_1872025_vardina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.tubes_1872025_vardina.databinding.ActivityDetailBinding;
import com.example.tubes_1872025_vardina.util.ExtraUtil;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null&&!bundle.isEmpty()){
            String title = bundle.getString(ExtraUtil.TITLE);
            String desc = bundle.getString(ExtraUtil.DESC);
            String date = bundle.getString(ExtraUtil.DATE);
            String poster = bundle.getString(ExtraUtil.POSTER);
            double rating = bundle.getDouble(ExtraUtil.RATING);

            binding.textView.setText(title);
            binding.tvDesc.setText(desc);
            binding.tvDatedetail.setText(date);
            Glide.with(binding.getRoot())
                    .load("https://image.tmdb.org/t/p/w185" + poster)
                    .into(binding.imageView);
            binding.ratingBar.setRating((float) rating);
        }
    }
}