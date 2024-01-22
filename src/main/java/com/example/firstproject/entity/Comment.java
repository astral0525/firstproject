package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //해당 댓글 엔터티 여러개가 하나의 Article에 연관된다.
    @JoinColumn(name = "article_id") //article_id컬럼으로 생성됨.
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;


    public static Comment createComent(CommentDto dto, Article article) {

        //id가 있을 시 예외 발생
        if(dto.getId() != null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }

        //json(dto)의 articleId와 article의 id가 다를 시 예외발생
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다");

        //엔터티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );





    }

    public void patch(CommentDto dto) {
        //예외 발생 : 현재의 id와 dto로 가져온 id가 다른 경우
        if(this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다");
        }

        //객체를 갱신
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if(dto.getBody() != null)
            this.body = dto.getBody();



    }
}
