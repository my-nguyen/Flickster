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
   static Context context;

   // view lookup cache
   private static class ViewHolder {
      ImageView image;
      TextView title;
      TextView overview;

      public ViewHolder(View view) {
         image = (ImageView)view.findViewById(R.id.image);
         title = (TextView)view.findViewById(R.id.title);
         overview = (TextView)view.findViewById(R.id.overview);
      }

      void populate(Movie movie) {
         Picasso.with(context).load(movie.posterPath).into(image);
         title.setText(movie.originalTitle);
         overview.setText(movie.overview);
      }
   }

   public MovieArrayAdapter(Context context, List<Movie> movies) {
      super(context, android.R.layout.simple_list_item_1, movies);
      this.context = context;
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
