package kv.polyanskiy.realworld.services;

import kv.polyanskiy.realworld.domain.Following;
import kv.polyanskiy.realworld.domain.FollowingId;
import kv.polyanskiy.realworld.domain.Profile;
import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.repositories.FollowingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserService userService;

  private final FollowingRepository followingRepository;

  public Profile getProfile(String username, User currentUser) {
    final User user = userService.findUserByUsername(username);

    if (user == null) {
      return null;
    }

    boolean isFollowing = false;

    if (currentUser != null) {
      isFollowing = currentUser.getFollowing().stream().map(Following::getFollowingUsername)
          .toList()
          .contains(username);
    }

    return new Profile(user.getUsername(), user.getBio(), user.getImage(), isFollowing);
  }

  public Profile followUser(String username) {
    final var currentUser = userService.getCurrentUser();
    final var user = userService.findUserByUsername(username);

    final var following = currentUser.getFollowing();
    final var isFollowing = following.stream().map(Following::getFollowingUsername).toList()
        .contains(user.getUsername());

    if (isFollowing) {
      return new Profile(user.getUsername(), user.getBio(), user.getImage(), true);
    }

    followingRepository.save(new Following(currentUser.getId(), username));

    return new Profile(user.getUsername(), user.getBio(), user.getImage(), true);
  }

  public Profile unfollowUser(String username) {
    final var currentUser = userService.getCurrentUser();
    final var user = userService.findUserByUsername(username);

    followingRepository.deleteById(new FollowingId(currentUser.getId(), username));

    return new Profile(user.getUsername(), user.getBio(), user.getImage(), false);
  }
}
