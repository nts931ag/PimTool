package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.i18n.SupportedLocale;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.utils.NavigationHandler;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.*;
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
    private FxControllerAndView<ViewListProjectController, Node> projectListCV;
    private FxControllerAndView<CreateProjectController, Node> createProjectCV;
    private FxControllerAndView<ErrorPageController, Node> errorPageCV;
    private FxControllerAndView<CreateProjectController, Node> editProjectCV;
    @FXML
    public BorderPane MainContainer;
    @FXML
    private AnchorPane contentContainer;
    @FXML
    public HBox headerContainer;
    @FXML
    public VBox sideBarContainer;
    @FXML
    public BorderPane bodyContainer;
    @FXML
    private Label lbHeaderOfTab;
    @FXML
    public Label lbMenuNew;
    @FXML
    public Label lbMenuProject;
    @FXML
    public Label lbTitleApp;
    @FXML
    public Label lbEN;
    @FXML
    public Label lbFR;
    @FXML
    public Label lbMenuHelo;
    @FXML
    public Label lbMenuLogout;
    @FXML
    public Label lbMenu;
    @FXML
    public Label lbMenuCustomer;
    @FXML
    public Label lbMenuSupplier;

    SimpleObjectProperty<Label> lbMenuObjectProperty = new SimpleObjectProperty<>();
    SimpleObjectProperty<Label> lbLangObjectProperty = new SimpleObjectProperty<>();

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

        /*contentContainer.setPrefWidth(GuiUtil.getScreenWidth()*85/100);
        contentContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        sideBarContainer.setPrefWidth(GuiUtil.getScreenWidth()*15/100);
        sideBarContainer.setPrefHeight(GuiUtil.getScreenHeight()*90/100);
        headerContainer.setPrefWidth(GuiUtil.getScreenWidth());
        headerContainer.setPrefHeight(GuiUtil.getScreenHeight()*10/100);*/
        lbMenuObjectProperty.addListener((observable, oldValue, newValue) -> {
            if(oldValue!= null && oldValue != newValue){

                oldValue.getStyleClass().remove("label-menu-button-active");
                newValue.getStyleClass().add("label-menu-button-active");
            }
        });
        lbLangObjectProperty.addListener((observable, oldValue, newValue) -> {
            if(oldValue!= null && oldValue != newValue){

                oldValue.getStyleClass().remove("label-menu-button-active");
                newValue.getStyleClass().add("label-menu-button-active");
            }
        });

        navigationHandler.handleNavigateToListProject();
        lbLangObjectProperty.set(lbEN);
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    @FXML
    public void onLbProjectClicked(){
        lbMenuObjectProperty.set(lbMenuProject);

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
        isEditProject = false;
        lbMenuObjectProperty.set(lbMenuNew);

        createProjectCV = fxWeaver.load(CreateProjectController.class, i18nManager.bundle());
        createProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_CREATE_PROJECT_TITLE));
            contentContainer.getChildren().add(view);
        });
    }
    public void navigateToEditProjectPage(Project project){
        isEditProject = true;
        lbMenuObjectProperty.set(lbMenuNew);

        editProjectCV = fxWeaver.load(CreateProjectController.class, i18nManager.bundle());

        editProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_EDIT_PROJECT_TITLE));
            editProjectCV.getController().initEditProjectLayout(project);
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

    private boolean isEditProject = false;

    private void switchLanguage(){
        lbHeaderOfTab.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_EDIT_PROJECT_TITLE));
        lbMenuNew.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_CREATE_PROJECT_TITLE));
        lbMenuProject.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_LIST_PROJECT_TITLE));
        lbTitleApp.setText(i18nManager.text(I18nKey.DASHBOARD_TITLE));
        lbMenuHelo.setText(i18nManager.text(I18nKey.DASHBOARD_HEADER_HELP));
        lbMenuLogout.setText(i18nManager.text(I18nKey.DASHBOARD_HEADER_LOGOUT));
        lbMenu.setText(i18nManager.text(I18nKey.DASHBOARD_MENU));
        lbMenuCustomer.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_CUSTOMER));
        lbMenuSupplier.setText(i18nManager.text(I18nKey.DASHBOARD_MENU_SUPPLIER));

        Label label = lbMenuObjectProperty.get();
        if (lbMenuNew.equals(label)) {
            if(isEditProject){
                editProjectCV.getController().switchLanguage();
            }else{
                createProjectCV.getController().switchLanguage();
            }
            projectListCV.getController().switchLanguage();
        } else if (lbMenuProject.equals(label)) {
            projectListCV.getController().switchLanguage();
        } else {
            throw new IllegalStateException("Unexpected value: " + lbMenuObjectProperty.get());
        }
    }



    @FXML
    public void switchToEN() {
        if(lbLangObjectProperty.get() != lbEN){
            i18nManager.setupLocale(SupportedLocale.ENGLISH);
            switchLanguage();
            lbLangObjectProperty.set(lbEN);
        }
    }
    @FXML
    public void switchToFR() {
        if(lbLangObjectProperty.get() != lbFR){
            i18nManager.setupLocale(SupportedLocale.FRANCE);
            switchLanguage();
            lbLangObjectProperty.set(lbFR);
        }
    }
}
