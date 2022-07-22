package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.models.entity.Project;
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
    public BorderPane MainContainer;
    private Stage stage;
    public static NavigationHandler navigationHandler;
    private final I18nManager i18nManager;
    private FxControllerAndView<ViewListProjectController, Node> projectListCV;
    private FxControllerAndView<CreateProjectController, Node> createProjectCV;
    private FxControllerAndView<ErrorPageController, Node> errorPageCV;
    private FxControllerAndView<CreateProjectController, Node> editProjectCV;

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
            public void handleNavigateToCreateProject() {
                onLbNewClicked();
            }

            @Override
            public void handleNavigateToEditProject(Project project) {
                navigateToEditProjectPage(project);
            }

            @Override
            public void handleNavigateToListProject() {
                onLbProjectClicked();
            }

            @Override
            public void handleNavigateToErrorPage(String msg) {
                navigateToErrorPage(msg);
            }
        };

        contentContainer.setPrefWidth(GuiUtil.getScreenWidth()*85/100);
        contentContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        sideBarContainer.setPrefWidth(GuiUtil.getScreenWidth()*15/100);
        sideBarContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        headerContainer.setPrefWidth(GuiUtil.getScreenWidth());
        headerContainer.setPrefHeight(GuiUtil.getScreenHeight()*10/100);

        navigationHandler.handleNavigateToListProject();

    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    @FXML
    public void onLbProjectClicked(){
        if(projectListCV == null){
            projectListCV = fxWeaver.load(ViewListProjectController.class, i18nManager.bundle());
        }
        projectListCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_LIST_PROJECT_TITLE));
            contentContainer.getChildren().add(view);
            projectListCV.getController().onBtnSearchClicked();
        });
    }

    @FXML
    public void onLbNewClicked() {
        createProjectCV = fxWeaver.load(CreateProjectController.class, i18nManager.bundle());

        createProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_CREATE_PROJECT_TITLE));
            contentContainer.getChildren().add(view);
        });
    }

    public void navigateToErrorPage(String msg){
        if(errorPageCV == null){
            errorPageCV = fxWeaver.load(ErrorPageController.class, i18nManager.bundle());
        }
        errorPageCV.getView().ifPresent(view -> {
            MainContainer.setLeft(null);
            contentContainer.getChildren().clear();
            errorPageCV.getController().setMsgError(msg);
            contentContainer.getChildren().add(view);
        });
    }

    public void navigateToEditProjectPage(Project project){

        if(editProjectCV == null){
            editProjectCV = fxWeaver.load(CreateProjectController.class, i18nManager.bundle());
        }
        editProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_EDIT_PROJECT_TITLE));
            editProjectCV.getController().initEditProjectLayout(project);
            contentContainer.getChildren().add(view);
        });
    }
}
