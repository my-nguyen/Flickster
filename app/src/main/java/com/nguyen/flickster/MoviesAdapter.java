package com.nguyen.flickster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.nguyen.flickster.databinding.ItemMovieBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.nguyen.flickster.DetailActivity.EXTRA_MOVIE_OBJECT;
import static com.nguyen.flickster.YoutubeActivity.EXTRA_MOVIE_ID;

/**
 * Created by My on 10/11/2016
 * Updated by My on 6/30/2020:
 * 1. migrated to AndroidX
 * 2. replaced ListView with RecyclerView
 * 3. replaced ButterKnife with data binding
 * 4. replaced Volley with Retrofit
 * 5. added EndlessRecyclerViewScrollListener
 * 6. replaced LinearLayout and RelativeLayout with ConstraintLayout
 * 7. implemented MVVM
 * 8. partially implemented Dagger DI
 * 9. removed SwipeRefreshLayout from MainActivity
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";
        private static final float VOTE_THRESHOLD = 7.5f;

        private ItemMovieBinding binding;

        public ViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            JsonMovie movie = movies.get(getAdapterPosition());
            if (movie.voteAverage >= VOTE_THRESHOLD) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra(EXTRA_MOVIE_ID, movie.id);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE_OBJECT, movie);
                context.startActivity(intent);
            }
        }

        void bind(JsonMovie movie) {
            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            Picasso.get()
                    .load(movie.IMAGE_PREFIX + movie.backdropPath)
                    .resize(widthPixels, 0)
                    .placeholder(R.drawable.homer)
                    .into(binding.itemImage);

            binding.itemPlayButton.setVisibility(movie.voteAverage >= VOTE_THRESHOLD ? View.VISIBLE : View.GONE);

            binding.itemTitle.setText(movie.title);

            String starCount = String.format("%.1f", movie.voteAverage);
            binding.itemVoteAverage.setText(starCount);

            String genresString = repository.genres.get(movie.genreIds.get(0));
            for (int i = 1; i < movie.genreIds.size(); i++)
                genresString += ", " + repository.genres.get(movie.genreIds.get(i));
            binding.itemGenres.setText(genresString);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(movie.releaseDate);
                format = new SimpleDateFormat("MM/dd/yyyy");
                String text = format.format(date);
                binding.itemReleaseDate.setText(text);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Inject
    MovieRepository repository;

    private List<JsonMovie> movies;
    private Context context;

    @Inject
    public MoviesAdapter(List<JsonMovie> movies, Context context) {
        this.movies = movies;
        this.context = context;
        ((MyApplication)context.getApplicationContext()).appComponent.inject(this);
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        JsonMovie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
