package com.elca.internship.client.controllers;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.models.entity.Response;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@FxmlView("/views/alertDanger.fxml")
@RequiredArgsConstructor
public class AlertDangerController implements Initializable {
    public HBox alertDanger;
    public Label lbAlertDanger;
    public HBox hboxIcon;
    private final I18nManager i18nManager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
        var iconNode = new IconNode(GoogleMaterialDesignIcons.CLOSE);
        hboxIcon.getChildren().add(iconNode);
        iconNode.getStyleClass().add("icon-node-close");
        iconNode.setOnMouseClicked(e -> {
            alertDanger.setVisible(false);
        });
    }

    public void setContentAndShowAlertLabel(Response response){
        var msg = findErrorType(response);
        lbAlertDanger.setText(msg);
        alertDanger.setVisible(true);
    }

    private String findErrorType(Response response) {
        var msg = "";
        if(response.getTypeError() == 1){
            msg = i18nManager.text(I18nKey.RESPONSE_ERROR_PROJECT_NUMBER);
        }else if(response.getTypeError() == 2){
            var startIdx = response.getStatusMsg().lastIndexOf("[");
            var listMemberNotExisted = "{" + response.getStatusMsg().substring(startIdx + 1, response.getStatusMsg().length() - 2) + "}";
            msg = i18nManager.text(I18nKey.RESPONSE_ERROR_PROJECT_MEMBERS,listMemberNotExisted);
        } else{
            msg = i18nManager.text(I18nKey.RESPONSE_ERROR_PROJECT_GROUP);
        }
        return msg;
    }
}
