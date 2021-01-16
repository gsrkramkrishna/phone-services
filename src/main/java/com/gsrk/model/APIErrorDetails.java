package com.gsrk.model;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIErrorDetails {
	private Date timestamp;
	private int status;
	private Set<String> details;
}
