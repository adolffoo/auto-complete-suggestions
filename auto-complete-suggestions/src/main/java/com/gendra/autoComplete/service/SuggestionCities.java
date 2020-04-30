package com.gendra.autoComplete.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gendra.autoComplete.models.entity.City;
import com.gendra.autoComplete.models.entity.CityResponse;
import com.gendra.autoComplete.models.entity.Suggestions;

@Service
public class SuggestionCities {

	@Autowired
	ReadFileTSV tsvCities;

	private Suggestions suggestion = new Suggestions();
	private float distance;

	
	public CityResponse findSuggestions(String queryStr, Double latitude, Double longitude) {
		LinkedList<City> allCities  = new LinkedList<>();
		try {
			allCities = tsvCities.readTSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CityResponse response = new CityResponse();
		List<Suggestions> listSuggestions = new ArrayList<>();
		Comparator<Suggestions> comparator = Collections.reverseOrder();

		
		if(latitude != 0 && longitude != 0) {	
			
			findMatchedCities(queryStr, allCities).forEach(cities ->{
				distance= calculateDistance(latitude, Double.parseDouble(cities.getLatitude()), longitude, Double.parseDouble(cities.getLongitude()));	
				suggestion = new Suggestions();
				suggestion.setName(String.valueOf(cities.getCity()) + ", " + cities.getState() + ", " + cities.getCountry());
				suggestion.setLatitude(String.valueOf(cities.getLatitude()));
				suggestion.setLongitude(String.valueOf(cities.getLongitude()));
				suggestion.setScore(calculeScore(distance));
				listSuggestions.add(suggestion);
			});
			
			response.setSuggestions(listSuggestions);
		}else {
			findMatchedCities(queryStr, allCities).forEach(cities ->{
				suggestion = new Suggestions();
				suggestion.setName(String.valueOf(cities.getCity()) + ", " + cities.getState() + ", " + cities.getCountry());
				suggestion.setLatitude(String.valueOf(cities.getLatitude()));
				suggestion.setLongitude(String.valueOf(cities.getLongitude()));
				suggestion.setScore(0.0);
				listSuggestions.add(suggestion);
			});
			
			response.setSuggestions(listSuggestions);
		}
		
		Collections.sort(listSuggestions, comparator);
		return response;
		
	}
	
	/*Method that create a list with the cities matched with the suggestion*/
	public LinkedList<City>  findMatchedCities(String queryStr,LinkedList<City> allCities) {
		LinkedList<City> listSuggestions = new LinkedList<>();
		
		allCities.forEach(std -> {
			if (isMatched(queryStr, std.getCity())) {
				listSuggestions.add(std);
			}
			
		});
		
		return listSuggestions;
	}
	
	/*Haversine Method for determinated the great-circle distance between 
	 * two points on a spheregiven their longitudes and latitudes.*/
	public float calculateDistance(Double lat1, Double lat2, Double lon1, Double lon2) {

		double earthRadius = 6371; //kilometers
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	}

	/*Method searches the sequence of characters in the given string.*/
	public boolean isMatched(String query, String text) {
		return text.toLowerCase().contains(query.toLowerCase());
	}
	
	/*Method that calcule the score acording the distance between two point*/
	public Double calculeScore(Float distance) {
		Double score=0.0;
		double distanceRound =distance;
		
		if(distanceRound == 0.0) {
			score = 1.0;
		}else if(distanceRound > 0 && distanceRound <= 100){
			score = 0.9;
		}else if(distanceRound > 100 && distanceRound <= 200){
			score = 0.8;
		}else if(distanceRound > 200 && distanceRound <= 300){
			score = 0.7;
		}else if(distanceRound > 300 && distanceRound <= 400){
			score = 0.6;
		}else if(distanceRound > 400 && distanceRound <= 500){
			score = 0.5;
		}else if(distanceRound > 500 && distanceRound <= 600){
			score = 0.4;
		}else if(distanceRound > 600 && distanceRound <= 700){
			score = 0.3;
		}else if(distanceRound > 700 && distanceRound <= 800){
			score = 0.2;
		}else if(distanceRound > 800 && distanceRound <= 900){
			score = 0.1;
		}else if(distanceRound > 900){
			score = 0.0;
		}
		
		 return score;
	}

}
