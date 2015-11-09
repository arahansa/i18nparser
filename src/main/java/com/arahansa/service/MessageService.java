package com.arahansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.arahansa.view.frame.MainFrame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageService {
	
	
	@Autowired MainFrame mainFrame;
	public void showMessage(String msg) {
		mainFrame.showMessage(msg);
	}

}
