package com.veliyetisgengil.onlinequizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.veliyetisgengil.onlinequizapp.Model.Question;
import com.veliyetisgengil.onlinequizapp.Model.QuestionScore;
import com.veliyetisgengil.onlinequizapp.ViewHolder.ScoreDetailViewHolder;

public class ScoreDetailActivity extends AppCompatActivity {
        String viewUser="";
        FirebaseDatabase database;
        DatabaseReference questionScore;

        RecyclerView scoreList;
        RecyclerView.LayoutManager layoutManager;
        FirebaseRecyclerAdapter<QuestionScore,ScoreDetailViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        //Firebase
        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");

        //view

        scoreList = (RecyclerView)findViewById(R.id.scoreList);
        scoreList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scoreList.setLayoutManager(layoutManager);



        if(getIntent() != null)
            viewUser = getIntent().getStringExtra("viewUser");

        if(!viewUser.isEmpty()){
            loadScoreDetail(viewUser);

        }

    }

    private void loadScoreDetail(String viewUser) {

        adapter = new FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder>(
                QuestionScore.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                questionScore.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder viewHolder, QuestionScore model, int position) {
                    viewHolder.txt_name.setText(model.getCategoryName());
                    viewHolder.txt_score.setText(model.getScore());
            }
        };
        adapter.notifyDataSetChanged();
        scoreList.setAdapter(adapter);
    }
}
