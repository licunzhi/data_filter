package com.study.data_filter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserMovieScore {
    private String userId;
    private String movieId;
    private int score;
    private String time;
}
