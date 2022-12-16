package kv.polyanskiy.realworld.repositories;

import kv.polyanskiy.realworld.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
