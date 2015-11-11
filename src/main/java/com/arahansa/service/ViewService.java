package com.arahansa.service;

import javax.annotation.PostConstruct;

import com.arahansa.view.frame.MainFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.arahansa.view.panel.NorthPanel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ViewService {

	public static final String SENTENCE_DELIMETER = "sentence.delimeter";

	@Autowired NorthPanel northPanel;
	@Autowired
	MainFrame mainFrame;
	@Autowired Environment env;

	private String sentence_delimeter;
	private String sentence_begin;
	private String sentence_end;
	
	@PostConstruct
	public void init(){
		sentence_delimeter = env.getProperty(SENTENCE_DELIMETER);
		sentence_begin = env.getProperty("sentence.begin");
		sentence_end = env.getProperty("sentence.end");
	}
	
	public String getSentenceDelimeter() {
		if(StringUtils.isEmpty(northPanel.getDelimeter())){
			return sentence_delimeter;
		}else{
			return northPanel.getDelimeter();
		}
	}
	public String getSentenceBegin(){
		// TODO : 뷰에서 앞글자, 뒷글자 정하게 해주자. . 지금은 귀찮으니..-_-
		return sentence_begin;
	}
	public String getSentenceEnd(){
		return sentence_end;
	}


	public void initTextArea(){
		mainFrame.initTextarea();
	}
	public void appendTextArea(String msg){
		mainFrame.appendTextArea(msg);
	}
	public void setCaretPos(int pos){
		mainFrame.setCaratArea(pos);
	}

	public String getTextAreaString(){
		return mainFrame.getTextAreaString();
	}


	


}
