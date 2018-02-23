package com.gwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
	private int id;
    private String match_date;
    private int winner1;
    private int winner2;
    private int losser1;
    private int losser2;
    private int win_score;
    private int loss_score;
    private int points;
    
    public Match(String match_date,int winner1,int winner2,int losser1,int losser2,int win_score,int loss_score,int points) {
    	this.match_date = match_date;
    	this.winner1 = winner1;
    	this.winner2 = winner2;
    	this.losser1 = losser1;
    	this.losser2 = losser2;
    	this.win_score = win_score;
    	this.loss_score = loss_score;
    	this.points = points;
    }
}


