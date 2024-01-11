package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j //로깅을 통한 어노테이션
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체를 가져다가 자동 연결해주기 때문에 구현안함
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    @ResponseBody
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        //1. Dto(form)를 Entity로 변환하는 과정
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "작성이완료되었습니다";
    }


    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        //article이라는 이름으로 articleEntity변수를 등록

        //3. 보여줄 페이지를 설정
        return "articles/show";

    }

    @GetMapping("/articles")
    public String index(Model model){
        //1.모든 Article을 가져온다!
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index"; //articles/index.mustache

        return "";
    }



}
