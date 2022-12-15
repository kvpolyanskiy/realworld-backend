package kv.polyanskiy.realworld.controllers;

import kv.polyanskiy.realworld.api.ProfilesApi;
import kv.polyanskiy.realworld.controllers.advice.UserNotFoundException;
import kv.polyanskiy.realworld.controllers.mappers.ProfileMapper;
import kv.polyanskiy.realworld.domain.Profile;
import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.dto.model.GetProfileByUsername200Response;
import kv.polyanskiy.realworld.services.ProfileService;
import kv.polyanskiy.realworld.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController implements ProfilesApi {

  private final ProfileService profileService;

  private final UserService userService;

  private final ProfileMapper profileMapper;

  @Override
  public ResponseEntity<GetProfileByUsername200Response> followUserByUsername(String username) {
    Profile profile = profileService.followUser(username);

    final var response = new GetProfileByUsername200Response();
    response.setProfile(profileMapper.profileToProfileDto(profile));

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<GetProfileByUsername200Response> getProfileByUsername(String username) {
    final User currentUser = userService.getCurrentUser();

    final var profile = profileService.getProfile(username, currentUser);

    if (profile == null) {
      throw new UserNotFoundException(username);
    }

    final var response = new GetProfileByUsername200Response();
    response.setProfile(profileMapper.profileToProfileDto(profile));

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<GetProfileByUsername200Response> unfollowUserByUsername(String username) {
    Profile profile = profileService.unfollowUser(username);

    final var response = new GetProfileByUsername200Response();
    response.setProfile(profileMapper.profileToProfileDto(profile));

    return ResponseEntity.ok(response);
  }
}
