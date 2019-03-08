package com.faculdade.faculdade.RecycleView.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faculdade.faculdade.R;

public class TarefaHolder extends RecyclerView.ViewHolder {

    public TextView nomeTarefa;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public TarefaHolder(View itemView) {
        super(itemView);
        nomeTarefa = (TextView) itemView.findViewById(R.id.nomeTarefa);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}