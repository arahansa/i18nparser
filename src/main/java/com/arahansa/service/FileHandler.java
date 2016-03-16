package com.arahansa.service;

import java.io.*;
import java.util.*;

import com.arahansa.domain.MessageHolder;
import com.arahansa.domain.OneRowI18n;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class FileHandler {
	@Autowired MessageService messageService;
	@Autowired ViewService viewService;
	@Autowired MessageHolder messageHolder;


	// stringBuilder 는 쓰레드안전하지않다. 성능상이점. 이것은 멀티쓰레드 프로그램이 아니다.
	StringBuilder keyBuffer = new StringBuilder();
	StringBuilder valueBuffer = new StringBuilder();
	
	Set<String> checkDuplicateKeys = new HashSet<>();
	Set<String> duplicateKeyName = new HashSet<>();

	private static final int START=0;
	private static final int AFTER_DELIMETER=1;
	private static final int END=2;
	private static int STATUS=0;

	public void openFileToMessageHolder(String fileName) {
		log.debug("들어온 파일명 :: {} ", fileName);
		List<OneRowI18n> oneRowI18nList = new ArrayList<>();
		// 자바 7 방식의 Autoclosable
		int number=0;
		try(BufferedReader fr= new BufferedReader(new FileReader(fileName))) {
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
	// TODO : 읽기 파일별 전략? 설정 ?
	public void openPropertiesFileToMessageHolder(String fileName) throws FileNotFoundException {
		List<OneRowI18n> oneRowI18nList = new ArrayList<>();
		Properties props = new Properties();
		InputStream is = new FileInputStream(fileName);
		try {
			props.load(new BufferedReader(new InputStreamReader(is, "UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("properties 파일 인코딩에러");
		}
		props.forEach((key,value)->{
			log.debug("Key :: {} , Value :: {} ", key, value);
			oneRowI18nList.add(new OneRowI18n(((String)key).trim(), ((String)value).trim()));
		});
		log.debug("들어온 파일명 :: {} ", fileName);
		messageHolder.setOneRowI18nList(oneRowI18nList);
		messageService.showMessage("읽기가 완료되었습니다" );
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 자바 7 방식의 Autoclosable 공부할것.
		/*try(BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String data;
			while((data=in.readLine())!=null){
				String key = data.substring(0, data.indexOf("="));
				String value = data.substring(data.indexOf("=") + 1);
				log.debug("Key :: {} , Value :: {} ", key, value);
				oneRowI18nList.add(new OneRowI18n(key.trim(), value.trim()));
			}
			messageHolder.setOneRowI18nList(oneRowI18nList);
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}*/
	}
	
	// 한 줄 씩 파일을 읽는 소스
	public void openPropertiesFileToMessageHolderByOneLine(String fileName){
		List<OneRowI18n> oneRowI18nList = new ArrayList<>();
		Map<String, String> checkDuplicateMap1 = new HashMap<>();
		try(BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String data;
			while((data=in.readLine())!=null){
				log.debug("value :: {}\" ", data);
				log.debug("value :: {}\" ", data.getBytes().length);
				if(!StringUtils.startsWithAny(data, "=", ">", "<", "#", " ", System.getProperty("line.separator")) && data.getBytes().length !=0 ){
					String key = data.substring(0, data.indexOf("="));
					String value = data.substring(data.indexOf("=") + 1);
					log.debug("Key :: {} , Value :: {} ", key, value);
					oneRowI18nList.add(new OneRowI18n(((String)key).trim(), ((String)value).trim()));
					
					// 중복체크 set으로
					int size = checkDuplicateKeys.size();
					String trimKey = ((String)key).trim();
					String trimValue = ((String)value).trim();
					checkDuplicateKeys.add(trimKey);
					checkDuplicateMap1.put(trimKey, trimValue);
					
					if(size == checkDuplicateKeys.size()){
						log.debug("중복 발생 {} ", ((String)key).trim());
						duplicateKeyName.add(((String)key).trim());
					}
				}
			}
			messageHolder.setOneRowI18nList(oneRowI18nList);
			messageHolder.setCheckDuplicateKeys(checkDuplicateKeys);
			messageHolder.setCheckDuplicateMap1(checkDuplicateMap1);
			messageHolder.setDuplicateKeyName(duplicateKeyName);
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}
	
	// 한 줄 씩 파일을 읽는 소스2
	public void openPropertiesFileToMessageHolderByOneLine4SecondFile(String fileName){
		List<OneRowI18n> oneRowI18nList = new ArrayList<>();
		Set<String> checkDuplicateKeys2 = new HashSet<>();
		Map<String, String> checkDuplicateMap2 = new HashMap<>();
		try(BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String data;
			while((data=in.readLine())!=null){
				log.debug("value :: {}\" ", data);
				log.debug("value :: {}\" ", data.getBytes().length);
				if(!StringUtils.startsWithAny(data, "=", ">", "<", "#", " ", System.getProperty("line.separator")) && data.getBytes().length !=0 ){
					String key = data.substring(0, data.indexOf("="));
					String value = data.substring(data.indexOf("=") + 1);
					log.debug("Key :: {} , Value :: {} ", key, value);
					oneRowI18nList.add(new OneRowI18n(((String)key).trim(), ((String)value).trim()));
					
					// 중복체크 set으로
					int size = checkDuplicateKeys2.size();
					String trimKey = ((String)key).trim();
					String trimValue = ((String)value).trim();
					checkDuplicateKeys.add(trimKey);
					checkDuplicateMap2.put(trimKey, trimValue);
					if(size == checkDuplicateKeys2.size()){
						log.debug("중복 발생 {} ", ((String)key).trim());
						duplicateKeyName.add(((String)key).trim());
					}
				}
			}
			// messageHolder.setOneRowI18nList(oneRowI18nList);
			// 여기가 중요 여기서는 두번째 키로 저장을 한다 
			messageHolder.setCheckDuplicateKeys2(checkDuplicateKeys2);
			// messageHolder.setDuplicateKeyName(duplicateKeyName);
			messageHolder.setCheckDuplicateMap2(checkDuplicateMap2);
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}

	public void openFileFillJTextArea(String fileName) {
		String data;
		viewService.initTextArea();
		try(BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			while((data=in.readLine())!=null){
				viewService.appendTextArea(data + "\n");
			}
			viewService.setCaretPos(0);
			messageService.showMessage("읽기가 완료되었습니다" );
		} catch (IOException e) {
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}

	public void saveFileFillJTextArea(String fileName) {
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fileName))){
			out.write(viewService.getTextAreaString());
			messageService.showMessage("쓰기 완료! " );
		}catch (IOException e){
			e.printStackTrace();
			messageService.showMessage("입출력 에러입니다 :: "+e.getMessage());
		}
	}
}
