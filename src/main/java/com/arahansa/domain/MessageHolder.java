package com.arahansa.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MessageHolder {

	List<OneRowI18n> oneRowI18nList = new ArrayList<>();
	Set<String> checkDuplicateKeys = new HashSet<>();
	Set<String> checkDuplicateKeys2 = new HashSet<>();
	Set<String> duplicateKeyName = new HashSet<>();
	
	Map<String, String> checkDuplicateMap1 = new LinkedHashMap<>();
	Map<String, String> checkDuplicateMap2 = new LinkedHashMap<>();
	
	public void init() {
		oneRowI18nList = new ArrayList<>();
		checkDuplicateKeys = new HashSet<>();
		checkDuplicateKeys2 = new HashSet<>();
		duplicateKeyName = new HashSet<>();
		
		checkDuplicateMap1 = new LinkedHashMap<>();
		checkDuplicateMap2 = new LinkedHashMap<>();
	}
	
}
