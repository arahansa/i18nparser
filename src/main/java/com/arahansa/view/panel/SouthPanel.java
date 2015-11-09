package com.arahansa.view.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arahansa.service.MessageFilterService;
import com.arahansa.service.MessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by arahansa on 2015-11-10.
 */
@Slf4j
@Component
public class SouthPanel extends JPanel  implements ActionListener{

	@Autowired MessageService messageService;
	@Autowired
	MessageFilterService messageFilterService;
	
	private JButton jbt_firstCheck = new JButton("파일 첫번째 검사");

	
	public SouthPanel() {
		this.init();
	}
	public void init(){
		setLayout(new FlowLayout());
		add(jbt_firstCheck);
		jbt_firstCheck.addActionListener(e->{
			messageService.showMessage("검사를 시작합니다.");
			messageFilterService.showTempMap();
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	

}
