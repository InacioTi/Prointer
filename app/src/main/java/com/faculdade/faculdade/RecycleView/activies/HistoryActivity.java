package com.faculdade.faculdade.RecycleView.activies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.faculdade.faculdade.Login.activities.UsersListActivity;
import com.faculdade.faculdade.Login.adapter.UsersRecyclerAdapter;
import com.faculdade.faculdade.Login.model.User;
import com.faculdade.faculdade.Login.sql.DatabaseHelper;
import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.adapter.HistoryAdapter;
import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import com.faculdade.faculdade.RecycleView.adapter.TarefaCardAdater;
import com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;
import com.faculdade.faculdade.RecycleView.sql.DbHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {
    Tarefa TarefaEditado = null;

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        if(intent.hasExtra("Tarefa")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            TarefaEditado = (Tarefa) intent.getSerializableExtra("Tarefa");
            EditText txtTitulo = (EditText)findViewById(R.id.txtTitulo);
            EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
            Spinner spnSemestre = (Spinner)findViewById(R.id.spnSemestre);
            CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);

            txtTitulo.setText(TarefaEditado.getTitulo());
            txtDesc.setText(TarefaEditado.getDesc());
            chkVip.setChecked(TarefaEditado.getVip());
            spnSemestre.setSelection(getIndex(spnSemestre, TarefaEditado.getSemestre()));


            if(TarefaEditado.getProfessor() != null){
                RadioButton rb;
                if(TarefaEditado.getProfessor().equals("P"))
                    rb = (RadioButton)findViewById(R.id.rbProfessor);
                else
                    rb = (RadioButton)findViewById(R.id.rbAluno);
                rb.setChecked(true);
            }



        }


        configurarRecycler();
    }

    RecyclerView recyclerView;
    private HistoryAdapter adapter;

    private void configurarRecycler() {

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        TarefaDAO dao = new TarefaDAO(this);
        adapter = new HistoryAdapter(dao.retornarTodos());
        recyclerView.setAdapter(adapter);

        // Configurando um separador entre linhas, para uma melhor visualização.
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}