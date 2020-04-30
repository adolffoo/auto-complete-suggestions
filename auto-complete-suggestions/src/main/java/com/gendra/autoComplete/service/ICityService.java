package com.gendra.autoComplete.service;

import com.gendra.autoComplete.models.entity.CityResponse;


public interface ICityService {

	public CityResponse findSuggestionCities(String queryStr, Double longitude, Double latitude);
}
