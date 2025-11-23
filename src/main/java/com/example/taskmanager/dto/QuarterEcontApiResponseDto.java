package com.example.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuarterEcontApiResponseDto {

    private List<SingleQuarter> quarterList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SingleQuarter {
        private Long quarterId;
        private Long cityId;
        private String quarter;
        private String quarterNameInEnglish;
    }
}