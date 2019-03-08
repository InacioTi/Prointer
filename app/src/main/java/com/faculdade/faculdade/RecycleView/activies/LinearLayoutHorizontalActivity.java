package com.faculdade.faculdade.RecycleView.activies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.model.UserModel;
import com.faculdade.faculdade.RecycleView.adapter.CardAdapter;
import com.faculdade.faculdade.RecycleView.repository.UserLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinearLayoutHorizontalActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.recycler_view_layour_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view_layour_fab)
    FloatingActionButton mFab;

    @BindView(R.id.recycler_view_layour_recycler)
    RecyclerView mRecyclerView;

    private CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_layout);

        ButterKnife.bind(this);

        setupView();
        setupRecycler();
    }

    private void setupView() {
        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        UserLoader
                .fetch()
                .subscribe(o -> mAdapter.updateList((UserModel) o));
    }

    private void setupRecycler() {

        // Para criar um layout de uma lista.
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        mAdapter = new CardAdapter(new ArrayList<>(0));
        mRecyclerView.setAdapter(mAdapter);
    }
}
