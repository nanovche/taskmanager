package com.example.taskmanager.mappers;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.Authority;
import com.example.taskmanager.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity entityUser);
    UserEntity toEntity(UserDTO userDTO);

    default String[] mapToDTOAuthorities (Set<Authority> authorities) {

        if(authorities == null) return null;

        return authorities.stream()
                .map(Authority::getName)
                .toArray(String[]::new);
    }

    default Set<Authority> mapToEntityAuthorities (String[] authorities) {
        return null;
    }
}