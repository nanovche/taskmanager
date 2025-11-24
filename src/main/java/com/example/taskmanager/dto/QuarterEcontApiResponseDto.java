package com.example.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("quarters")
    private List<SingleQuarter> quarterList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SingleQuarter {

        @JsonProperty("id")
        private Long quarterId;
        @JsonProperty("cityID")
        private Long cityId;
        @JsonProperty("name")
        private String quarter;
        @JsonProperty("nameEn")
        private String quarterNameInEnglish;
    }
}