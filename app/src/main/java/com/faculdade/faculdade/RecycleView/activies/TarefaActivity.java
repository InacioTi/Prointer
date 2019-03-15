package com.faculdade.faculdade.RecycleView.activies;

import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import  com.faculdade.faculdade.RecycleView.repository.Tarefa;
import  com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.Login.activities.LoginActivity;
import com.faculdade.faculdade.Login.adapter.UsersRecyclerAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TarefaActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    Button chooseImg;
    ImageView imgView;
    //Button down;

    int PICK_IMAGE_REQUEST = 1011;
    Uri filePath;
    ProgressDialog pd;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://faculdade-e089d.appspot.com");    //change the url according to your firebase app


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


        chooseImg = (Button)findViewById(R.id.btnAnexar);      //choose button
        imgView = (ImageView)findViewById(R.id.imageView3);
        //down=(Button)findViewById(R.id.download); //image view

        chooseImg.setOnClickListener(new View.OnClickListener() {       //Image Selection start
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

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
                if(filePath != null) {
                    pd.setMax(100);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.show();

                    StorageReference childRef = storageRef.child(filePath.getLastPathSegment());

                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(TarefaActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(TarefaActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress =(100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();                // for displaying the upload percentage in progress bar.
                                    pd.setMessage(((int)progress) + "% Uploaded..");
                                }
                            });
                }
                else {
                    Toast.makeText(TarefaActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }


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



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();


            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);            //getting image from gallery


                imgView.setImageBitmap(bitmap);                                                               //Setting image to ImageView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }
    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

}
