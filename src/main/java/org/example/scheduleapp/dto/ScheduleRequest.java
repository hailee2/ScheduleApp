package org.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleRequest {

    private String title;
    private String author;
    private String content;
    private String password;
}
