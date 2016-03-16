package com.arahansa.view.panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.arahansa.view.frame.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arahansa.service.MessageFilterService;
import com.arahansa.service.MessageService;
import com.arahansa.service.ViewService;

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
	@Autowired
	MainFrame mainFrame;
	@Autowired ViewService viewService;
	
	private JButton jbt_firstCheck = new JButton(" [1] 첫번째  파일 검사");
	private JLabel jbl_fileCheck = new JLabel(" :: 파일검사는 엑셀추출txt에서 다시 불량필드들을 추출하려는 용도이나 미구현..");
	private JButton jbt_secondCheck = new JButton("[3]properties 파일 검사");
	private JLabel jbl_secondCheck = new JLabel(" :: 다국어 properties의 키값을 일어들여서 스프링 메세지 코드로 변환한다.");
	private JButton jbt_duplicateCheck = new JButton("[4] 중복 키 체크 ");
	private JButton jbt_duplicateCheck2 = new JButton("[5] 왼쪽->오른쪽 파일: 없는 키 표시 ");
	private JButton jbt_duplicateCheck3 = new JButton("[5] 왼쪽->오른쪽 파일: 있는 키 표시 ");
	private JButton jbt_duplicateCheck4 = new JButton("[6] 빈 값 표시 ");
	private JButton jbt_init = new JButton("초기화");

	JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	
	public SouthPanel() {
		this.init();
	}
	public void init(){
		setLayout(new GridLayout(2,1));
		firstPanel.add(jbt_firstCheck);
		firstPanel.add(jbl_fileCheck);
		firstPanel.add(jbt_duplicateCheck);
		firstPanel.add(jbt_duplicateCheck2);
		firstPanel.add(jbt_duplicateCheck3);
		firstPanel.add(jbt_duplicateCheck4);
		secondPanel.add(jbt_secondCheck);
		secondPanel.add(jbl_secondCheck);
		secondPanel.add(jbt_init);
		add(firstPanel);
		add(secondPanel);
		jbt_firstCheck.addActionListener(e->{
			messageService.showMessage("검사를 시작합니다.");
			messageFilterService.showTempMap();
			messageFilterService.checkThoroughList();
		});
		jbt_secondCheck.addActionListener(e->{
			messageService.showMessage("properties 검사를 시작합니다.");
			messageFilterService.showTempMap();
			messageFilterService.checkTextAreaWithi18nList();
			mainFrame.search();
		});
		jbt_duplicateCheck.addActionListener(e->{
			viewService.showDuplicateKeys();
		});
		jbt_duplicateCheck2.addActionListener(e->{
			messageFilterService.checkNoneExistKeys();
		});
		jbt_duplicateCheck3.addActionListener(e->{
			messageFilterService.checkExistKeys();
		});
		jbt_duplicateCheck4.addActionListener(e->{
			messageFilterService.checkNoneExistValue();
		});
		jbt_init.addActionListener(e->{
			messageFilterService.initHolder();
		});
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	

}
