package com.elca.internship.client;

import com.elca.internship.client.Utils.AlertDialog;
import com.elca.internship.client.Utils.Util;
import com.elca.internship.client.config.connection.ServerConnection;
import com.elca.internship.client.controllers.DashboardController;
import com.elca.internship.client.controllers.LoadingPageController;
import javafx.concurrent.Task;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private int retryCount;
    private final FxWeaver fxWeaver;
    private Stage stage;


    private FxControllerAndView<LoadingPageController, Node> loadingPageCV;
    private FxControllerAndView<DashboardController, Node> dashboardPageCV;


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
        /*Dimension2D dimension2DLoading = Util.getCenterSceneDim(stage, 2, 1.5);
        loadingPageCV = fxWeaver.load(LoadingPageController.class);
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
        });

        stage.show();*/

        loadDashboardIgnoreConnection();
    }

    public void loadDashboardIgnoreConnection(){
        Dimension2D dimension2DLoading = Util.getCenterSceneDim(stage, 1, 1);
        dashboardPageCV = fxWeaver.load(DashboardController.class);
        dashboardPageCV.getView().ifPresent(view -> {
            Scene loadScene = new Scene(fxWeaver.loadView(DashboardController.class)
                    , dimension2DLoading.getWidth()
                    , dimension2DLoading.getHeight());
            loadScene.setFill(Color.TRANSPARENT);
            stage.setScene(loadScene);
        });

        stage.show();
    }

    public void proceedToAuth() {
        ServerConnection connection = this.getConnection();

        if (connection.isConnected()) {

            Dimension2D dimension2D = Util.getCenterSceneDim(this.stage, 1, 1);
            Scene root = new Scene(fxWeaver.loadView(DashboardController.class), dimension2D.getWidth(), dimension2D.getHeight());
            root.setFill(Color.TRANSPARENT);
            this.stage.setScene(root);
            this.stage.show();

        }
    }

    public ServerConnection getConnection(){
        ServerConnection connection = new ServerConnection();
        connection.connect();

        if (connection.isConnected()) {
            return connection;
        } else {
            if (retryCount > 60) {
                // Information dialog
                AlertDialog alertDialog = new AlertDialog("Error", "Connection Timed Out: Error Connecting ...", "Try relaunching ther application! Exiting....", Alert.AlertType.ERROR);
                alertDialog.showInformationDialog();
                stage.close();
                return null;
            } else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Retrying to connect.....");
                    System.out.println("Retry Count: " + retryCount);
                    retryCount++;
                    return getConnection();
                } catch (NullPointerException npe) {
                    System.out.println(npe.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return null;
            }
        }
    }
}
