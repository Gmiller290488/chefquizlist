package com.ctingcter.chefquizlist;

/**
 * Created by CTingCTer on 29/07/2017.
 */

    public class Question {

        private int mId;

        private String mQuestion;

        private String mAnswer1;

        private String mAnswer2;

        private String mAnswer3;

        private String mCorrectAnswer;

        public Question(int id, String question, String Answer1, String Answer2, String Answer3, String correctAnswer) {
            mId = id;
            mQuestion = question;
            mAnswer1 = Answer1;
            mAnswer2 = Answer2;
            mCorrectAnswer = correctAnswer;
        }
        public int getId() {
            return mId;
        }

        public String getQuestion() {
            return mQuestion;
        }

        public String getAnswer1() {
            return mAnswer1;
        }

        public String getAnswer2() {
            return mAnswer2;
        }

        public String getAnswer3() {
            return mAnswer3;
        }

        public String getCorrectanswer() {
            return mCorrectAnswer;
        }

        public void setId(int Id) {
            mId = Id;
        }

        public void setQuestion(String question) {
            mQuestion = question;
        }

        public void setAnswer1(String Answer1) {
        mAnswer1 = Answer1;
         }

        public void setAnswer2(String Answer2) {
        mAnswer2 = Answer2;
    }

        public void setAnswer3(String Answer3) {
        mAnswer3 = Answer3;
    }

        public void setCorrectAnswer(String correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

}
