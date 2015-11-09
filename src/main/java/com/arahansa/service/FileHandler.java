package com.arahansa.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileHandler {
	
	@Autowired
	MessageFilterService messageFilterService;
	@Autowired MessageService messageService;
	@Autowired ViewService viewService;
	// stringBuilder 는 쓰레드안전하지않다. 성능상이점. 이것은 멀티쓰레드 프로그램이 아니다.
	StringBuilder keyBuffer = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();
	private static final int START=0;
	private static final int AFTER_DELIMETER=1;
	private static final int END=2;
	private static int STATUS=0;

	public void openFileToMessageHolder(String fileName) {
		log.debug("들어온 파일명 :: {} ", fileName);
		Map<String, String> targetMap = new HashMap<>();
		// 자바 7 방식의 Autoclosable
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
					targetMap.put(keyBuffer.toString(), valueBuffer.toString());
					log.debug("Key :: {} , value :: {}", keyBuffer.toString(), valueBuffer.toString());
					keyBuffer.setLength(0);
					valueBuffer.setLength(0);
				}
				switch (STATUS){
					case START: keyBuffer.append((char)data); break;
					case AFTER_DELIMETER : valueBuffer.append((char)data);break;
				}
			}
			messageFilterService.setTempMap(targetMap);
			// TODO : JAVA7 AUTOCLOSABLE 더 공부.
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}
}
