package com.faculdade.faculdade.RecycleView.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faculdade.faculdade.R;

public class TarefaCardHolder extends RecyclerView.ViewHolder {

    public TextView main_card_tarefa_title;
    public TextView main_card_tarefa_desc;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;

    public TarefaCardHolder(View itemView) {
        super(itemView);
        main_card_tarefa_title = (TextView) itemView.findViewById(R.id.main_card_tarefa_title);
        main_card_tarefa_desc = (TextView) itemView.findViewById(R.id.main_card_tarefa_desc);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
    }
}
