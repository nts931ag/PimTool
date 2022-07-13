package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.Utils.FormValidation;
import com.elca.internship.client.models.entity.Employee;
import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Project.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@FxmlView("/views/createProject.fxml")
@Component
@RequiredArgsConstructor
public class CreateProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;
    public GridPane gpCreateProjectTab;
    private Stage stage;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private ComboBox<Long> cbProGroup;

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
        projectFormValidation.getFormFields().put("proStartDate", true);
        projectFormValidation.getFormFields().put("proEndDate", false);
        projectFormValidation.getFormFields().put("proMember", false);
        this.addEventListeners();
    }

    private void addEventListeners() {
        tfProNum.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProNumExisted(
                    newVal,
                    lbValidateProNum
            ).isValid();
            projectFormValidation.getFormFields().put("proNum", valid);

        });

        tfProName.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProjectName(
                    newVal,
                    lbValidateProName
            ).isValid();
            projectFormValidation.getFormFields().put("proName", valid);

        });

        tfProCustomer.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProjectCustomer(
                    newVal,
                    lbValidateProCustomer
            ).isValid();
            projectFormValidation.getFormFields().put("proCustomer", valid);

        });

        tfProMember.textProperty().addListener(((observable, oldValue, newValue) -> {
            var valid = FormValidation.isMemberValidated(
                    newValue,
                    listMembers,
                    lbValidateProMember
            ).isValid();
            projectFormValidation.getFormFields().put("proMember", valid);
        }));

        pickerEndDate.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            var startDate = pickerStartDate.getValue();
            var valid = FormValidation.isValidatedEndDate(
                    newVal,
                    startDate,
                    lbValidateProDate
            ).isValid();
            projectFormValidation.getFormFields().put("proEndDate", valid);
        });
    }

    private boolean validateFrom() {
        var node = (HBox) alertDangerCV.getView().get();
        if (projectFormValidation.getFormFields().containsValue(false)) {
            node.setVisible(true);
//            btnCreate.setDisable(true);
            return false;
        } else {
            node.setVisible(false);
//            btnCreate.setDisable(false);
            return true;
        }
    }

    public void initLayout() {

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
        var rowsContraints = gpCreateProjectTab.getRowConstraints();
        rowsContraints.get(0).setPrefHeight(1);
        for(var i = 1; i< rowsContraints.size();++i){
            rowsContraints.get(i).setPrefHeight(35);
        }
        alertDangerCV = fxWeaver.load(AlertDangerController.class);

        var node = (HBox) alertDangerCV.getView().get();
        gpCreateProjectTab.add(node,0,1,4,1);
        node.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        node.setVisible(false);



    }

    private ObservableList<Long> listGroups;
    private ObservableList<String> listMembers;

    public void fillDefaultValueForInputForm() {


        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(0);

        var responseForGroups = restTemplate.getForEntity(BASE_URI + "/api/groups", Group[].class);
        var groups = responseForGroups.getBody();
        listGroups = FXCollections.observableArrayList(Arrays.stream(groups).map(Group::getId).collect(Collectors.toList()));
        cbProGroup.setItems(listGroups);
        cbProGroup.getSelectionModel().select(0);

        var responseForMembers = restTemplate.getForEntity(BASE_URI + "/api/employees", Employee[].class);
        var members = responseForMembers.getBody();
        listMembers = FXCollections.observableArrayList(Arrays.stream(members).map(Employee::getVisa).collect(Collectors.toList()));
        var curDate = LocalDate.now();
        pickerEndDate.setValue(curDate);
        pickerStartDate.setValue(curDate);
    }

    @FXML
    public void onCreateProjectBtn() {
        System.out.println(projectFormValidation.getFormFields().toString());

        if(validateFrom()) {
            var project = getProjectInputForm();
            var listMember = getMemberInputForm();

            var objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            var map = new HashMap<String, Object>();
            map.put("project", project);
            map.put("listMember", listMember);

            try {
                var msg = objectMapper.writeValueAsString(map);
                var uri = BASE_URI + "/api/projects/save";
                var headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                var httpEntity = new HttpEntity<>(msg, headers);
                System.out.println(httpEntity.getBody());
                var responseEntity = restTemplate.exchange(
                        uri
                        , HttpMethod.POST
                        , httpEntity
                        , String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Connection status : " + responseEntity.getStatusCode());
                    navigateToTabListProject();
                }
            } catch (JsonProcessingException | ResourceAccessException jpe) {
                navigateToErrorPage(jpe.getMessage());
            }
        }
    }

    private void navigateToTabListProject() {
        // get and set contentContainer
        var borderPane = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(2);
        var titleContentContainer =  (Label)borderPane.getChildren().get(1);
        var contentContainer = (Pane) borderPane.getChildren().get(0);
        tabProjectListCV = fxWeaver.load(ViewListProjectController.class);
        tabProjectListCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
        });
        titleContentContainer.setText("Projects list");
    }

    public Project getProjectInputForm() {
        var proNum = tfProNum.getText();
        var proName = tfProName.getText();
        var customer = tfProCustomer.getText();
        var groupId = cbProGroup.getSelectionModel().getSelectedItem();
        var status = cbProStatus.getSelectionModel().getSelectedItem();
        var startDate = pickerStartDate.getValue();
        var endDate = pickerEndDate.getValue();
        return new Project(groupId, Integer.parseInt(proNum), proName, customer, Status.getStatus(status), startDate, endDate);
    }

    public List<String> getMemberInputForm() {
        var members = tfProMember.getText();
        return Arrays.stream(members.split(", ")).toList();
    }


    @FXML
    public void onCancelProjectBtn() {
        /*tfProNum.setText("");
        tfProName.setText("");
        tfProCustomer.setText("");
        tfProCustomer.setText("");
        cbProGroup.getSelectionModel().select(0);
        cbProStatus.getSelectionModel().select(0);*/
        navigateToErrorPage("msgError");
    }

    private void navigateToErrorPage(String msgError) {
        var nodeBorderPane = (BorderPane) stage.getScene().getRoot();
        nodeBorderPane.setLeft(null);
        var errorPageCV = fxWeaver.load(ErrorPageController.class);
        errorPageCV.getView().ifPresent(nodeBorderPane::setCenter);
        var errorPageController = errorPageCV.getController();
        errorPageController.setMsgError(msgError);
    }
}
