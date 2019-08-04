package com.example.recyclerviewpractice1;


import java.io.Serializable;

public class NewsData implements Serializable {//데이터 분류 용도
    private String title;
    private String urlToImage;
    private String content;//캡슐화를 위해 private으로 선언한다.

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
