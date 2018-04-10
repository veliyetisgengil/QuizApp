package com.veliyetisgengil.onlinequizapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.veliyetisgengil.onlinequizapp.R;

/**
 * Created by veliyetisgengil on 3.04.2018.
 */

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder  {

    public TextView txt_name,txt_score;

    public ScoreDetailViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.txt_name);
        txt_score = (TextView)itemView.findViewById(R.id.txt_score);
    }
}
