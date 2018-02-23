package com.gwb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
	private int id;
    private String match_date;
    private int winner1;
    private String winner1Name;
    private int winner2;
    private String winner2Name;
    private int losser1;
    private String losser1Name;
    private int losser2;
    private String losser2Name;
    private int win_score;
    private int loss_score;
    private int points;
}
