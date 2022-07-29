package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/errorPage.fxml")
@RequiredArgsConstructor
public class ErrorPageController implements Initializable, ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    @FXML
    public Label lbErrorContent;
    @FXML
    public Label lbErrorPlease;
    @FXML
    public Label lbBtnErrorContact;
    @FXML
    public Label lbErrorOr;
    @FXML
    public Label lbBtnErrorBackToSearch;
    @FXML
    public HBox parentLayoutErrorPage;

    @FXML
    private ImageView imgError;
    private final I18nManager i18nManager;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane.setTopAnchor(parentLayoutErrorPage,0.0);
        AnchorPane.setBottomAnchor(parentLayoutErrorPage,0.0);
        AnchorPane.setLeftAnchor(parentLayoutErrorPage,25.0);
        AnchorPane.setRightAnchor(parentLayoutErrorPage,200.0);
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    public void setMsgError(String msgError) {
        lbErrorContent.setText(msgError);
    }

    public void showMsgError(I18nKey i18nKey){
        lbErrorContent.setText(i18nManager.text(i18nKey));
    }
}
