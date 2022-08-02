package com.elca.internship.client.controllers;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.models.entity.Response;
import com.elca.internship.client.exception.ErrorResponseKey;
import javafx.fxml.FXML;
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
    @FXML
    public HBox alertDanger;
    @FXML
    public Label lbAlertDanger;
    @FXML
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

    public void showErrorAlertLabel(ErrorResponseKey errorResponseKey, String errorResponseValue){
        lbAlertDanger.setText(i18nManager.text(errorResponseKey, errorResponseValue));
        alertDanger.setVisible(true);

    }
}
