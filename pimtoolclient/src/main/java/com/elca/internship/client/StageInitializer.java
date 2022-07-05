package com.elca.internship.client;

import com.elca.internship.client.Utils.Util;
import com.elca.internship.client.controllers.LoadingPageController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private int retryCount;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @Value("classpath:/views/dashboard.fxml")
    private Resource resource;

    private FxControllerAndView<LoadingPageController, Node> loadingPageCV;


    @Autowired
    public StageInitializer(FxWeaver fxWeaver){
        this.fxWeaver = fxWeaver;
        this.retryCount = 0;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        stage = event.getStage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        Dimension2D dimension2DLoading = Util.getCenterSceneDim(stage, 2, 1.5);
        /*loadingPageCV = fxWeaver.load(LoadingPageController.class);
        loadingPageCV.getView().ifPresent(view -> {
            Scene loadScene = new Scene(fxWeaver.loadView(LoadingPageController.class), dimension2DLoading.getWidth(), dimension2DLoading.getHeight());
            loadScene.setFill(Color.TRANSPARENT);
            stage.setScene(loadScene);

            Task<Void> sleeper = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(event1 -> {
                proceedToAuth();
            });
            new Thread(sleeper).start();

        });*/

        try {
            var fxmlLoader = new FXMLLoader(resource.getURL());
            var parent = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(parent, dimension2DLoading.getWidth(), dimension2DLoading.getHeight()));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
