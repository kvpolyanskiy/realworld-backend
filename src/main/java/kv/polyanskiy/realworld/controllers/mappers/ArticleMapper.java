package kv.polyanskiy.realworld.controllers.mappers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import kv.polyanskiy.realworld.domain.Article;
import kv.polyanskiy.realworld.domain.Tag;
import kv.polyanskiy.realworld.domain.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

  Article newArticleDtoToArticle(kv.polyanskiy.realworld.dto.model.NewArticle article);

  @Mapping(target = "createdAt", qualifiedByName = "fromInstant")
  @Mapping(target = "updatedAt", qualifiedByName = "fromInstant")
  @Mapping(source = "tags", target = "tagList", qualifiedByName = "tagList")
  @Mapping(source = "id", target = "slug")
  @Mapping(source = "author", target = "author", qualifiedByName = "author")
  kv.polyanskiy.realworld.dto.model.Article articleToArticleDto(Article article,
      @Context User currentUser);

  @Named("tagList")
  public static List<String> tagList(Set<Tag> tags) {
    return tags.stream().map(Tag::getName).toList();
  }

  @Named("fromInstant")
  public static OffsetDateTime fromInstant(Instant instant) {
    return instant == null ? null
        : OffsetDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
  }

  @Named("author")
  public static kv.polyanskiy.realworld.dto.model.Profile author(User author,
      @Context User currentUser) {
    final var profile = new kv.polyanskiy.realworld.dto.model.Profile();

    final var isFollowing = author.getFollowing().stream()
        .anyMatch(f -> f.getFollowingUsername().equals(currentUser.getUsername()));

    profile.setUsername(author.getUsername());
    profile.bio(author.getBio());
    profile.setImage(author.getImage());
    profile.setFollowing(isFollowing);

    return profile;
  }
}
