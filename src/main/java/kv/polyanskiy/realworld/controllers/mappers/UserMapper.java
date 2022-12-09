package kv.polyanskiy.realworld.controllers.mappers;


import kv.polyanskiy.realworld.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  kv.polyanskiy.realworld.dto.model.User userToUserDto(User user);

  User newUserDtoToUser(kv.polyanskiy.realworld.dto.model.NewUser userDto);
}
