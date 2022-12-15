package kv.polyanskiy.realworld.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingId implements Serializable {
  private Integer userId;
  private String followingUsername;
}
