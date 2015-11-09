package com.arahansa;

import com.arahansa.config.ApplicationContext;
import com.arahansa.view.frame.MainFrame;



import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by arahansa on 2015-11-10.
 */

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationContext.class);
        MainFrame parser = ac.getBean(MainFrame.class);
        parser.execute();
    }
}
