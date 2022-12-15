package kv.polyanskiy.realworld.controllers.advice;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String username) {
    super("User " + username + " not found");
  }
}
