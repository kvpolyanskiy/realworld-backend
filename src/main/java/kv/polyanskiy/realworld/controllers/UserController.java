package kv.polyanskiy.realworld.controllers;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import kv.polyanskiy.realworld.api.UserApi;
import kv.polyanskiy.realworld.controllers.mappers.UserMapper;
import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.dto.model.Login200Response;
import kv.polyanskiy.realworld.dto.model.UpdateCurrentUserRequest;
import kv.polyanskiy.realworld.security.JwtUtils;
import kv.polyanskiy.realworld.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final HttpServletRequest httpServletRequest;
  private final JwtUtils jwtUtils;
  private final UserMapper userMapper;

  private final UserService userService;

  @Override
  public ResponseEntity<Login200Response> getCurrentUser() {
    final String userEmail = jwtUtils.getTokenSubject(httpServletRequest.getHeader(AUTHORIZATION));
    final User user = userService.findUserByEmail(userEmail);

    final var response = new Login200Response();
    response.setUser(userMapper.userToUserDto(user));

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Login200Response> updateCurrentUser(UpdateCurrentUserRequest body) {
    final var email = jwtUtils.getTokenSubject(httpServletRequest.getHeader(AUTHORIZATION));
    final var user = userService.update(email, userMapper.updateUserDtoToUser(body.getUser()));

    final var response = new Login200Response();
    response.setUser(userMapper.userToUserDto(user));

    return ResponseEntity.ok(response);
  }
}
