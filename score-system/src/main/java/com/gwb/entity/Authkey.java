package com.gwb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authkey {
	private String key;
	private Boolean matchEntryAuth;
	private Boolean AdminAuth;
}
