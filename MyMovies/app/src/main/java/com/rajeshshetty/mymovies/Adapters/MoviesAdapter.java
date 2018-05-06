package com.rajeshshetty.mymovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rajeshshetty.mymovies.Activity.DetailsActivity;
import com.rajeshshetty.mymovies.AppConstants;
import com.rajeshshetty.mymovies.GlideApp;
import com.rajeshshetty.mymovies.Model.Movies;
import com.rajeshshetty.mymovies.R;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_MOVIE = 1;
    private ArrayList<Movies> moviesList;
    private Context context;

    public MoviesAdapter(Context context, ArrayList<Movies> movies_list){
        this.context = context;
        this.moviesList = movies_list;

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePosterImageView;
        TextView moviewTitle;
        RatingBar mRatingBar;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.moviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_poster_imageview);
            this.moviewTitle = (TextView) itemView.findViewById(R.id.movie_title);
            this.mRatingBar=itemView.findViewById(R.id.ratingbar);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ITEM_TYPE_MOVIE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
                holder = new MovieViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {
        MovieViewHolder movieViewHolder=(MovieViewHolder)holder;

        movieViewHolder.moviewTitle.setText(moviesList.get(position).getTitle());
        GlideApp.with(context).load(AppConstants.IMAGE_BASE_URL_W780+moviesList.get(position).getPoster_path()).into(movieViewHolder.moviePosterImageView);
        movieViewHolder.mRatingBar.setRating(moviesList.get(position).getVote_average()/2);

        movieViewHolder.moviePosterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE_KEY,moviesList.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public int getItemViewType(int position) {
        return ITEM_TYPE_MOVIE;
    }
}
