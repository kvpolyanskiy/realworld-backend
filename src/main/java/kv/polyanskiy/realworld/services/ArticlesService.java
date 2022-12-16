package kv.polyanskiy.realworld.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import kv.polyanskiy.realworld.domain.Article;
import kv.polyanskiy.realworld.domain.Tag;
import kv.polyanskiy.realworld.repositories.ArticleRepository;
import kv.polyanskiy.realworld.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlesService {
  private final UserService userService;
  private final TagRepository tagRepository;

  private final ArticleRepository articleRepository;

  @Transactional
  public Article createArticle(Article newArticle, List<String> tagList) {
    final var currentUser = userService.getCurrentUser();
    final var article = new Article();

    final Set<Tag> tags = new HashSet<>();

    tagList.forEach(tagName -> {
      final var tag = tagRepository.findByName(tagName);

      if (tag == null) {
        final var newTag = new Tag();
        newTag.setName(tagName);
        tags.add(tagRepository.save(newTag));
      }
    });

    article.setTitle(newArticle.getTitle());
    article.setBody(newArticle.getBody());
    article.setDescription(newArticle.getDescription());
    article.setAuthor(currentUser);
    article.setTags(tags);

    return articleRepository.save(article);
  }
}
