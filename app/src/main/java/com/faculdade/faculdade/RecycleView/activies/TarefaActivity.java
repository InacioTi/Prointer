package com.faculdade.faculdade.RecycleView.activies;

import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import  com.faculdade.faculdade.RecycleView.repository.Tarefa;
import  com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.Login.activities.LoginActivity;
import com.faculdade.faculdade.Login.adapter.UsersRecyclerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.faculdade.faculdade.R;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class TarefaActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_tarefas);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //carregando os campos

                EditText txtTitulo = (EditText)findViewById(R.id.txtTitulo);
                EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
                Spinner spnSemestre = (Spinner)findViewById(R.id.spnSemestre);
                RadioGroup rgProfessor = (RadioGroup)findViewById(R.id.rgProfessor);
                CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);

                //pegando os valores
                String titulo = txtTitulo.getText().toString();
                String descri = txtDesc.getText().toString();
                String semestre = spnSemestre.getSelectedItem().toString();
                boolean vip = chkVip.isChecked();
                String professor = rgProfessor.getCheckedRadioButtonId() == R.id.rbProfessor ? "Professor" : "Aluno";

                //salvando os dados
                TarefaDAO dao = new TarefaDAO(getBaseContext());
                boolean sucesso;
                if(TarefaEditado != null)
                    sucesso = dao.salvar(TarefaEditado.getId(), titulo, descri  , professor, semestre, vip);
                else
                    sucesso = dao.salvar(titulo, descri , professor, semestre, vip);

                if(sucesso) {
                    Tarefa tarefa = dao.retornarUltimo();
                    if(TarefaEditado != null)
                        adapter.atualizarTarefa(tarefa);
                    else
                        adapter.adicionarTarefa(tarefa);


                    //alerta ao alterar o recycle view
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Alteracao");
                    builder.setMessage("Dados alterado por : " + professor);
                    builder.show();
                    //alerta ao alterar o recycle view


                    //limpa os campos
                    TarefaEditado = null;
                    txtTitulo.setText("");
                    txtDesc.setText("");
                    rgProfessor.setSelected(false);
                    spnSemestre.setSelection(0);
                    chkVip.setChecked(false);

                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);


                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        configurarRecycler();
    }

    RecyclerView recyclerView;
    private TarefaAdapter adapter;

    private void configurarRecycler() {

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        TarefaDAO dao = new TarefaDAO(this);
        adapter = new TarefaAdapter(dao.retornarTodos());
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
