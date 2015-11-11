package com.arahansa.view.panel;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arahansa.service.FileHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


@Slf4j
@Component
public class NorthPanel extends JPanel implements ActionListener {
	@Autowired
	FileHandler fileHandler;
	
	// 참고&복붙한 부분 :
	// http://blog.naver.com/PostView.nhn?blogId=cracker542&logNo=40119977325&categoryNo=7&viewDate=&currentPage=1&listtype=0


	JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	//-----first Panel
	final private JFileChooser jfc = new JFileChooser();
	final private JFileChooser jfc_jsp = new JFileChooser();
	private JButton jbt_open = new JButton("열기");
	private JButton jbt_save = new JButton("저장");
	private JLabel jta_delimiter = new JLabel("초기 파일 구분자 : ");
	private JTextField jtf_delimiter = new JTextField(3);
	private JLabel jlb = new JLabel(" ");

	//--- secondPanel
	private JButton jbt_properties= new JButton("[1] properties읽기");
	private JLabel jlb_properties = new JLabel(" ");
	private JButton jbt_jsp= new JButton("[2]jsp읽기");
	private JButton jbt_save_jsp = new JButton("[4]jsp저장");
	private JLabel jlb_jsp = new JLabel(" ");

	public NorthPanel() {
		log.debug("init");
		this.init();
		this.start();
	}

	public void init() {
		setLayout(new GridLayout(2, 1));
		firstPanel.add(jbt_open);
		firstPanel.add(jbt_save);
		firstPanel.add(jta_delimiter);
		firstPanel.add(jtf_delimiter);
		firstPanel.add(jlb);
		add(firstPanel);

		secondPanel.add(jbt_properties);
		secondPanel.add(jlb_properties);
		secondPanel.add(jbt_jsp);
		secondPanel.add(jbt_save_jsp);
		secondPanel.add(jlb_jsp);
		add(secondPanel);
	}

	public void start() {
		jbt_open.addActionListener(this); // 람다로 바꿀 수 있지만 액션은 액션끼리 빼자.
		jbt_save.addActionListener(this);
		jfc.setFileFilter(new FileNameExtensionFilter("확장자들", "txt",  "properties", "jsp")); // 파일 필터
		jfc.setMultiSelectionEnabled(false);// 다중 선택 불가

		jbt_properties.addActionListener(e->{
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
				jlb_properties.setText("읽기 경로 : " + jfc.getSelectedFile().toString() );
				try {
					fileHandler.openPropertiesFileToMessageHolder(jfc.getSelectedFile().toString());
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(this, "파일을 찾을 수 없다네요");
				}
			}
		});
		jbt_jsp.addActionListener(e->{
			if (jfc_jsp.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
				jlb_jsp.setText("읽기 경로 : " + jfc_jsp.getSelectedFile().toString() );
				fileHandler.openFileFillJTextArea(jfc_jsp.getSelectedFile().toString());
			}
		});
		jbt_save_jsp.addActionListener(e->{
			if (jfc_jsp.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
				jlb_jsp.setText("저장 경로 : " + jfc_jsp.getSelectedFile().toString() );
				fileHandler.saveFileFillJTextArea(jfc_jsp.getSelectedFile().toString());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == jbt_open) {
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
				jlb.setText("열기 경로 : " + jfc.getSelectedFile().toString());
				fileHandler.openFileToMessageHolder(jfc.getSelectedFile().toString());
			}
		} else if (arg0.getSource() == jbt_save) {
			if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
				jlb.setText("저장 경로 : " + jfc.getSelectedFile().toString() + "." + jfc.getFileFilter().getDescription());
			}
		}
	}


	public String getDelimeter(){
		return jtf_delimiter.getText();
	}

}
