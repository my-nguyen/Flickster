package com.nguyen.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.flickster.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by My on 10/12/2016.
 */

public class DetailActivity extends AppCompatActivity {
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);

      ImageView image = (ImageView)findViewById(R.id.image);
      TextView title = (TextView)findViewById(R.id.title);
      TextView synopsis = (TextView)findViewById(R.id.synopsis);
      TextView popularity = (TextView)findViewById(R.id.popularity);

      final Movie movie = (Movie)getIntent().getSerializableExtra("MOVIE_IN");
      Picasso.with(this).load(movie.posterPath).placeholder(R.drawable.homer).into(image);
      image.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(DetailActivity.this, YouTubePlayerActivity.class);
            intent.putExtra("ID_IN", movie.id);
            startActivity(intent);
         }
      });
      title.setText(movie.originalTitle);
      synopsis.setText(movie.overview);
      popularity.setText(movie.popularity + "");
   }
}
