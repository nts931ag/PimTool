package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.Utils.NavigationHandler;
import com.elca.internship.client.Utils.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/dashboard.fxml")
@RequiredArgsConstructor
public class DashboardController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    public Button btnLogout;

    private Stage stage;
    public static NavigationHandler navigationHandler;

    private FxControllerAndView<TabProjectListController, Node> tabProjectListCV;
    private FxControllerAndView<TabCreateProjectController, Node> tabCreateProjectCV;

    @FXML
    private Pane contentContainer;
    @FXML
    public HBox headerContainer;
    @FXML
    public VBox sideBarContainer;
    @FXML
    private Label lbHeaderOfTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationHandler = new NavigationHandler() {
            @Override
            public void handleShowTabProjectList() {
                showTabProjectList();
            }

            @Override
            public void handleShowTabCreateProject() {
                showTabCreateProject();
            }
        };

        System.out.println("width: " + Util.getScreenWidth() + " height: " + Util.getScreenWidth());
        contentContainer.setPrefWidth(Util.getScreenWidth()*90/100);
        contentContainer.setPrefHeight(Util.getScreenHeight()*90/100);
        sideBarContainer.setPrefWidth(Util.getScreenWidth()*10/100);
        sideBarContainer.setPrefHeight(Util.getScreenHeight()*90/100);
        headerContainer.setPrefWidth(Util.getScreenWidth());
        headerContainer.setPrefHeight(Util.getScreenHeight()*10/100);


        showTabCreateProject();

    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }



    @FXML
    public void showTabProjectList(){
        System.out.println("width: " + stage.getWidth() + " height: " + stage.getHeight());

        tabProjectListCV = fxWeaver.load(TabProjectListController.class);
        tabProjectListCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
            lbHeaderOfTab.setText("Project List");

        });
    }

    @FXML
    public void showTabCreateProject(){
        tabCreateProjectCV = fxWeaver.load(TabCreateProjectController.class);
        tabCreateProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
            lbHeaderOfTab.setText("New Project");
        });
    }

    @FXML
    public void onBtnLogout() {
        System.out.println("on button logout clicked");
    }
}
