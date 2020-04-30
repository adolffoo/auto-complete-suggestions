package com.gendra.autoComplete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gendra.autoComplete.models.entity.CityResponse;
import com.gendra.autoComplete.service.ICityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@Controller
@Api(value="Auto-complete suggestions System")
public class CityController {

	@Autowired
	private ICityService cities;
	
	@ApiOperation(value = "List of suggestions from partial (or complete) search term")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list of suggestions"),
	    @ApiResponse(code = 404, message = "The term you were trying to reach is not found")
	})
	@GetMapping("/suggestions")
	public ResponseEntity<?> getSuggestions(@ApiParam(value = "Partial (or complete) search term", required = true)
											@RequestParam(name = "q", required = true) String qstr,
											@ApiParam(value = "Caller's latitude location", required = false)
											@RequestParam(name = "latitude", required = false, defaultValue = "0") Double latitude,
											@ApiParam(value = "Caller's longitude location", required = false)
											@RequestParam(name = "longitude", required = false,  defaultValue = "0") Double longitude){
		CityResponse response = new CityResponse();
		response = cities.findSuggestionCities(qstr, latitude, longitude);
		
		if(response.getSuggestions().size() > 0) {
			return new ResponseEntity<CityResponse>(response, HttpStatus.OK);
		}else {
			return new ResponseEntity<CityResponse>(response, HttpStatus.NOT_FOUND) ;
		}
		
	}
}
