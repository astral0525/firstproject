package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class ArticleForm {
    private Long id; //id값 추가
    private String title;
    private String content;

    public Article toEntity() { //null값 대신 id값 추가
        return new Article(id, title, content);
    }
}
