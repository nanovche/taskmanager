package com.example.taskmanager.mappers;

import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.entity.Authority;
import com.example.taskmanager.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDTO toDto(UserEntity entityUser);
    UserEntity toEntity(UserDTO userDTO);

    default String[] mapToDTOAuthorities (Set<Authority> authorities) {

        if(authorities == null) return null;

        return authorities.stream()
                .map(Authority::getName)
                .toArray(String[]::new);
    }

    default Set<Authority> mapToEntityAuthorities (String[] authorities) {

        if(authorities == null) return null;

        return Arrays.stream(authorities)
                .map(authorityName -> new Authority(null, authorityName))
                .collect(Collectors.toSet());
    }
}