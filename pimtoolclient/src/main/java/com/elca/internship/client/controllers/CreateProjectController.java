package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.consume.GroupRestConsume;
import com.elca.internship.client.consume.ProjectEmployeeConsume;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.utils.AlertDialog;
import com.elca.internship.client.utils.FormValidation;
import com.elca.internship.client.api.RestTemplateConsume;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Project.Status;
import com.elca.internship.client.utils.ValidatedResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@FxmlView("/views/createProject.fxml")
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    public GridPane gpCreateProjectTab;
    private final I18nManager i18nManager;
    public Label lbProEndDate;
    public Label lbProStartDate;
    public Label lbProStatus;
    public Label lbProMembers;
    public Label lbProGroup;
    public Label lbProCustomer;
    public Label lbProName;
    public Label lbProNumber;
    private Stage stage;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private ComboBox<String> cbProGroup;

    @FXML
    private ComboBox<String> cbProStatus;

    @FXML
    private Label lbValidateProCustomer;

    @FXML
    private Label lbValidateProDate;

    @FXML
    private Label lbValidateProGroup;

    @FXML
    private Label lbValidateProMember;

    @FXML
    private Label lbValidateProName;

    @FXML
    private Label lbValidateProNum;

    @FXML
    private Label lbValidateProStatus;

    @FXML
    private DatePicker pickerEndDate;

    @FXML
    private DatePicker pickerStartDate;

    @FXML
    private TextField tfProCustomer;

    @FXML
    private TextField tfProMember;

    @FXML
    private TextField tfProName;

    @FXML
    private TextField tfProNum;

    public FormValidation projectFormValidation;
    private FxControllerAndView<AlertDangerController, Node> alertDangerCV;
    private ObservableList<String> listGroups;
    private ObservableList<String> listMembers;
    private final RestTemplateConsume restTemplateConsume;
    private final ProjectRestConsume projectRestConsume;
    private final ProjectEmployeeConsume projectEmployeeConsume;
    private boolean isEditMode;
    private Long currentIdEdit = 0L;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
        fillDefaultValueForInputForm();
        projectFormValidation = new FormValidation();
        projectFormValidation.getFormFields().put("proNumber", false);
        projectFormValidation.getFormFields().put("proName", false);
        projectFormValidation.getFormFields().put("proCustomer", false);
        projectFormValidation.getFormFields().put("proMember", false);
        projectFormValidation.getFormFields().put("proDate", true);
        this.addEventListeners();

    }

    private void addEventListeners() {

        tfProMember.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue){
                var inputMembers = tfProMember.getText();
                var valid = FormValidation.isProMemberValidInput(
                        inputMembers,
                        lbValidateProMember,
                        i18nManager
                ).getIsValid();
                projectFormValidation.getFormFields().put("proMember", valid);
            }
        }));
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        tfProNum.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue && firstTime.get() == false){
                var inputNumber = tfProNum.getText();
                var valid = FormValidation.isProNumberValidInput(
                        inputNumber,
                        lbValidateProNum,
                        i18nManager
                ).getIsValid();
                if(valid){
                    var isExisted = projectRestConsume.CheckProjectNumberIsExisted(Long.parseLong(inputNumber));
                    valid = FormValidation.isProNumberNotExisted(
                            isExisted,
                            lbValidateProNum,
                            i18nManager
                    ).getIsValid();
                }
                projectFormValidation.getFormFields().put("proNumber", valid);
            }else {
                if(firstTime.get() == true){
                    gpCreateProjectTab.requestFocus();
                    firstTime.setValue(false);
                }
            }
        }));

        tfProName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue){
                var valid = FormValidation.isProNameValidInput(
                        tfProName.getText(),
                        lbValidateProName,
                        i18nManager
                ).getIsValid();
                projectFormValidation.getFormFields().put("proName", valid);
            }
        }));

        tfProCustomer.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue){
                var valid = FormValidation.isProCustomerValidInput(
                        tfProCustomer.getText(),
                        lbValidateProCustomer,
                        i18nManager
                ).getIsValid();
                projectFormValidation.getFormFields().put("proCustomer", valid);
            }
        }));


        pickerStartDate.valueProperty().addListener(((observable, oldValue, newValue) -> {
            var endDate = pickerEndDate.getValue();
            var valid = FormValidation.isDateValidInput(
                    endDate,
                    newValue,
                    lbValidateProDate,
                    i18nManager
            ).getIsValid();
            projectFormValidation.getFormFields().put("proDate", valid);
        }));

        pickerEndDate.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            var startDate = pickerStartDate.getValue();
            var valid = FormValidation.isDateValidInput(
                    newVal,
                    startDate,
                    lbValidateProDate,
                    i18nManager
            ).getIsValid();
            projectFormValidation.getFormFields().put("proDate", valid);
        });


    }

    private boolean validateFrom() {
        var node = (HBox) alertDangerCV.getView().get();
        if (projectFormValidation.getFormFields().containsValue(false)) {
            node.setVisible(true);
            return false;
        } else {
            node.setVisible(false);
            return true;
        }
    }

    public void initLayout() {
        isEditMode = false;
        gpCreateProjectTab.setVgap(15);
        gpCreateProjectTab.setHgap(10);
        var colsConstraints = gpCreateProjectTab.getColumnConstraints();
        colsConstraints.get(0).setMaxWidth(150);
        colsConstraints.get(1).setPrefWidth(200);
        colsConstraints.get(1).setMaxWidth(300);
        colsConstraints.get(2).setMaxWidth(150);
        colsConstraints.get(3).setPrefWidth(200);
        colsConstraints.get(3).setMaxWidth(300);
        colsConstraints.get(4).setPrefWidth(450);
        colsConstraints.get(4).setMinWidth(450);

        cbProGroup.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        cbProStatus.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pickerEndDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        pickerStartDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        var rowsConstraints = gpCreateProjectTab.getRowConstraints();
        rowsConstraints.get(0).setMaxHeight(1);
        for(var i = 1; i< rowsConstraints.size()-1;++i){
            rowsConstraints.get(i).setMaxHeight(45);
        }
        rowsConstraints.get(11).setPrefHeight(45);
        AnchorPane.setBottomAnchor(gpCreateProjectTab, 10.0);
        AnchorPane.setLeftAnchor(gpCreateProjectTab, 0.0);
        AnchorPane.setRightAnchor(gpCreateProjectTab, 0.0);
        AnchorPane.setTopAnchor(gpCreateProjectTab, 10.0);

        alertDangerCV = fxWeaver.load(AlertDangerController.class, i18nManager.bundle());
        var node = (HBox) alertDangerCV.getView().get();
        gpCreateProjectTab.add(node,0,1,4,1);
        node.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        node.setVisible(false);



    }


    public void initEditProjectLayout(Project project){
        btnCreate.setText(i18nManager.text(I18nKey.BUTTON_EDIT_PROJECT));
        currentIdEdit = project.getId();
        tfProNum.setText(String.valueOf(project.getProjectNumber()));
        projectFormValidation.getFormFields().put("proNumber", true);
        tfProNum.setDisable(true);
        tfProName.setText(project.getName());
        tfProCustomer.setText(project.getCustomer());
        cbProGroup.getSelectionModel().select(String.valueOf(project.getGroupId()));
        cbProStatus.getSelectionModel().select(i18nManager.text(Status.convertStatusToI18nKey(project.getStatus())));
        pickerStartDate.setValue(project.getStartDate());
        pickerEndDate.setValue(project.getEndDate());
        var listMemberOfCurrentProject = projectEmployeeConsume.retrieveAllEmployeeVisasByProjectId(project.getId());
        if(listMemberOfCurrentProject.size() != 0){
            var listMember = new StringBuilder();
            listMemberOfCurrentProject.forEach(e->{
                listMember.append(e + ", ");
            });
            listMember.delete(listMember.length()-2, listMember.length());
            tfProMember.setText(listMember.toString());
        }

        isEditMode = true;
        Platform.runLater(()->{
            tfProName.requestFocus();
            tfProCustomer.requestFocus();
            tfProMember.requestFocus();
            gpCreateProjectTab.requestFocus();
        });

    }

    private final GroupRestConsume groupRestConsume;

    public void fillDefaultValueForInputForm() {

        var listStatus = FXCollections.observableArrayList(
                i18nManager.text(I18nKey.PROJECT_STATUS_NEW)
                , i18nManager.text(I18nKey.PROJECT_STATUS_PLANNED)
                , i18nManager.text(I18nKey.PROJECT_STATUS_IN_PROGRESS)
                , i18nManager.text(I18nKey.PROJECT_STATUS_FINISHED)
        );
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(0);
        listGroups = FXCollections.observableArrayList(i18nManager.text(I18nKey.GROUP_NEW));
        listGroups.addAll(groupRestConsume.retrieveObsListAllGroupIds());
        cbProGroup.setItems(listGroups);
        cbProGroup.getSelectionModel().select(0);

        listMembers = restTemplateConsume.getAllEmployeeVisa();



        var curDate = LocalDate.now();
        pickerStartDate.setValue(curDate);
    }

    @FXML
    public void onCreateProjectBtn() {

        if(validateFrom()) {
            var project = getProjectInputForm();
            var listMember = getMemberInputForm();
            log.info("Input form project: {}", project);
            log.info("Input form list member: {}", listMember);
            if(!isEditMode){
                try {
                    var response = projectRestConsume.createNewProject(project, listMember);

                    if(response.getTypeError() == 0){
                        DashboardController.navigationHandler.handleNavigateToListProject();
                        log.info("Reponse: {}", response.getStatusMsg());
                    }else{
                        alertDangerCV.getController().setContentAndShowAlertLabel(response);
                        log.error("Reponse: {}", response.getStatusMsg());
                    }
                } catch (JsonProcessingException e) {
                    DashboardController.navigationHandler.handleNavigateToErrorPage(e.getMessage());
                }
            }else{
                try {
                    var response = projectRestConsume.saveProjectChange(project, listMember);
                    if(response.getTypeError() == 0){
                        DashboardController.navigationHandler.handleNavigateToListProject();
                        log.info("Reponse: {}", response.getStatusMsg());
                    }else{
                        alertDangerCV.getController().setContentAndShowAlertLabel(response);
                        log.error("Reponse: {}", response.getStatusMsg());
                    }
                } catch (JsonProcessingException e) {
                    DashboardController.navigationHandler.handleNavigateToErrorPage(e.getMessage());
                }
            }
        }
    }

    public Project getProjectInputForm() {
        var proNum = tfProNum.getText();
        var proName = tfProName.getText();
        var customer = tfProCustomer.getText();
        var groupId = cbProGroup.getSelectionModel().getSelectedItem();
        var status = cbProStatus.getSelectionModel().getSelectedItem();
        var startDate = pickerStartDate.getValue();
        var endDate = pickerEndDate.getValue();
        if(groupId.equalsIgnoreCase(i18nManager.text(I18nKey.GROUP_NEW))){
            return new Project(
                    currentIdEdit,
                    0,
                    Integer.parseInt(proNum),
                    proName,
                    customer,
                    Status.convertStringStatusToStatus(status, i18nManager),
                    startDate,
                    endDate,
                    1);

        }else{
            return new Project(
                    currentIdEdit,
                    Long.parseLong(groupId),
                    Integer.parseInt(proNum),
                    proName,
                    customer,
                    Status.convertStringStatusToStatus(status, i18nManager),
                    startDate,
                    endDate,
                    1);

        }
    }

    public List<String> getMemberInputForm() {
        var members = tfProMember.getText();
        return Arrays.stream(members.split(", ")).toList();
    }

    @FXML
    public void onCancelProjectBtn() {

        if(!isEditMode){
            DashboardController.navigationHandler.handleNavigateToListProject();
        }else{
            DashboardController.navigationHandler.handleNavigateToListProject();
        }
    }

    public void switchLanguage(){
        var listStatus = FXCollections.observableArrayList(
                i18nManager.text(I18nKey.PROJECT_STATUS_NEW)
                , i18nManager.text(I18nKey.PROJECT_STATUS_PLANNED)
                , i18nManager.text(I18nKey.PROJECT_STATUS_IN_PROGRESS)
                , i18nManager.text(I18nKey.PROJECT_STATUS_FINISHED)
        );
        var idxStatusCurrent = cbProStatus.getSelectionModel().getSelectedIndex();
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(idxStatusCurrent);
        listGroups.set(0,i18nManager.text(I18nKey.GROUP_NEW));
//        cbProGroup.setItems(listGroups);


        lbProEndDate.setText(i18nManager.text(I18nKey.LABEL_PROJECT_END_DATE));
        lbProStartDate.setText(i18nManager.text(I18nKey.LABEL_PROJECT_START_DATE));
        lbProStatus.setText(i18nManager.text(I18nKey.LABEL_PROJECT_STATUS));
        lbProMembers.setText(i18nManager.text(I18nKey.LABEL_PROJECT_MEMBERS));
        lbProGroup.setText(i18nManager.text(I18nKey.LABEL_PROJECT_GROUP));
        lbProCustomer.setText(i18nManager.text(I18nKey.LABEL_PROJECT_CUSTOMER));
        lbProName.setText(i18nManager.text(I18nKey.LABEL_PROJECT_NAME));
        lbProNumber.setText(i18nManager.text(I18nKey.LABEL_PROJECT_NUMBER));
        btnCancel.setText(i18nManager.text(I18nKey.BUTTON_CANCEL_CREATE_PROJECT));
        if(isEditMode){
            btnCreate.setText(i18nManager.text(I18nKey.BUTTON_EDIT_PROJECT));
        }else{
            btnCreate.setText(i18nManager.text(I18nKey.BUTTON_SEARCH_PROJECT));

        }
    }
}
