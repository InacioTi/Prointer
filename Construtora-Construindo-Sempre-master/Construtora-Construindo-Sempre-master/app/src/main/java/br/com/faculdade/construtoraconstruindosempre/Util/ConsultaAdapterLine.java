package br.com.faculdade.construtoraconstruindosempre.Util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.faculdade.construtoraconstruindosempre.ConsultaReservaActicity;
import br.com.faculdade.construtoraconstruindosempre.DAO.ReservaDAO;
import br.com.faculdade.construtoraconstruindosempre.EditarLocacaoActivity;
import br.com.faculdade.construtoraconstruindosempre.Model.Reserva;
import br.com.faculdade.construtoraconstruindosempre.R;

/**
 * Created by edinilson.silva on 13/09/2016.
 */
public class ConsultaAdapterLine extends BaseAdapter {

    //LINK DIRTO COM A VIEW
    private static LayoutInflater layoutInflater = null;

    //ACESSO AO BANCO
    ReservaDAO reservaDAO;

    List<Reserva> reservas = new ArrayList<Reserva>();

    private ConsultaReservaActicity consultaReservaActicity;

    public ConsultaAdapterLine(ConsultaReservaActicity consultaReservaActicity, List<Reserva> reservas){
        this.reservas = reservas;
        this.consultaReservaActicity = consultaReservaActicity;
        this.layoutInflater = (LayoutInflater) this.consultaReservaActicity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reservaDAO = new ReservaDAO(consultaReservaActicity);
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        final View viewLinhaLista = layoutInflater.inflate(R.layout.activity_linha_consultar, null);

        TextView textViewIdReserva = (TextView)viewLinhaLista.findViewById(R.id.textViewCodigo);
        TextView textViewNomeLocador = (TextView)viewLinhaLista.findViewById(R.id.textViewNomeLocador);
        TextView textViewDestino = (TextView)viewLinhaLista.findViewById(R.id.textViewDestino);
        TextView textViewEquipamento = (TextView)viewLinhaLista.findViewById(R.id.textViewEquipamento);
        TextView textViewData = (TextView)viewLinhaLista.findViewById(R.id.textViewData);
        TextView textViewHorarioInicial = (TextView)viewLinhaLista.findViewById(R.id.textViewInicial);
        TextView textViewHorarioFinal = (TextView)viewLinhaLista.findViewById(R.id.textViewFinal);

        Button buttonExcluir = (Button)viewLinhaLista.findViewById(R.id.buttonExcluir);
        Button buttonEditar = (Button)viewLinhaLista.findViewById(R.id.buttonEditar);

        //SETANDO VALORES A VIEW
        textViewIdReserva.setText(String.valueOf(reservas.get(position).getIdLocacao()));
        textViewNomeLocador.setText(String.valueOf(reservas.get(position).getNomeLocador()));
        textViewEquipamento.setText(String.valueOf(reservas.get(position).getEquipamento()));
        textViewDestino.setText(String.valueOf(reservas.get(position).getDestino()));
        textViewData.setText(reservas.get(position).getData());
        textViewHorarioFinal.setText(reservas.get(position).getHorarioFinal());
        textViewHorarioInicial.setText(reservas.get(position).getHorarioInicial());

        //CRIANDO EVENTOS EXCLUIR
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reservaDAO.excluir(reservas.get(position).getIdLocacao());
                Toast.makeText(consultaReservaActicity, "Registro excluido com sucesso!", Toast.LENGTH_SHORT).show();
                atualizarLista();
            }
        });

        //CRIANDO EVENTOS EDITAR
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(consultaReservaActicity, EditarLocacaoActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", reservas.get(position).getIdLocacao());
                consultaReservaActicity.startActivity(intent);
                consultaReservaActicity.finish();
            }
        });
        return viewLinhaLista;
    }

    private void atualizarLista(){
        this.reservas.clear();
        this.reservas = reservaDAO.selecionarTodos();
        this.notifyDataSetChanged();
    }


}
