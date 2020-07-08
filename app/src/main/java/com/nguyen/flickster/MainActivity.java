package com.nguyen.flickster;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyen.flickster.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    MainViewModel mainViewModel;
    @Inject
    MovieRepository repository;

    private List<Movie> movies;
    private MoviesAdapter adapter;
    private ActivityMainBinding binding;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MyApplication)getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(movies, this);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayout);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchMovies();
            }
        };
        binding.recyclerView.addOnScrollListener(scrollListener);

        // first fetch the genres, then upon success, fetch the first page of movies
        fetchGenres();
    }

    void fetchGenres() {
        mainViewModel.getGenres().observe(this, new Observer<Map<Integer, String>>() {
            @Override
            public void onChanged(Map<Integer, String> map) {
                fetchMovies();
            }
        });
    }

    private void fetchMovies() {
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> data) {
                int size = movies.size();
                movies.addAll(data);
                adapter.notifyItemRangeInserted(size, data.size());
            }
        });
    }
}
