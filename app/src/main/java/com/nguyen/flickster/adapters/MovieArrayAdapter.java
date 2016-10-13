package com.nguyen.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.flickster.R;
import com.nguyen.flickster.models.Genres;
import com.nguyen.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by My on 10/11/2016.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
   static Context sContext;

   // view lookup cache
   static class ViewHolder {
      @BindView(R.id.image) ImageView image;
      @BindView(R.id.play) ImageView play;
      @BindView(R.id.title) TextView title;
      @BindView(R.id.vote_average) TextView voteAverage;
      @BindView(R.id.genres) TextView genres;
      @BindView(R.id.release_date) TextView releaseDate;

      public ViewHolder(View view) {
         ButterKnife.bind(this, view);
      }

      void populate(Movie movie) {
         int widthPixels = sContext.getResources().getDisplayMetrics().widthPixels;
         Picasso.with(sContext).load(movie.backdropPath).resize(widthPixels, 0).placeholder(R.drawable.homer).into(image);

         play.setVisibility(movie.voteAverage >= 6.0 ? View.VISIBLE : View.GONE);

         title.setText(movie.originalTitle);

         String starCount = String.format("%.1f", movie.voteAverage);
         voteAverage.setText(starCount);

         String genresString = Genres.getInstance().get(movie.genreIds.get(0));
         for (int i = 1; i < movie.genreIds.size(); i++)
            genresString += ", " + Genres.getInstance().get(movie.genreIds.get(i));
         genres.setText(genresString);

         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         try {
            Date date = format.parse(movie.releaseDate);
            format = new SimpleDateFormat("MM/dd/yyyy");
            String text = format.format(date);
            releaseDate.setText(text);
         } catch (ParseException e) {
            e.printStackTrace();
         }
      }
   }

   public MovieArrayAdapter(Context context, List<Movie> movies) {
      super(context, android.R.layout.simple_list_item_1, movies);
      sContext = context;
   }

   @NonNull
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      // get the data item for this position
      Movie movie = getItem(position);

      // check if an existing view is being reused
      ViewHolder holder;
      if (convertView == null) {
         // if there's no view to reuse, inflate a brand new view for row
         LayoutInflater inflater = LayoutInflater.from(getContext());
         convertView = inflater.inflate(R.layout.item_movie, parent, false);

         holder = new ViewHolder(convertView);

         // cache the ViewHolder object inside the fresh view
         convertView.setTag(holder);
      } else {
         // view is being recycled, so retrieve the ViewHolder object from tag
         holder = (ViewHolder)convertView.getTag();
         // clear out image from convertView
         holder.image.setImageResource(0);
      }

      // populate data
      holder.populate(movie);

      // return the view
      return convertView;
   }
}
