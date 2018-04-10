package com.veliyetisgengil.onlinequizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.veliyetisgengil.onlinequizapp.Common.Common;
import com.veliyetisgengil.onlinequizapp.Interface.ItemClickListener;
import com.veliyetisgengil.onlinequizapp.Interface.RankingCallBack;
import com.veliyetisgengil.onlinequizapp.Model.QuestionScore;
import com.veliyetisgengil.onlinequizapp.Model.Rank;
import com.veliyetisgengil.onlinequizapp.ViewHolder.RankingViewHolder;


public class RankingFragment extends Fragment {
    View myFragment;
    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Rank,RankingViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference questionScore ,rankingTb1;
int sum=0;
    public static RankingFragment newInstance(){

        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database= FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTb1 = database.getReference("Rank");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking, container,false);
        //innit view
        rankingList = (RecyclerView)myFragment.findViewById(R.id.rankingList);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        updateScore(Common.currentUser.getUserName(), new RankingCallBack<Rank>() {
            @Override
            public void callBack(Rank rank) {
                rankingTb1.child(rank.getUserName()).setValue(rank);
               // showRanking();
            }
        });
        adapter = new FirebaseRecyclerAdapter<Rank, RankingViewHolder>( Rank.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingTb1.orderByChild("score")) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Rank model, int position) {

               viewHolder.txt_name.setText(model.getUserName());
               viewHolder.txt_score.setText(String.valueOf(model.getScore()));

               viewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                    Intent scoreDetail = new Intent(getActivity(),ScoreDetailActivity.class);
                    scoreDetail.putExtra("viewUser", model.getUserName());
                    startActivity(scoreDetail);
                   }
               });
            }
        };
                adapter.notifyDataSetChanged();
                rankingList.setAdapter(adapter);
        return myFragment;
    }



    private void updateScore(final String userName, final RankingCallBack<Rank> callback) {
            questionScore.orderByChild("user").equalTo(userName)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()){
                                        QuestionScore ques = data.getValue(QuestionScore.class);
                                        sum+=Integer.parseInt(ques.getScore());
                                    }
                                    Rank rank = new Rank(userName,sum);
                                    callback.callBack(rank);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

    }
}
