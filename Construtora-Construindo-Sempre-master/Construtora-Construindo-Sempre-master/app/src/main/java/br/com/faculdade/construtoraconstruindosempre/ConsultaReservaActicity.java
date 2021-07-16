package br.com.faculdade.construtoraconstruindosempre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.faculdade.construtoraconstruindosempre.DAO.ReservaDAO;
import br.com.faculdade.construtoraconstruindosempre.Model.Reserva;
import br.com.faculdade.construtoraconstruindosempre.Util.ConsultaAdapterLine;

/**
 * Created by edinilson.silva on 14/09/2016.
 */
public class ConsultaReservaActicity extends Activity {

    ListView listViewReservas;
    Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        listViewReservas = (ListView)this.findViewById(R.id.listViewReservas);
        buttonVoltar = (Button)this.findViewById(R.id.buttonVoltar);

        carregarReservasCadastradas();
        botaoVoltar();
    }

    private void carregarReservasCadastradas(){

        ReservaDAO reservaDAO = new ReservaDAO(this);

        List<Reserva> reservas = reservaDAO.selecionarTodos();
        listViewReservas.setAdapter(new ConsultaAdapterLine(this, reservas));
    }

    private void botaoVoltar(){
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
                finish();
            }
        });
    }
}
