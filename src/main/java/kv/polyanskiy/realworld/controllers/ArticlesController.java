package kv.polyanskiy.realworld.controllers;

import kv.polyanskiy.realworld.controllers.mappers.ArticleMapper;
import kv.polyanskiy.realworld.domain.Article;
import kv.polyanskiy.realworld.dto.model.CreateArticle201Response;
import kv.polyanskiy.realworld.dto.model.CreateArticleComment200Response;
import kv.polyanskiy.realworld.dto.model.CreateArticleCommentRequest;
import kv.polyanskiy.realworld.dto.model.CreateArticleRequest;
import kv.polyanskiy.realworld.dto.model.GetArticleComments200Response;
import kv.polyanskiy.realworld.dto.model.GetArticlesFeed200Response;
import kv.polyanskiy.realworld.dto.model.UpdateArticleRequest;
import kv.polyanskiy.realworld.services.ArticlesService;
import kv.polyanskiy.realworld.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticlesController implements kv.polyanskiy.realworld.api.ArticlesApi {

  private final ArticlesService articlesService;

  private final ArticleMapper articleMapper;

  private final UserService userService;

  @Override
  public ResponseEntity<CreateArticle201Response> createArticle(CreateArticleRequest request) {
    final var articleDto = request.getArticle();
    final Article article = articlesService.createArticle(
        articleMapper.newArticleDtoToArticle(articleDto), articleDto.getTagList());

    final var response = new CreateArticle201Response();
    response.setArticle(articleMapper.articleToArticleDto(article, userService.getCurrentUser()));

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CreateArticleComment200Response> createArticleComment(String slug,
      CreateArticleCommentRequest comment) {
    return null;
  }

  @Override
  public ResponseEntity<CreateArticle201Response> createArticleFavorite(String slug) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteArticle(String slug) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteArticleComment(String slug, Integer id) {
    return null;
  }

  @Override
  public ResponseEntity<CreateArticle201Response> deleteArticleFavorite(String slug) {
    return null;
  }

  @Override
  public ResponseEntity<CreateArticle201Response> getArticle(String slug) {
    return null;
  }

  @Override
  public ResponseEntity<GetArticleComments200Response> getArticleComments(String slug) {
    return null;
  }

  @Override
  public ResponseEntity<GetArticlesFeed200Response> getArticles(String tag, String author,
      String favorited, Integer offset, Integer limit) {
    return null;
  }

  @Override
  public ResponseEntity<GetArticlesFeed200Response> getArticlesFeed(Integer offset, Integer limit) {
    return null;
  }

  @Override
  public ResponseEntity<CreateArticle201Response> updateArticle(String slug,
      UpdateArticleRequest article) {
    return null;
  }
}
