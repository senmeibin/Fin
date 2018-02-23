package com.gwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinPer {
    private int id;
    private String name;
    private int total_win_match;
    private int total_loss_match;
    private int total_match;
    private Double win_per;
}
