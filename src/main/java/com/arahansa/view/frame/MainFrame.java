package com.arahansa.view.frame;





import com.arahansa.domain.MessageHolder;
import com.arahansa.domain.OneRowI18n;
import lombok.extern.slf4j.Slf4j;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.arahansa.view.panel.NorthPanel;
import com.arahansa.view.panel.SouthPanel;
import org.fife.ui.rsyntaxtextarea.*;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;


@Slf4j
@Component
public class MainFrame extends JFrame {

	@Autowired NorthPanel panelNorth;
	public void setPanelNorth(NorthPanel panelNorth) {
		this.panelNorth = panelNorth;
	}
	@Autowired SouthPanel panelSouth;
	public void setPanelSouth(SouthPanel panelSouth) {
		this.panelSouth = panelSouth;
	}
	@Autowired
	MessageHolder messageHolder;

	JPanel textPanel = new JPanel();
	RSyntaxTextArea textArea = new RSyntaxTextArea (500, 500);

	public void execute(){
		log.debug("init");

		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSP);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		textPanel.setLayout(new BorderLayout());
		textArea.setCodeFoldingEnabled(true);

		textPanel.add(sp);
		textPanel.setBounds(0, 0, 1000, 600);
		textPanel.setSize(1000, 600);

		add(panelNorth, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(200, 200, 1200, 800);
		setTitle("I18nParser");
	}

	// TODO : 메인프레임이 좀 커진 기분이다.. 알람창이 있어서 여기다가 우선 넣긴했다만..음..
	public void search(){
		List<OneRowI18n> oneRowI18nList = messageHolder.getOneRowI18nList();
		Loop : for(OneRowI18n n : oneRowI18nList){
			String text = n.getEng();
			textArea.setCaretPosition(0);
			SearchContext context = new SearchContext();
			context.setSearchFor(text);
			context.setSearchForward(true);
			context.setWholeWord(false);

			while(true){
				SearchResult searchResult = SearchEngine.find(textArea, context);
				if(!searchResult.wasFound())
					break;
				String textAreaText = textArea.getText();
				int startOffset = searchResult.getMatchRange().getStartOffset();
				int endOffset = searchResult.getMatchRange().getEndOffset();
				//log.debug("매치 래인지? :: {} , {} ",searchResult.getMatchRange().getStartOffset(),searchResult.getMatchRange().getEndOffset());
				String beforeString = textAreaText.substring(0, startOffset);
				String nextString = textAreaText.substring(endOffset);
				log.debug("before :: {}", startOffset);
				final int confirm = JOptionPane.showConfirmDialog(this, text+"가 발견되었습니다. \n 변경을 하실까요?");
				log.debug( " 컨펌 결과 : {} ", confirm);
				switch (confirm){
					case JOptionPane.YES_OPTION :
						textArea.setText(beforeString.concat("<spring:message code=\""+n.getKor()+"\" />").concat(nextString));
						textArea.setCaretPosition(startOffset+n.getKor().length());
						break;
					case JOptionPane.NO_OPTION : break;
					case JOptionPane.CANCEL_OPTION : break Loop;
				}
			}
		}
	}

	public void showMessage(String msg){
		log.debug("get Message :: {}", msg);
		JOptionPane.showMessageDialog(null, msg, "메시지창", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) {
		log.debug("main function excuted! ");
		MainFrame mainFrame = new MainFrame();
		mainFrame.setPanelNorth(new NorthPanel());
		mainFrame.setPanelSouth(new SouthPanel());
		mainFrame.execute();
	}

	public void initTextarea(){
		textArea.setText("");
	}
	public void appendTextArea(String msg){
		textArea.append(msg);
	}
	public void setCaratArea(int pos){
		textArea.setCaretPosition(pos);
	}


	public String getTextAreaString() {
		return textArea.getText();
	}

	public void setTextareaInit() {
		textArea.setText("");
		
	}
}
