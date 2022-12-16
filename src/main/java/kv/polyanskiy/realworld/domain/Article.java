package kv.polyanskiy.realworld.domain;

import java.time.Instant;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "articles")
@Data
public class Article {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "body")
  private String body;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(name = "favorites_count")
  private Integer favoritesCount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private User author;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "articles_tags",
      joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "article_id")
  )
  private Set<Tag> tags;
}
