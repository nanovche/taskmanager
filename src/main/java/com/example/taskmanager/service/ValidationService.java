package com.example.taskmanager.service;

import com.example.taskmanager.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.passay.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ValidationService {

    void validateUserInput(UserDTO dtoUser) {

        if (dtoUser == null) {
            throw new IllegalArgumentException("User DTO is null");
        }
        if (StringUtils.isEmpty(dtoUser.getUsername()) || StringUtils.isEmpty(dtoUser.getPassword())) {
            throw new IllegalArgumentException("Missing username or password");
        }
        if (dtoUser.getAuthorities() == null || dtoUser.getAuthorities().length == 0) {
            throw new IllegalArgumentException("No authorities data");
        }
    }

    boolean checkPasswordStrength(String password){

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 20),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
}
