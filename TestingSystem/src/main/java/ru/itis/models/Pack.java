package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Pack {
    private Long id;
    private Long taskId, userId;
    private String date;
    private String lang, code, result, message;
    private Integer score;
}
