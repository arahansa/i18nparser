package com.arahansa.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MessageHolder {

	List<OneRowI18n> oneRowI18nList = new ArrayList<>();

}
