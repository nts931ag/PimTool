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


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
        fillDefaultValueForInputForm();

        projectFormValidation = new FormValidation();
        projectFormValidation.getFormFields().put("proNum", true);
        projectFormValidation.getFormFields().put("proName", true);
        projectFormValidation.getFormFields().put("proCustomer", true);
        projectFormValidation.getFormFields().put("proStartDate", true);
        projectFormValidation.getFormFields().put("proEndDate", true);

        this.addEventListeners();
    }

    private void addEventListeners() {
        tfProNum.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProNumExisted(
                    newVal,
                    lbValidateProNum
            ).isValid();
            projectFormValidation.getFormFields().put("proNum", valid);

            this.validateFrom();
        });

        tfProName.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProjectName(
                    newVal,
                    lbValidateProName
            ).isValid();
            projectFormValidation.getFormFields().put("proName", valid);

            this.validateFrom();
        });

        tfProCustomer.textProperty().addListener((observableValue, oldVal, newVal) -> {
            var valid = FormValidation.isProjectCustomer(
                    newVal,
                    lbValidateProCustomer
            ).isValid();
            projectFormValidation.getFormFields().put("proCustomer", valid);

            this.validateFrom();
        });



        pickerEndDate.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            var startDate = pickerStartDate.getValue();
            var valid = FormValidation.isValidatedEndDate(
                    newVal,
                    startDate,
                    lbValidateProDate
            ).isValid();
            projectFormValidation.getFormFields().put("proEndDate", valid);

            this.validateFrom();
        });
    }

    private boolean validateFrom() {

        if (projectFormValidation.getFormFields().containsValue(false)) {
            var node =(GridPane) fxWeaver.loadView(AlertDangerController.class);
            node.setPrefWidth(800.0);
            node.setPadding(new Insets(0,0,0,25));
            gpCreateProjectTab.add(node, 0, 1, 4, 1);
            btnCreate.setDisable(true);
            return false;
        } else {
            gpCreateProjectTab.getRowConstraints().get(1).setPrefHeight(0);
            btnCreate.setDisable(false);
            return true;
        }
    }

    public void initLayout() {
        gpCreateProjectTab.setVgap(20);
        gpCreateProjectTab.setHgap(5);


        var colsConstraints = gpCreateProjectTab.getColumnConstraints();
        colsConstraints.forEach(c -> c.setPrefWidth(200));
        gpCreateProjectTab.getColumnConstraints().get(4).setPrefWidth(800);

    }

    private ObservableList<Long> listGroups;
    private ObservableList<String> listMembers;

    public void fillDefaultValueForInputForm() {


        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(0);

        var responseForGroups = restTemplate.getForEntity(BASE_URI + "/api/groups", Group[].class);
        var groups = responseForGroups.getBody();
//        var listGroups = FXCollections.observableArrayList(1, 2, 3);
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
                var uri = BASE_URI + "/projects/save";
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
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
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
        var listMemberExist = new ArrayList<String>();
        var listMemberNonExist = new ArrayList<String>();
        var listMember = Arrays.stream(members.split(", ")).toList();
        listMember.forEach(e->{
            if(listMembers.contains(e)){
                listMemberExist.add(e);
            }else{
                listMemberNonExist.add(e);
            }
        });
        return listMemberExist;
    }


    @FXML
    public void onCancelProjectBtn() {
        navigateToTabListProject();
    }
}
