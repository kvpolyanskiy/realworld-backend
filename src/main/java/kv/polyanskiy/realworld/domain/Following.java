package kv.polyanskiy.realworld.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "following")
@Entity
@Data
@IdClass(FollowingId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Following implements Serializable {

  @Id
  @Column(name = "user_id")
  private Integer userId;

  @Id
  @Column(name = "following_username")
  private String followingUsername;
}
