package com.arahansa.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.arahansa.domain.OneRowI18n;
import com.arahansa.view.frame.AlertClass;
import javafx.scene.control.Alert;

import org.apache.commons.lang3.StringUtils;
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
	@Autowired ViewService viewService;
	@Autowired AlertClass alertClass;
	Set<String> duplicateResult = new HashSet<>(); 
	
	private boolean isWorking =true;

	int num=0;
	List<OneRowI18n> oneRowI18nList;
	List<OneRowI18n> temp = new ArrayList<>();

	public void showTempMap() {
		oneRowI18nList = messageHolder.getOneRowI18nList();
		log.debug("tempOneRowList:: {}", oneRowI18nList);

	}
	// 리스트 검사 추출  TODO 계속 작업..
	public void checkThoroughList(){
		for(;num<=oneRowI18nList.size();num++){
			OneRowI18n oneRowI18n = oneRowI18nList.get(num);
			if(thisHasFilterString(oneRowI18n)){
				alertClass.setJtaMessage(oneRowI18n.getKor(), true);
				alertClass.setJtaMessage(oneRowI18n.getEng(), false);
				alertClass.init();
				break;
			}else{
				log.debug(" good key-value :: {} ", oneRowI18n);
				temp.add(oneRowI18n);
			}
		}
		log.debug(" 완전한 키밸류 \n {}", temp);
		viewService.initTextArea();
		temp.forEach(v-> viewService.appendTextArea("="+v.getKor()+"\n") );
	}
	private boolean thisHasFilterString(OneRowI18n oneRowI18n){
		boolean flag=false;
		for(String s : filterStrings){
			if(oneRowI18n.getKor().contains(s)){
				flag=true;
				break;
			}
		}
		return flag;
	}

	// TEXT AREA 검색
	public void checkTextAreaWithi18nList() {

	}
	
	public void checkNoneExistKeys(){
		Map<String, String> checkDuplicateMap1 = messageHolder.getCheckDuplicateMap1();
		Map<String, String>  checkDuplicateMap2 = messageHolder.getCheckDuplicateMap2();
		viewService.appendTextArea("==============\n");
		checkDuplicateMap1.forEach((k,v)->{
			log.debug("Check Key : {} " , k);
			String isContainString = checkDuplicateMap2.get(k);
			if(isContainString==null){
				log.debug("found This Key in Only first file :: {} ", k);
				
				viewService.appendTextArea("Key : "+k+" : "+v+"\n");
			}
		});
	}
	
	
	public void checkExistKeys(){
		Map<String, String> checkDuplicateMap1 = messageHolder.getCheckDuplicateMap1();
		Map<String, String>  checkDuplicateMap2 = messageHolder.getCheckDuplicateMap2();
		viewService.appendTextArea("==============\n");
		checkDuplicateMap1.forEach((k,v)->{
			log.debug("Check Key : {} " , k);
			String isContainString = checkDuplicateMap2.get(k);
			if(isContainString!=null){
				log.debug("found This Key in Only first file :: {} ", k);
				
				viewService.appendTextArea("Key : "+k+" : "+v+"\n");
			}
		});
	}
	
	public void initHolder() {
		viewService.setTextareaInit();
		messageHolder.init();
		
	}
	public void checkNoneExistValue() {
		Map<String, String> checkDuplicateMap1 = messageHolder.getCheckDuplicateMap1();
		viewService.appendTextArea("==============\n");
		checkDuplicateMap1.forEach((k,v)->{
			if(StringUtils.isEmpty(v)){
				viewService.appendTextArea("Key : "+k+" : "+v+"\n");
			}
		});
	}


}
