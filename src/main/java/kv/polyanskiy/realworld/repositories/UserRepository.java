package kv.polyanskiy.realworld.repositories;

import kv.polyanskiy.realworld.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByEmail(String username);

  User findByUsername(String username);
}
