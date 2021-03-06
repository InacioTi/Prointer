package com.faculdade.faculdade.RecycleView.activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.adapter.CardAdapter;
import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import com.faculdade.faculdade.RecycleView.adapter.TarefaCardAdater;
import com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.RecycleView.model.UserModel;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;
import com.faculdade.faculdade.RecycleView.repository.UserLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridLayoutCardAdpter extends AppCompatActivity {

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

        //verifica se come??ou agora ou se veio de uma edi????o
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
            EditText txtData = (EditText) this.findViewById(R.id.txtData);
            EditText  txtHorarioInicial = (EditText) this.findViewById(R.id.txtHorarioInicial);

            txtTitulo.setText(TarefaEditado.getTitulo());
            txtDesc.setText(TarefaEditado.getDesc());
            chkVip.setChecked(TarefaEditado.getVip());
            spnSemestre.setSelection(getIndex(spnSemestre, TarefaEditado.getSemestre()));
            txtData.setText(TarefaEditado.getData());
            txtHorarioInicial.setText(TarefaEditado.getHorainicio());


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
                EditText txtData = (EditText) findViewById(R.id.txtData);
                EditText  txtHorarioInicial = (EditText)findViewById(R.id.txtHorarioInicial);


                //pegando os valores
                String titulo = txtTitulo.getText().toString();
                String descri = txtDesc.getText().toString();
                String semestre = spnSemestre.getSelectedItem().toString();
                boolean vip = chkVip.isChecked();
                String professor = rgProfessor.getCheckedRadioButtonId() == R.id.rbProfessor ? "P" : "A";
                String data = txtData.getText().toString();
                String horainicio = txtHorarioInicial.getText().toString();

                //salvando os dados
                TarefaDAO dao = new TarefaDAO(getBaseContext());
                boolean sucesso;
                if(TarefaEditado != null)
                    sucesso = dao.salvar(TarefaEditado.getId(), titulo, descri, professor, semestre, vip, data, horainicio);
                else
                    sucesso = dao.salvar(titulo, descri,professor, semestre, vip, data, horainicio);

                if(sucesso) {
                    Tarefa tarefa = dao.retornarUltimo();
                    if(TarefaEditado != null)
                        adapter.atualizarTarefa(tarefa);
                    else
                        adapter.adicionarTarefa(tarefa);

                    //limpa os campos
                    TarefaEditado = null;
                    txtTitulo.setText("");
                    txtDesc.setText("");
                    txtData.setText(null);
                    txtHorarioInicial.setText(null);
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
    private TarefaCardAdater adapter;

    private void configurarRecycler() {

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que ir?? anexar os objetos ?? lista.
        TarefaDAO dao = new TarefaDAO(this);
        adapter = new TarefaCardAdater(dao.retornarTodos());
        recyclerView.setAdapter(adapter);

        // Configurando um separador entre linhas, para uma melhor visualiza????o.
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
