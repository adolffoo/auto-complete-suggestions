package com.gendra.autoComplete.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gendra.autoComplete.models.entity.CityResponse;

@Service
public class CityServiceImpl  implements ICityService {

	@Autowired 
	private SuggestionCities suggestions;
	
	
	public CityResponse findSuggestionCities(String queryStr, Double latitude, Double longitude) {
		return suggestions.findSuggestions(queryStr, latitude, longitude);
	}

}
