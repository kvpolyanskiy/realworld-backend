package kv.polyanskiy.realworld.controllers;

import kv.polyanskiy.realworld.api.UsersApi;
import kv.polyanskiy.realworld.security.JwtUtils;
import kv.polyanskiy.realworld.controllers.mappers.UserMapper;
import kv.polyanskiy.realworld.domain.UserCredentials;
import kv.polyanskiy.realworld.dto.model.CreateUserRequest;
import kv.polyanskiy.realworld.dto.model.Login200Response;
import kv.polyanskiy.realworld.dto.model.LoginRequest;
import kv.polyanskiy.realworld.services.UserService;
import kv.polyanskiy.realworld.validation.EntityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController implements UsersApi {

  private final UserService usersService;
  private final UserMapper userMapper;

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final JwtUtils jwtUtils;

  private final PasswordEncoder passwordEncoder;

  private final EntityValidator entityValidator;

  @Override
  public ResponseEntity<Login200Response> createUser(CreateUserRequest body) {
    final var newUser = userMapper.newUserDtoToUser(body.getUser());

    entityValidator.validate(newUser);

    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

    final var user = usersService.createUser(newUser);

    final var response = new Login200Response();
    response.setUser(userMapper.userToUserDto(user));

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Login200Response> login(LoginRequest body) {
    final var user = body.getUser();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

    if (userDetails instanceof UserCredentials userCredentials) {
      final var token = jwtUtils.generateToken(userDetails);
      final var userDto = userMapper.userToUserDto(userCredentials.getUser());
      userDto.setToken(token);
      final var response = new Login200Response();
      response.setUser(userDto);

      return ResponseEntity.ok(response);
    }

    throw new BadCredentialsException("Username or password is wrong");
  }
}
