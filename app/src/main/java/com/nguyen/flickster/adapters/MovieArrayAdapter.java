package com.nguyen.flickster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.flickster.R;
import com.nguyen.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by My on 10/11/2016.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
   public MovieArrayAdapter(Context context, List<Movie> movies) {
      super(context, android.R.layout.simple_list_item_1, movies);
   }

   @NonNull
   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      // get the data item for this position
      Movie movie = getItem(position);

      // check if the existing view is being reused
      if (convertView == null) {
         LayoutInflater inflater = LayoutInflater.from(getContext());
         convertView = inflater.inflate(R.layout.item_movie, parent, false);
      }

      // find the image view
      ImageView image = (ImageView)convertView.findViewById(R.id.image);
      // clear out image from convertView
      image.setImageResource(0);

      TextView title = (TextView)convertView.findViewById(R.id.title);
      TextView overview = (TextView)convertView.findViewById(R.id.overview);

      // populate data
      Picasso.with(getContext()).load(movie.posterPath).into(image);
      title.setText(movie.originalTitle);
      overview.setText(movie.overview);

      // return the view
      return convertView;
   }
}
