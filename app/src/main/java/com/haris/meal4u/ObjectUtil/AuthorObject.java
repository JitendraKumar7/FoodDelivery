package com.haris.meal4u.ObjectUtil;

public class AuthorObject {
    private String authorName;
    private String authorBooks;
    private String authorBio;
    private String authorOverallRating;


    public String getAuthorName() {
        return authorName;
    }

    public AuthorObject setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getAuthorBooks() {
        return authorBooks;
    }

    public AuthorObject setAuthorBooks(String authorBooks) {
        this.authorBooks = authorBooks;
        return this;
    }

    public String getAuthorBio() {
        return authorBio;
    }

    public AuthorObject setAuthorBio(String authorBio) {
        this.authorBio = authorBio;
        return this;
    }

    public String getAuthorOverallRating() {
        return authorOverallRating;
    }

    public AuthorObject setAuthorOverallRating(String authorOverallRating) {
        this.authorOverallRating = authorOverallRating;
        return this;
    }
}
