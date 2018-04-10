package com.veliyetisgengil.onlinequizapp.Model;

/**
 * Created by veliyetisgengil on 29.03.2018.
 */

public class Question {
    private String Question,AnswerA,AnswerB,AnswerC,AnswerD,AnswerE,CorrectAnswer,CategoryId,imageQuestion;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String answerE, String correctAnswer, String categoryId, String imageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        AnswerE = answerE;
        CorrectAnswer = correctAnswer;
        CategoryId = categoryId;
        this.imageQuestion = imageQuestion;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getAnswerE() {
        return AnswerE;
    }

    public void setAnswerE(String answerE) {
        AnswerE = answerE;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getImageQuestion() {
        return imageQuestion;
    }

    public void setImageQuestion(String imageQuestion) {
        this.imageQuestion = imageQuestion;
    }
}
