package br.com.faculdade.construtoraconstruindosempre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button bt_reserva;
    Button bt_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarComponentes();
        criarEventos();

    }

    private void criarComponentes() {
        bt_reserva = (Button)this.findViewById(R.id.bt_reserva);
        bt_lista = (Button)this.findViewById(R.id.bt_lista);
    }


    protected  void criarEventos(){
        bt_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReserva = new Intent(getApplicationContext(), LocacaoActivity.class);
                startActivity(intentReserva);
            }
        });

        bt_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLista = new Intent(getApplicationContext(), ConsultaReservaActicity.class);
                startActivity(intentLista);
            }
        });

    }

    protected void redirecionaTela(String opcaoMenu){


    }
}
