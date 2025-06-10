package com.kitapkosem.model;

import java.sql.Timestamp;

public class Rating {

    private int ratingId;
    private int bookId;
    private int userId;
    private int ratingValue;
    private Timestamp ratingDate;

    public Rating() {
    }

    public Rating(int ratingId, int bookId, int userId, int ratingValue, Timestamp ratingDate) {
        this.ratingId = ratingId;
        this.bookId = bookId;
        this.userId = userId;
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
    }



    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Timestamp getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Timestamp ratingDate) {
        this.ratingDate = ratingDate;
    }


    @Override
    public String toString() {
        return "Rating{" +
                "ratingId=" + ratingId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", ratingValue=" + ratingValue +
                ", ratingDate=" + ratingDate +
                '}';
    }
}