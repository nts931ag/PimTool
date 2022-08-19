package com.elca.internship.client.common;

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiUtil {
private static double titleLabelHeight = GuiUtil.getScreenHeight() * 0.05;
    public static double getScreenWidth(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getWidth();
    }

    public static double getScreenHeight(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getHeight();
    }

    public static double getContentAreaWidth(){
        return GuiUtil.getScreenWidth() * 0.85;
    }

    public static double getContentAreaHeight(){
        return GuiUtil.getScreenHeight();
    }

    public static Dimension2D getCenterSceneDim(Stage stage, double widthScale, double heightScale){
        double width = GuiUtil.getScreenWidth() / widthScale;
        double height = GuiUtil.getScreenHeight() / heightScale;
        stage.setX((GuiUtil.getScreenWidth() - width) / 2);
        stage.setY((GuiUtil.getScreenHeight() - height) / 2);
        return new Dimension2D(width,height);
    }

}
