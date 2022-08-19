package com.elca.internship.client;

import com.elca.internship.client.common.GuiUtil;
import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.i18n.SupportedLocale;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    private final I18nManager i18nManager;
    private Stage stage;


    private FxControllerAndView<DashboardController, Node> dashboardPageCV;


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        stage = event.getStage();
        loadDashboardIgnoreConnection();
    }

    public void loadDashboardIgnoreConnection(){
        Dimension2D dimension2DLoading = GuiUtil.getCenterSceneDim(stage, 1.25, 1.25);
        SupportedLocale locale = SupportedLocale.DEFAULT_LOCALE;
        i18nManager.setupLocale(locale);
        var bundle = i18nManager.bundle();
        dashboardPageCV = fxWeaver.load(DashboardController.class, bundle);
        dashboardPageCV.getView().ifPresent(view -> {
            Scene loadScene = new Scene((Parent) view
                    , dimension2DLoading.getWidth()
                    , dimension2DLoading.getHeight());
            loadScene.setFill(Color.TRANSPARENT);
            loadScene.getStylesheets().add(getClass().getResource("/views/css/main.css").toExternalForm());
            stage.setScene(loadScene);
        });

        stage.show();
    }

}
