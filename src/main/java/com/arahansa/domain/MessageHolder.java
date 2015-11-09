package com.arahansa.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MessageHolder {
	
	Map<String, String> resultMap = new HashMap<>();
	Map<String, String> tempMap = new HashMap<>();

}
