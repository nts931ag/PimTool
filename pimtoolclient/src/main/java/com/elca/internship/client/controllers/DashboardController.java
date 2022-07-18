package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.utils.NavigationHandler;
import com.elca.internship.client.utils.GuiUtil;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
    private Stage stage;
    public static NavigationHandler navigationHandler;
    private final I18nManager i18nManager;
    private FxControllerAndView<ViewListProjectController, Node> tabProjectListCV;
    private FxControllerAndView<CreateProjectController, Node> tabCreateProjectCV;

    @FXML
    private Pane contentContainer;
    @FXML
    public HBox headerContainer;
    @FXML
    public VBox sideBarContainer;
    @FXML
    private Label lbHeaderOfTab;
    @FXML
    public Label lbMenuNew;
    @FXML
    public Label lbMenuProject;
    @FXML
    public BorderPane bodyContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigationHandler = new NavigationHandler() {
            @Override
            public void handleShowTabProjectList() {
                onLbProjectClicked();
            }

            @Override
            public void handleShowTabCreateProject() {
                onLbNewClicked();
            }
        };

        contentContainer.setPrefWidth(GuiUtil.getScreenWidth()*85/100);
        contentContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        sideBarContainer.setPrefWidth(GuiUtil.getScreenWidth()*15/100);
        sideBarContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        headerContainer.setPrefWidth(GuiUtil.getScreenWidth());
        headerContainer.setPrefHeight(GuiUtil.getScreenHeight()*10/100);

        onLbNewClicked();

    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    @FXML
    public void onLbProjectClicked(){
        var currentBundle = i18nManager.bundle();
        tabProjectListCV = fxWeaver.load(ViewListProjectController.class, currentBundle);
        tabProjectListCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
//            lbHeaderOfTab.setText("Project List");
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_LIST_PROJECT_TITLE));
        });
    }

    @FXML
    public void onLbNewClicked() {
        var currentBundle = i18nManager.bundle();
        tabCreateProjectCV = fxWeaver.load(CreateProjectController.class, currentBundle);
        tabCreateProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
//            lbHeaderOfTab.setText("New Project");
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_CREATE_PROJECT_TITLE));
        });
    }
}
