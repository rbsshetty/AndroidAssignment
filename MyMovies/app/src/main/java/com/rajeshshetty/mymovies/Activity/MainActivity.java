package com.rajeshshetty.mymovies.Activity;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajeshshetty.mymovies.Adapters.MoviesAdapter;
import com.rajeshshetty.mymovies.Model.Movies;
import com.rajeshshetty.mymovies.Network.APIManager;
import com.rajeshshetty.mymovies.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.img_network_error)ImageView placeholderImageView;
    @BindView(R.id.network_error)TextView placeholderTextView;

    private MoviesAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar
        );
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle(getString(R.string.activity_moview_title));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        mRecyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerview.setLayoutManager(linearLayoutManager);

        getMovieList();

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieList();
            }
        });
    }

    private void  getMovieList(){

        if (APIManager.getInstance(getApplicationContext()).isNetworkAvailble()) {

            mRecyclerview.setVisibility(View.VISIBLE);
            setPlaceHolderVisibility(View.INVISIBLE);
            mSwipeRefresh.setRefreshing(true);

            APIManager.getInstance(getApplicationContext()).getMovieList(new APIManager.Callback<ArrayList<Movies>>() {
                @Override
                public void onSuccess(ArrayList<Movies> param) {

                    mSwipeRefresh.setRefreshing(false);
                    mMovieAdapter = new MoviesAdapter(MainActivity.this, param);
                    mRecyclerview.setAdapter(mMovieAdapter);

                    if(param.size()>0){
                        setPlaceHolderVisibility(View.INVISIBLE);
                    }else{
                        setPlaceHolderVisibility(View.VISIBLE);
                        mRecyclerview.setVisibility(View.INVISIBLE);
                        setPlaceHolderValue(getString(R.string.no_content),getResources().getDrawable(R.drawable.no_content));
                    }

                }

                @Override
                public void onFailure(JSONObject param) {
                    mRecyclerview.setVisibility(View.INVISIBLE);
                    setPlaceHolderVisibility(View.VISIBLE);
                    setPlaceHolderValue(getString(R.string.server_error),getResources().getDrawable(R.drawable.network));
                }
            });

        }else {
            mRecyclerview.setVisibility(View.INVISIBLE);
            setPlaceHolderVisibility(View.VISIBLE);
            setPlaceHolderValue(getString(R.string.no_network),getResources().getDrawable(R.drawable.network));
        }

    }

    public void setPlaceHolderVisibility(int value){
        placeholderTextView.setVisibility(value);
        placeholderImageView.setVisibility(value);

    }

    public void setPlaceHolderValue(String Text, Drawable drawable){
        placeholderTextView.setText(Text);
        placeholderImageView.setImageDrawable(drawable);
    }
}
