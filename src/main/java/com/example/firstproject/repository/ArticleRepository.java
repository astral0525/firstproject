package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

//CrudRepository : 스프링 프레임워크에서 제공하는 인터페이스
//기본적인 CRUD 작업을 수행하는 메서드를 정의
//CrudRepository를 상속받으면 별도로 메서드를 선언하지 않아도
// 기본적인 CRUD 작업 수행가능(JPA가 내부적으로 메서드 자동 생성해줌)
//findById(), save(), delete()등
//<엔터티 타입, 해당 엔터티의 기본 키 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {



}
