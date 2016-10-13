package com.nguyen.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.flickster.models.Genres;
import com.nguyen.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by My on 10/12/2016.
 */

public class DetailActivity extends AppCompatActivity {
   @BindView(R.id.image) ImageView image;
   @BindView(R.id.title) TextView title;
   @BindView(R.id.vote_average) TextView voteAverage;
   @BindView(R.id.genres) TextView genres;
   @BindView(R.id.overview) TextView overview;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);

      ButterKnife.bind(this);

      final Movie movie = (Movie)getIntent().getSerializableExtra("MOVIE_IN");

      Picasso.with(this).load(movie.posterPath).placeholder(R.drawable.homer)
            .transform(new RoundedCornersTransformation(10, 10)).into(image);

      image.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(DetailActivity.this, YouTubePlayerActivity.class);
            intent.putExtra("ID_IN", movie.id);
            startActivity(intent);
         }
      });

      String year = movie.releaseDate.split("-")[0];
      String text = movie.originalTitle + " (" + year + ")";
      title.setText(text);

      String starCount = String.format("%.1f", movie.voteAverage);
      voteAverage.setText(starCount);

      String genresString = Genres.getInstance().get(movie.genreIds.get(0));
      for (int i = 1; i < movie.genreIds.size(); i++)
         genresString += ", " + Genres.getInstance().get(movie.genreIds.get(i));
      genres.setText(genresString);

      overview.setText(movie.overview);
   }
}
