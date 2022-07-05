package com.elca.internship.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PimToolClientApplication {

    public static void main(String[] args) {
        Application.launch(AppUI.class, args);
    }

}
