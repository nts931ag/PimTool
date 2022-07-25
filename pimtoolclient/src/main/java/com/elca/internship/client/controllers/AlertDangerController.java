package com.elca.internship.client.controllers;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.models.entity.Response;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.AllArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@FxmlView("/views/alertDanger.fxml")
@AllArgsConstructor
public class AlertDangerController implements Initializable {
    public HBox alertDanger;
    public Label lbAlertDanger;
    public HBox hboxIcon;
    private final I18nKey i18nKey;

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

    public void setContentAndShowAlertLabel(Response e){


        lbAlertDanger.setText(e.getStatusMsg());
        alertDanger.setVisible(true);

    }
}
