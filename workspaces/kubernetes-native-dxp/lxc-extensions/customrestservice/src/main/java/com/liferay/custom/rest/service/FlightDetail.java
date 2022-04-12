package com.liferay.custom.rest.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class FlightDetail {
	
	public String totalDuration;
	public Leg[] legs;

	@JsonAutoDetect(fieldVisibility = Visibility.ANY)
	public static class Leg {

		public String airline;
		public String departureCode;
		public String arrivalCode;
		public String duration;
		
	}
}
