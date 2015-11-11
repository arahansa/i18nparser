package com.arahansa;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arahansa on 2015-11-10.
 */
public class ThreadWaitTest extends JFrame implements  Runnable{

    JLabel jlabel = new JLabel("0");
    JButton jButton = new JButton("notify!");

    public ThreadWaitTest(){
        jlabel.setFont(new Font("돋움", Font.PLAIN, 50));
        add(jlabel, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
        jButton.addActionListener(e->restart());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(200, 200, 600, 400);
        setTitle("I18nParser");
    }
    public static void main(String[] args) {
        new ThreadWaitTest().run();
    }
    @Override
    public void run() {
        timeLabel();
    }
    public synchronized  void restart(){
        notify();
    }
    public synchronized void timeLabel(){
        for(int i=0;i<10;i++){
            jlabel.setText(i+"");
            try {
                if(i==5){ wait(); }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
