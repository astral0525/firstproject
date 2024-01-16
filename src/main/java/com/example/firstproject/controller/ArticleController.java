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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        //1. Dto(form)를 Entity로 변환하는 과정
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에게 Entity를 DB안에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
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
        //이 때 findAll()함수는 Iterable이 반환값이므로 List<Article>을 반환하도록 오버라이드 해준다.
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정!
        return "articles/index"; //articles/index.mustache

    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        //수정할 데이터를 가져오기 : Repository
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit"; //뷰페이지 설정 : edit.mustache
    }


    @PostMapping("/articles/update")
    public String update(ArticleForm form){

        //1. DTO를 엔티티로 변환한다.
        Article articleEntity = form.toEntity();

        //2. 엔터티를 DB로 저장한다.
        //2-1.DB에서 기존 데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2.기존 데이터가 있다면 값을 갱신한다.
        if (target!=null){
            articleRepository.save(articleEntity); //엔터티가 DB로 갱신
        }

        //3. 수정결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();

    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        //1. 삭제 대상을 가져온다.
        Article target = articleRepository.findById(id).orElse(null);

        //2. 대상을 삭제한다.
        if(target !=null){
            articleRepository.delete(target); //deleteById사용해도 됨
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다");
        }

        //3. 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles";
    }




}
