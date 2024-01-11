package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Entity //DB가 해당 객체를 인식 가능
public class Article {

    @Id //Article의 대표값
    @GeneratedValue //1,2,3.. 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
