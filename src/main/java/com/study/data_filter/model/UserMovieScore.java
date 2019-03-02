package com.study.data_filter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMovieScore {
    private String userId;
    private String movieId;
    private int score;
    private String time;
}
