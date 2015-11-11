package com.arahansa.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.arahansa.domain.OneRowI18n;
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
	@Autowired AlertClass alertClass;
	private boolean isWorking =true;

	int num=0;
	List<OneRowI18n> oneRowI18nList;
	List<OneRowI18n> temp = new ArrayList<>();

	public void showTempMap() {
		oneRowI18nList = messageHolder.getOneRowI18nList();
		log.debug("tempOneRowList:: {}", oneRowI18nList);
		checkThoroughList();
	}

	private void checkThoroughList(){
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
}
