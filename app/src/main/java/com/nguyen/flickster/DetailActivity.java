package com.nguyen.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.nguyen.flickster.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.nguyen.flickster.YoutubeActivity.EXTRA_MOVIE_ID;

/**
 * Created by My on 10/12/2016.
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_OBJECT = "MOVIE_OBJECT";

    private static final String TAG = "DetailActivity";

    @Inject
    MovieRepository repository;

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApplication)getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        final Movie movie = (Movie)getIntent().getSerializableExtra(EXTRA_MOVIE_OBJECT);
        Log.d(TAG, movie.toString());

        // showImageUsingObserver(movie);
        showImageUsingFixedWidth(movie);

        binding.detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, YoutubeActivity.class);
                intent.putExtra(EXTRA_MOVIE_ID, movie.id);
                startActivity(intent);
            }
        });

        // extract release year and append it to title
        String year = movie.releaseDate.split("-")[0];
        String text = movie.title + " (" + year + ")";
        binding.detailTitle.setText(text);

        // show vote average together with a star
        String starCount = String.format("%.1f", movie.voteAverage);
        binding.detailVoteAverage.setText(starCount);

        // list all genres, separated by commas
        String genres = repository.genres.get(movie.genreIds.get(0));
        for (int i = 1; i < movie.genreIds.size(); i++)
            genres += ", " + repository.genres.get(movie.genreIds.get(i));
        binding.detailGenres.setText(genres);

        // show the overview text
        binding.detailOverview.setText(movie.overview);
    }

    private void showImageUsingObserver(Movie movie) {
        ViewTreeObserver observer = binding.detailImage.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.detailImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width  = binding.detailImage.getMeasuredWidth();
                Picasso.get()
                        .load(movie.IMAGE_PREFIX + movie.posterPath)
                        .resize(width, 0)
                        .placeholder(R.drawable.homer)
                        .into(binding.detailImage);
            }
        });
    }

    private void showImageUsingFixedWidth(Movie movie) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        Picasso.get()
                .load(movie.IMAGE_PREFIX + movie.posterPath)
                .resize(widthPixels / 2, 0)
                .placeholder(R.drawable.homer)
                .into(binding.detailImage);
    }
}
