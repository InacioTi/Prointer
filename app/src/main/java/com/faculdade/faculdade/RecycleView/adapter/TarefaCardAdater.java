package com.faculdade.faculdade.RecycleView.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.adapter.viewholder.TarefaCardHolder;
import com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;

import java.util.List;


public class TarefaCardAdater extends RecyclerView.Adapter<TarefaCardHolder> {


    private final List<Tarefa> tarefas;

    public TarefaCardAdater(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public void atualizarTarefa(Tarefa tarefa){
        tarefas.set(tarefas.indexOf(tarefa), tarefa);
        notifyItemChanged(tarefas.indexOf(tarefa));
    }

    public void adicionarTarefa(Tarefa tarefa){
        tarefas.add(tarefa);
        notifyItemInserted(getItemCount());
    }

    public void removerTarefa(Tarefa tarefa){
        int position = tarefas.indexOf(tarefa);
        tarefas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public TarefaCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TarefaCardHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_tarefa_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(TarefaCardHolder holder, int position) {

        holder.main_card_tarefa_title.setText(tarefas.get(position).getTitulo());
        holder.main_card_tarefa_desc.setText(tarefas.get(position).getDesc());
        holder.date.setText(tarefas.get(position).getData());
        final Tarefa tarefa = tarefas.get(position);
        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir esta Tarefa?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TarefaDAO dao = new TarefaDAO(view.getContext());
                                boolean sucesso = dao.excluir(tarefa.getId());
                                if(sucesso) {
                                    removerTarefa(tarefa);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir a Tarefa!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });

        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("Tarefa", tarefa);
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return tarefas != null ? tarefas.size() : 0;
    }
}

