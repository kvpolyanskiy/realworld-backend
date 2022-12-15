package kv.polyanskiy.realworld.controllers.mappers;

import kv.polyanskiy.realworld.domain.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
  kv.polyanskiy.realworld.dto.model.Profile profileToProfileDto(Profile profile);
}
