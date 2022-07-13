package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.Utils.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/errorPage.fxml")
@RequiredArgsConstructor
public class ErrorPageController implements Initializable, ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    @FXML
    private Label lbErrorContent;
    @FXML
    private Label btnContact;
    @FXML
    private Label btnBackToSearch;
    @FXML
    private ImageView imgError;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    public void setMsgError(String msgError) {
        lbErrorContent.setText(msgError);
    }
}
