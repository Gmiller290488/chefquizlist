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

        private int mImageAnswer1;

        private int mImageAnswer2;

        private int mImageAnswer3;

        private int mImageCorrect;

        private int mImageResourceId = NO_IMAGE_PROVIDED;

        private static final int NO_IMAGE_PROVIDED = -1;

        private String mCategory;

        public Question(int id, String question, String Answer1, String Answer2, String Answer3, String correctAnswer, String category) {
            mId = id;
            mQuestion = question;
            mAnswer1 = Answer1;
            mAnswer2 = Answer2;
            mAnswer3 = Answer3;
            mCorrectAnswer = correctAnswer;
            mCategory = category;
        }

    public Question(int id, String question, int imageAnswer1, int imageAnswer2, int imageAnswer3, int imageAnswerCorrect, int imageResourceId, String category ) {
        mId = id;
        mQuestion = question;
        mImageAnswer1 = imageAnswer1;
        mImageAnswer2 = imageAnswer2;
        mImageAnswer3 = imageAnswer3;
        mImageCorrect = imageAnswerCorrect;
        mImageResourceId = imageResourceId;
        mCategory = category;
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

    public int getImageAnswer1() {
        return mImageAnswer1;
    }

    public int getImageAnswer2() {
        return mImageAnswer2;
    }

    public int getImageAnswer3() {
        return mImageAnswer3;
    }

    public int getImageCorrect() {
        return mImageCorrect;
    }
    public String getCorrectanswer() {
        return mCorrectAnswer;
    }

    public String getCategory() { return mCategory; }

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

        public void setImageAnswer1(int imageAnswer1) {
            mImageAnswer1 = imageAnswer1;
        }

    public void setImageAnswer2(int imageAnswer2) {
        mImageAnswer2 = imageAnswer2;
    }

    public void setImageAnswer3(int imageAnswer3) {
        mImageAnswer3 = imageAnswer3;
    }

    public void setImageAnswerCorrect(int imageAnswerCorrect) {
        mImageCorrect = imageAnswerCorrect;
    }

        public boolean hasImage() {
            return mImageResourceId != NO_IMAGE_PROVIDED;
    }

        public void setCorrectAnswer(String correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

}
