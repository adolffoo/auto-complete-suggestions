package com.gendra.autoComplete.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.gendra.autoComplete.models.entity.City;


@Service
public class ReadFileTSV {

	private LinkedList<City> cities = new LinkedList<>();
	
	
	/**
     * Method for read TSV file that containst cities
	 * @throws IOException 
     */
	public LinkedList<City> readTSV() throws IOException{
		Resource resource = new ClassPathResource("/cities_canada-usa.tsv");		
		InputStream file = resource.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		
		if(cities.size() <= 0) {
			while(reader.ready()) {
			
				City city = new City();
				try{
					loadFromTSV(city, reader.readLine());
					cities.add(city);
				}
				catch(Exception e){
				}
			}
		}
		
		return cities;
	}
	
	 /**
     * TSV load data
     * @param workbook Workook to work with Excel
     */
	public void loadFromTSV(City city, String line){
		String[] tokens = line.split("\t");
		int token_index = 0;
		for(String token : tokens){
			switch(token_index){
			case 0 : //id
				city.setIdCity(Double.parseDouble(token));
				break;
			case 1 : //name
				city.setCity(token);
				break;
			case 2 : //ascii
				break;
			case 3: //alt_name 
				break;
			case 4: //lat
				city.setLatitude(token);
				break;
			case 5: //long
				city.setLongitude(token);
				break;
			case 6: //feat_class
				break;
			case 7: //feat_code
				break;
			case 8: //country
				if(token.equals("CA")){
					token = "Canada";
				}
				if(token.equals("US")){
					token = "USA";
				}
				city.setCountry(token);
				break;
			case 9: //cc2
				break;
			case 10: //admin1
				city.setState(token);
				break;
			}
			token_index++;
		}	
	}
	
	
}
