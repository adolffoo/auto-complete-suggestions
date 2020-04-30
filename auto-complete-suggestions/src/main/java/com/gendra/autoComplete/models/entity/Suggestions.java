package com.gendra.autoComplete.models.entity;

public class Suggestions implements java.lang.Comparable<Suggestions> {
	
	private String name;
	private String latitude;
	private String longitude;
	private Double score;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(Suggestions sugges) {
		 if (score != null && sugges.getScore() != null) {
	            return score.compareTo(sugges.getScore());
	        }
		return 0;
	}
	
	
}
