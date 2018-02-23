package com.gwb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private int id;
    private String name;
    private String match_date;
    private int win_match;
    private int loss_match;
    private int get_points;
    private int total_points;
    private int rank;
}
