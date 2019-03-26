package com.faculdade.faculdade.RecycleView.activies;

import com.faculdade.faculdade.RecycleView.adapter.TarefaAdapter;
import  com.faculdade.faculdade.RecycleView.repository.Tarefa;
import  com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.Login.activities.LoginActivity;
import com.faculdade.faculdade.Login.adapter.UsersRecyclerAdapter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.provider.DocumentFile;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class TarefaActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialogDataLocacao;
    TimePickerDialog timePickerDialogHorarioInicial;
    EditText txtData, txtHorarioInicial;

    private ProgressDialog progressDialog;

    Button chooseImg;
    ImageView imgView;
    //Button down;
    int PICK_IMAGE_REQUEST = 1011;
    int PDF = 0;
    Uri filePath;
    ProgressDialog pd;

    //creating reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://faculdade-e089d.appspot.com");    //altere o URL de acordo com seu aplicativo firebase

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

//evento para pegar data e hora local
    public  void criarEventos() {

        txtData = (EditText) this.findViewById(R.id.txtData);
        txtHorarioInicial = (EditText) this.findViewById(R.id.txtHorarioInicial);

        final Calendar calendarDataAtual = Calendar.getInstance();
        int anoAtual = calendarDataAtual.get(Calendar.YEAR);
        int mesAtual = calendarDataAtual.get(Calendar.MONTH);
        int diaAtual = calendarDataAtual.get(Calendar.DAY_OF_MONTH);

        final Calendar horarioAtual = Calendar.getInstance();
        int horaAtual = horarioAtual.get(Calendar.HOUR_OF_DAY);
        int minutoAtual = horarioAtual.get(Calendar.MINUTE);

        //CRIANDO DATA
        datePickerDialogDataLocacao = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                String mes = (String.valueOf(mesSelecionado + 1).length() == 1 ? "0" + (mesSelecionado + 1) : String.valueOf(mesSelecionado));

                txtData.setText(diaSelecionado + "/" + mes + "/" + anoSelecionado);
            }
        }, anoAtual, mesAtual, diaAtual);

        txtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogDataLocacao.show();
            }
        });

        //CRIANDO HORÁRIOS INICIAL
        timePickerDialogHorarioInicial = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker arg0, int hora, int minutos) {

                txtHorarioInicial.setText(hora + ":" + minutos);
            }
        }, horaAtual, minutoAtual, true);

        txtHorarioInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogHorarioInicial.show();
            }
        });

    }

//evento para pegar data e hora local

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //evento para pegar data e hora local
        criarEventos();
        //evento para pegar data e hora local


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

        // escolhendo a pasta firebase
        RadioButton rdPasta1 = (RadioButton)findViewById(R.id.rdPasta1);
        RadioButton rdPasta2 = (RadioButton)findViewById(R.id.rdPasta2);

        chooseImg = (Button)findViewById(R.id.btnAnexar);      //butao de anexar
        imgView = (ImageView)findViewById(R.id.imageView3);
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setTitle("Upload Process");                              //progessobar
        // progress spinner
        //down=(Button)findViewById(R.id.download); //image view

        chooseImg.setOnClickListener(new View.OnClickListener() {       //Selecionar arquivo
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Arquivo"), PICK_IMAGE_REQUEST);

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

            //fazer upload do anexo
            @Override
            public void onClick(View view) {
                if(filePath != null) {
                    pd.setMax(100);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.show();

                        // escolhendo a pasta firebase
                        if(rdPasta1.isChecked()) {
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://faculdade-e089d.appspot.com/teste1");    //altere o URL de acordo com seu aplicativo firebase

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
                                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();                // for displaying the upload percentage in progress bar.
                                            pd.setMessage(((int) progress) + "% Uploaded..");
                                        }
                                    });
                        }
                                    else {
                                    Toast.makeText(TarefaActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                                    }


                                // escolhendo a pasta firebase
                                if (rdPasta2.isChecked()) {
                                    StorageReference storageRef = storage.getReferenceFromUrl("gs://faculdade-e089d.appspot.com/teste2");    //altere o URL de acordo com seu aplicativo firebase

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
                                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();                // for displaying the upload percentage in progress bar.
                                                    pd.setMessage(((int) progress) + "% Uploaded..");
                                                }
                                            });

                                } else {
                                    Toast.makeText(TarefaActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                                }
                }
                //fazer upload do anexo


                //carregando os campos
                EditText txtTitulo = (EditText)findViewById(R.id.txtTitulo);
                EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
                Spinner spnSemestre = (Spinner)findViewById(R.id.spnSemestre);
                RadioGroup rgProfessor = (RadioGroup)findViewById(R.id.rgProfessor);
                CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);
                TextView date = (TextView)findViewById(R.id.date);
                EditText txtData = (EditText) findViewById(R.id.txtData);
                EditText  txtHorarioInicial = (EditText)findViewById(R.id.txtHorarioInicial);
                //carregando os campos

                //pegando os valores
                String titulo = txtTitulo.getText().toString();
                String descri = txtDesc.getText().toString();
                String semestre = spnSemestre.getSelectedItem().toString();
                boolean vip = chkVip.isChecked();
                String professor = rgProfessor.getCheckedRadioButtonId() == R.id.rbProfessor ? "Professor" : "Aluno";
                String data = txtData.getText().toString();
                String horainicio = txtHorarioInicial.getText().toString();
                //pegando os valores

                //salvando os dados
                TarefaDAO dao = new TarefaDAO(getBaseContext());
                boolean sucesso;
                if(TarefaEditado != null)
                    sucesso = dao.salvar(TarefaEditado.getId(), titulo, descri  , professor, semestre, vip, data, horainicio);
                else
                    sucesso = dao.salvar(titulo, descri , professor, semestre, vip, data, horainicio);

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


                    //enviando todos os dados da Do titulo para a minha intent/bundle em history
                    Intent intent = new Intent(TarefaActivity.this, HistoryActivity.class);

                    Bundle params = new Bundle();

                    params.putString("Titulo", txtTitulo.getText().toString());
                    params.putString("Desc", txtDesc.getText().toString());

                    intent.putExtras(params);

                    //startActivity(intent);

                    //enviando todos os dados da Do titulo para a minha intent/bundle em history


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

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);            //Obtendo imagem da galeria

                imgView.setImageBitmap(bitmap);                                                               //Definir imagem para ImageView



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == RESULT_OK){

            filePath = data.getData();

        }

    }

    protected void showProgressDialog(String title, String msg) {
        progressDialog = ProgressDialog.show(this, title, msg, true);
    }
    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }



}
