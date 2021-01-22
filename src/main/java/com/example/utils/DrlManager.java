package com.example.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DrlManager {
	
	private static Map<String, String> drls = new HashMap<>();
	
	public static void insertDrl(String key, String value) {
		drls.put( key, value );
	}
	
	public static String getDrl(String key) {
		return drls.get(key);
	}
	
	public static List<String> getKeys(){
		return drls.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
	}
	
	public static Map<String, String> getAllDrls(){
		return drls;
	}

}
