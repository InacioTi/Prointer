package com.faculdade.faculdade.RecycleView.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.faculdade.faculdade.R;
import com.faculdade.faculdade.RecycleView.activies.HistoryActivity;
import com.faculdade.faculdade.RecycleView.adapter.viewholder.HistoryHolder;
import com.faculdade.faculdade.RecycleView.adapter.viewholder.TarefaCardHolder;
import com.faculdade.faculdade.RecycleView.model.TarefaDAO;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {

    private final List<Tarefa> tarefas;

    public HistoryAdapter(List<Tarefa> tarefas) {
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
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_tarefa_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {



        holder.main_card_tarefa_title.setText(tarefas.get(position).getTitulo());
        holder.main_card_tarefa_desc.setText(tarefas.get(position).getDesc());
        holder.date.setText(tarefas.get(position).getData());
        holder.hora.setText(tarefas.get(position).getHorainicio());
        final Tarefa tarefa = tarefas.get(position);

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