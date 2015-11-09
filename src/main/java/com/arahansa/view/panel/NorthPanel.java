package com.arahansa.view.panel;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arahansa.service.FileHandler;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


@Slf4j
@Component
public class NorthPanel extends JPanel implements ActionListener {
	@Autowired
	FileHandler fileHandler;
	
	// 참고&복붙한 부분 :
	// http://blog.naver.com/PostView.nhn?blogId=cracker542&logNo=40119977325&categoryNo=7&viewDate=&currentPage=1&listtype=0
	
	private JFileChooser jfc = new JFileChooser();
	private JButton jbt_open = new JButton("열기");
	private JButton jbt_save = new JButton("저장");
	private JLabel jta_delimiter = new JLabel("초기 파일 구분자 : ");
	private JTextField jtf_delimiter = new JTextField(3);
	private JLabel jlb = new JLabel(" ");

	public NorthPanel() {
		log.debug("init");
		this.init();
		this.start();
	}

	public void init() {
		setLayout(new FlowLayout());
		add(jbt_open);
		add(jbt_save);
		add(jta_delimiter);
		add(jtf_delimiter);
		add(jlb);
	}

	public void start() {
		jbt_open.addActionListener(this); // 람다로 바꿀 수 있지만 액션은 액션끼리 빼자.
		jbt_save.addActionListener(this);
		jfc.setFileFilter(new FileNameExtensionFilter("txt", "txt")); // 파일 필터
		jfc.setMultiSelectionEnabled(false);// 다중 선택 불가
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
