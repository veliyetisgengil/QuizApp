package com.veliyetisgengil.onlinequizapp.Model;

/**
 * Created by veliyetisgengil on 30.03.2018.
 */

public class Rank {

    private String userName;
    private long score;

    public Rank() {
    }

    public Rank(String userName, long score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
