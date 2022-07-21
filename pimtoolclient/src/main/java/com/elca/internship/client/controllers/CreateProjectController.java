package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.consume.ProjectEmployeeConsume;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.utils.FormValidation;
import com.elca.internship.client.api.RestTemplateConsume;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Project.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
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
public class CreateProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    public GridPane gpCreateProjectTab;
    private final I18nManager i18nManager;
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
    private FxControllerAndView<ViewListProjectController, Node> tabProjectListCV;
    private FxControllerAndView<AlertDangerController, Node> alertDangerCV;
    private ObservableList<String> listGroups;
    private ObservableList<String> listMembers;
    private ObservableList<Integer> listCurProNum;
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
        projectFormValidation.getFormFields().put("proNum", false);
        projectFormValidation.getFormFields().put("proName", false);
        projectFormValidation.getFormFields().put("proCustomer", false);
        projectFormValidation.getFormFields().put("proDate", true);
        projectFormValidation.getFormFields().put("proMember", false);
        this.addEventListeners();


    }

    private void addEventListeners() {

        tfProNum.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProNumValid(
                    newVal,
                    listCurProNum,
                    lbValidateProNum,
                    i18nManager
            ).isValid();
            projectFormValidation.getFormFields().put("proNum", valid);

        });

        tfProName.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProNameValid(
                    newVal,
                    lbValidateProName
            ).isValid();
            projectFormValidation.getFormFields().put("proName", valid);
        });

        tfProCustomer.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProCustomerValid(
                    newVal,
                    lbValidateProCustomer
            ).isValid();
            projectFormValidation.getFormFields().put("proCustomer", valid);

        });

        tfProMember.textProperty().addListener(((observable, oldValue, newValue) -> {
            var valid = FormValidation.isProMemberValid(
                    newValue,
                    listMembers,
                    lbValidateProMember
            ).isValid();
            projectFormValidation.getFormFields().put("proMember", valid);
        }));

        pickerStartDate.valueProperty().addListener(((observable, oldValue, newValue) -> {
            var endDate = pickerEndDate.getValue();
            var valid = FormValidation.isDateValid(
                    endDate,
                    newValue,
                    lbValidateProDate
            ).isValid();
            projectFormValidation.getFormFields().put("proDate", valid);
        }));

        pickerEndDate.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            var startDate = pickerStartDate.getValue();
            var valid = FormValidation.isDateValid(
                    newVal,
                    startDate,
                    lbValidateProDate
            ).isValid();
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
        colsConstraints.get(0).setPercentWidth(8);
        colsConstraints.get(1).setPercentWidth(14);
        colsConstraints.get(2).setPercentWidth(14);
        colsConstraints.get(3).setPercentWidth(14);
        colsConstraints.get(4).setPercentWidth(50);
        cbProGroup.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        cbProStatus.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pickerEndDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        pickerStartDate.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        var rowsConstraints = gpCreateProjectTab.getRowConstraints();
        rowsConstraints.get(0).setPrefHeight(1);
        for(var i = 1; i< rowsConstraints.size();++i){
            rowsConstraints.get(i).setPrefHeight(35);
        }

        alertDangerCV = fxWeaver.load(AlertDangerController.class, i18nManager.bundle());
        var node = (HBox) alertDangerCV.getView().get();
        gpCreateProjectTab.add(node,0,1,4,1);
        node.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        node.setVisible(false);



    }


    public void initEditProjectLayout(Project project){
        currentIdEdit = project.getId();
        tfProNum.setText(String.valueOf(project.getProjectNumber()));
        tfProNum.setDisable(true);
        tfProName.setText(project.getName());
        tfProCustomer.setText(project.getCustomer());
        cbProGroup.getSelectionModel().select(String.valueOf(project.getGroupId()));
        cbProStatus.getSelectionModel().select(i18nManager.text(Status.convertStatusToI18nKey(project.getStatus())));
        pickerStartDate.setValue(project.getStartDate());
        pickerEndDate.setValue(project.getEndDate());
        var listMemberOfcurrentProject = projectEmployeeConsume.retrieveAllEmployeeVisasByProjectId(project.getId());
        var listMember = new StringBuilder();
        listMemberOfcurrentProject.forEach(e->{
            listMember.append(e + ", ");
        });
        listMember.delete(listMember.length()-2, listMember.length());
        tfProMember.setText(listMember.toString());
        isEditMode = true;
    }

    public void fillDefaultValueForInputForm() {

        var listStatus = FXCollections.observableArrayList(
                i18nManager.text(I18nKey.PROJECT_STATUS_NEW)
                , i18nManager.text(I18nKey.PROJECT_STATUS_PLANNED)
                , i18nManager.text(I18nKey.PROJECT_STATUS_IN_PROGRESS)
                , i18nManager.text(I18nKey.PROJECT_STATUS_FINISHED)
        );
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(0);
        listGroups = FXCollections.observableArrayList("New");
        listGroups.addAll(restTemplateConsume.getAllGroupId());
        cbProGroup.setItems(listGroups);
        cbProGroup.getSelectionModel().select(0);

        listMembers = restTemplateConsume.getAllEmployeeVisa();

        listCurProNum = restTemplateConsume.getAllProjectNumber();


        var curDate = LocalDate.now();
        pickerEndDate.setValue(curDate);
        pickerStartDate.setValue(curDate);
    }

    @FXML
    public void onCreateProjectBtn() {

        if(validateFrom()) {
            var project = getProjectInputForm();
            var listMember = getMemberInputForm();
            if(!isEditMode){
                try {
                    var response = projectRestConsume.createNewProject(project, listMember);
                    if(!response.isError()){
                        DashboardController.navigationHandler.handleNavigateToListProject();
                    }else{
                        alertDangerCV.getController().setContentAndShowAlertLabel(response.getStatusMsg());
                    }
                } catch (JsonProcessingException e) {
                    DashboardController.navigationHandler.handleNavigateToErrorPage(e.getMessage());
                }
            }else{
                try {
                    var response = projectRestConsume.saveProjectChange(project, listMember);
                    if(!response.isError()){
                        DashboardController.navigationHandler.handleNavigateToListProject();
                    }else{
                        alertDangerCV.getController().setContentAndShowAlertLabel(response.getStatusMsg());
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
        if(groupId.equalsIgnoreCase("new")){
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

    private FxControllerAndView<DashboardController, Node> dashboardCV;


    @FXML
    public void onCancelProjectBtn() {
        if(!isEditMode){
            tfProNum.setText("");
            tfProName.setText("");
            tfProCustomer.setText("");
            tfProCustomer.setText("");
            cbProGroup.getSelectionModel().select(0);
            cbProStatus.getSelectionModel().select(0);
        }else{
            // navigationBackToProjectList;
            DashboardController.navigationHandler.handleNavigateToListProject();
        }
    }

}
