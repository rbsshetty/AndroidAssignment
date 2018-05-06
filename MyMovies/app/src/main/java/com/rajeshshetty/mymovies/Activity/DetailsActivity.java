package com.rajeshshetty.mymovies.Activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rajeshshetty.mymovies.AppConstants;
import com.rajeshshetty.mymovies.GlideApp;
import com.rajeshshetty.mymovies.Model.Movies;
import com.rajeshshetty.mymovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_KEY="movie_object";

    @BindView(R.id.poster_imageview)ImageView posterImageView;
    @BindView(R.id.title_textview)TextView titleTextView;
    @BindView(R.id.desc_textview)TextView descTextView;
    @BindView(R.id.vote_textview)TextView voteTextView;
    @BindView(R.id.date_textview)TextView dateTextView;
    @BindView(R.id.details_ratingbar)RatingBar ratingBar;


    private Movies movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("");


        movies=(Movies)getIntent().getParcelableExtra(MOVIE_KEY);

        GlideApp.with(getApplicationContext()).load(AppConstants.IMAGE_BASE_URL_W780+movies.getPoster_path()).into(posterImageView);
        titleTextView.setText(movies.getOriginal_title());
        descTextView.setText(movies.getOverview());
        voteTextView.setText(movies.getVote_count()+" votes");
        dateTextView.setText("Released: "+movies.getRelease_date());
        ratingBar.setRating(movies.getVote_average()/2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
