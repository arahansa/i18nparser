package com.arahansa.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arahansa.view.frame.AlertClass;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.arahansa.domain.MessageHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageFilterService {

	@Autowired MessageHolder messageHolder;
	List<String> filterStrings = Arrays.asList(",", "\n", "|", "/");
	@Autowired MessageService messageService;

	@Autowired
	ApplicationContext context;
	private boolean isWorking =true;
	
	public void setTempMap(Map<String, String> targetMap) {
		messageHolder.setTempMap(targetMap);
	}

	public synchronized void showTempMap() {
		Map<String, String> tempMap = messageHolder.getTempMap();
		Map<String, String> temp = new HashMap<>();
		log.debug("tempMap :: {}", tempMap);
		tempMap.forEach((k,v)->{
				if(thisHasFilterString(k)){
					log.debug(" maybe more than one : Key : ( {} ), value : ( {} ) ", k, v);
					AlertClass alertClass = context.getBean(AlertClass.class);
					alertClass.setJtaMessage(k, true);
					alertClass.setJtaMessage(v, false);
					alertClass.run();
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					log.debug(" good key value :: {} , {}", k, v);
					temp.put(k, v);
				}
		});
		log.debug(" 완전한 키밸류 \n {}", temp);
	}
	public synchronized void restart(){
		log.debug("restart !! ");
		notify();
	}

	private boolean thisHasFilterString(String targetString){
		boolean flag=false;
		for(String s : filterStrings){
			if(targetString.contains(s)){
				flag=true;
				break;
			}
		}
		return flag;
	}
}
