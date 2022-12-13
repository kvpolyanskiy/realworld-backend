package kv.polyanskiy.realworld.services;

import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.repositories.UsersRepository;
import kv.polyanskiy.realworld.services.mappers.UserUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;

  private final UserUpdateMapper userUpdateMapper;

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
}
