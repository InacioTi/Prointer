package com.faculdade.faculdade.RecycleView.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faculdade.faculdade.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class LineHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_line_title)
    public TextView title;

    @BindView(R.id.main_line_more)
    public ImageButton moreButton;

    @BindView(R.id.main_line_delete)
    public ImageButton deleteButton;

    public LineHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
