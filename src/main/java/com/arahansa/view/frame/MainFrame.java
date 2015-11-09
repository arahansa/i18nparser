package com.arahansa.view.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.arahansa.view.panel.NorthPanel;
import com.arahansa.view.panel.SouthPanel;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MainFrame extends JFrame{

	@Autowired
	NorthPanel panelNorth;
	@Autowired
	SouthPanel panelSouth;
	

	public void execute(){
		log.debug("init");
		add(panelNorth, BorderLayout.NORTH);
		add(panelSouth, BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 200, 1200, 800);
		setTitle("I18nParser");
	}
	
	public void showMessage(String msg){
		log.debug("get Message :: {}", msg);
		JOptionPane.showMessageDialog(null, msg, "메시지창", JOptionPane.INFORMATION_MESSAGE);
		
	}


}
