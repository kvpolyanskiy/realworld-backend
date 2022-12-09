package kv.polyanskiy.realworld.services;

import kv.polyanskiy.realworld.domain.User;
import kv.polyanskiy.realworld.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UsersRepository usersRepository;

  @Autowired
  public UserService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public User createUser(User user) {
    return usersRepository.save(user);
  }
}
