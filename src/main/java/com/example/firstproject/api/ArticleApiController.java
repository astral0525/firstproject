package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController// RestAPI용 컨트롤러! 데이터(Json 등)을 반환
public class ArticleApiController {

    @Autowired //DI
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
    //POST - 데이터생성
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){
       Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH - 데이터 수정
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                          @RequestBody ArticleForm dto){
        //1. 수정용 엔터티 생성
        Article article = dto.toEntity();
        log.info("id : {}, article: {}", id, article.toString());

        //2. 대상 엔터티를 조회
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청 처리
        //대상이 없거나 URL의 id와 데이터(JSON)의 id가 다른 경우
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답.
            log.info("잘못된 요청 : id:{}, article: {} ", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //4. 업데이트 및 정상 응답(200)
        target.patch(article); //데이터가 일부만 왔을 경우
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        //대상찾기
        Article target = articleRepository.findById(id).orElse(null);

        //대상 삭제
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //데이터 반환
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build(); //응답생성

    }


}
