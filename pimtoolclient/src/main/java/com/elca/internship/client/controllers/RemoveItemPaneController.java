package com.elca.internship.client.controllers;

import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/removeItemPane.fxml")
@RequiredArgsConstructor
public class RemoveItemPaneController implements Initializable {
    public Label lbItemQuantity;
    public Label lbDeleteItemSelected;
    public HBox hbLayout;
    public HBox hbIconItem;
    public IconNode iconNode;

    private final I18nManager i18nManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbLayout.getStyleClass().add("hb-layout-remove-pane");
        lbDeleteItemSelected.setText(i18nManager.text(I18nKey.MSG_DELETE_ITEM_SELECTED_TITLE));
        lbDeleteItemSelected.getStyleClass().addAll("lb-msg-remove-item", ".lb-msg-remove-item-red");
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
        iconNode = new IconNode(GoogleMaterialDesignIcons.DELETE);
        hbIconItem.setSpacing(15);
        hbIconItem.getChildren().add(iconNode);
        iconNode.setIconSize(25);
        iconNode.getStyleClass().add("icon-node");
        /*iconNode.setOnMouseClicked(e->{
            hbLayout.setVisible(false);
        });*/


    }

    public void setBindingItemQuantity(IntegerBinding quantity){
        lbItemQuantity.textProperty().bind(i18nManager.textBinding(
                I18nKey.MSG_DELETE_ITEM_QUANTITY,
                quantity
                ));
        lbItemQuantity.getStyleClass().addAll("lb-msg-remove-item", "lb-msg-remove-item-blue");
    }
}
