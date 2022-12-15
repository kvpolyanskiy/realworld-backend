package kv.polyanskiy.realworld.services;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import kv.polyanskiy.realworld.domain.Profile;
import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.repositories.UserRepository;
import kv.polyanskiy.realworld.security.JwtUtils;
import kv.polyanskiy.realworld.services.mappers.UserUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository usersRepository;

  private final UserUpdateMapper userUpdateMapper;

  private final JwtUtils jwtUtils;

  private final HttpServletRequest httpServletRequest;

  public User createUser(User user) {
    return usersRepository.save(user);
  }

  public User findUserByEmail(String userEmail) {
    return usersRepository.findByEmail(userEmail);
  }

  public User update(String email, User updatedUser) {
    var user = usersRepository.findByEmail(email);
    userUpdateMapper.updateUser(updatedUser, user);

    return usersRepository.save(user);
  }

  public User findUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  public User getCurrentUser() {
    final var userEmail = jwtUtils.getTokenSubject(httpServletRequest.getHeader(AUTHORIZATION));
    return findUserByEmail(userEmail);
  }
}
