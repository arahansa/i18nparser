package com.arahansa.view.frame;

import com.arahansa.service.MessageFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arahansa on 2015-11-10.
 */
@Slf4j
@Component
public class AlertClass extends JFrame{

    @Autowired
    MessageFilterService messageFilterService;

    JPanel jPanel = new JPanel();
    JButton jButton = new JButton("확인");

    JTextArea jta1 = new JTextArea(20, 30);
    JTextArea jta2 = new JTextArea(20, 30);

	public AlertClass() {
		
    }
    public  void init(){
        log.debug("setLayout");
        jPanel.setLayout(new GridLayout(2, 1));
        jta1.setBorder(BorderFactory.createLineBorder(Color.black));
        jta2.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanel.add(jta1);
        jPanel.add(jta2);
        log.debug("add !! ");
        add(jPanel, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
        jButton.addActionListener(e -> again());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setBounds(200, 200, 800, 600);
        setTitle("AlertClass");
        log.debug("set Title ");


    }

    private synchronized void again() {
        notify();
        dispose();
    }

    public static void main(String[] args) {
		new AlertClass().init();
	}

    public void setJtaMessage(String msg, boolean isUp){
        log.debug("set jta message ");
        if(isUp){
            jta1.append(msg);
        }else{
            jta2.append(msg);
        }
    }


}
