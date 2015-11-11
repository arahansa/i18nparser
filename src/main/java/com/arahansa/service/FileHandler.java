package com.arahansa.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arahansa.domain.MessageHolder;
import com.arahansa.domain.OneRowI18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileHandler {
	

	@Autowired MessageService messageService;
	@Autowired ViewService viewService;
	@Autowired MessageHolder messageHolder;

	// stringBuilder 는 쓰레드안전하지않다. 성능상이점. 이것은 멀티쓰레드 프로그램이 아니다.
	StringBuilder keyBuffer = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();

	private static final int START=0;
	private static final int AFTER_DELIMETER=1;
	private static final int END=2;
	private static int STATUS=0;

	public void openFileToMessageHolder(String fileName) {
		log.debug("들어온 파일명 :: {} ", fileName);
		List<OneRowI18n> oneRowI18nList = new ArrayList<>();
		// 자바 7 방식의 Autoclosable
		int number=0;
		try(FileReader fr = new FileReader(fileName)) {
			int data;
			while((data=fr.read())!=-1){
				if(((char)data)=='{'){
					data=fr.read();
					STATUS=START;
				}else if(((char)data)=='^'){
					data=fr.read();
					STATUS=AFTER_DELIMETER;
				}else if(((char)data)=='}'){
					STATUS=END;
					oneRowI18nList.add(new OneRowI18n(number, keyBuffer.toString(), valueBuffer.toString()));
					number++;
					log.debug("Key :: {} , value :: {}", keyBuffer.toString(), valueBuffer.toString());
					keyBuffer.setLength(0);
					valueBuffer.setLength(0);
				}
				switch (STATUS){
					case START: keyBuffer.append((char)data); break;
					case AFTER_DELIMETER : valueBuffer.append((char)data);break;
				}
			}
			messageHolder.setOneRowI18nList(oneRowI18nList);
			// TODO : JAVA7 AUTOCLOSABLE 더 공부.
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}
}
