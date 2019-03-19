package com.faculdade.faculdade.RecycleView.activies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.faculdade.faculdade.Login.activities.UsersListActivity;
import com.faculdade.faculdade.Login.adapter.UsersRecyclerAdapter;
import com.faculdade.faculdade.Login.model.User;
import com.faculdade.faculdade.Login.sql.DatabaseHelper;
import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;
import com.faculdade.faculdade.RecycleView.sql.DbHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        if (intent != null){

            Bundle params = intent.getExtras();

            if(params != null){
                String title = params.getString("Titulo");
                String disc = params.getString("Desc");

                TextView txt_title_meta = (TextView) findViewById(R.id.txt_title_meta);
                TextView txt_size_meta = (TextView) findViewById(R.id.txt_size_meta);
                TextView   txt_date_create_meta = (TextView) findViewById(R.id.txt_date_create_meta);

                txt_title_meta.setText(title);
                txt_size_meta.setText(disc);

                String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
                txt_date_create_meta.setText(currentDateTimeString);
            }

        }

    }
}