package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.Project.Status;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.json.JSONObject;
import org.springframework.context.ApplicationListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@FxmlView("/views/tabCreateProject.fxml")
@Component
@RequiredArgsConstructor
public class TabCreateProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private final RestTemplate restTemplate;
    public GridPane gpCreateProjectTab;
    private Stage stage;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnCreate;

    @FXML
    private ComboBox<Integer> cbProGroup;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gpCreateProjectTab.setVgap(20);
        gpCreateProjectTab.setHgap(5);

        var colsConstraints = gpCreateProjectTab.getColumnConstraints();
        colsConstraints.forEach(c -> {
            c.setPrefWidth(200);
        });


        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbProStatus.setItems(listStatus);
        cbProStatus.getSelectionModel().select(0);
        var listGroups = FXCollections.observableArrayList(1,2,3);
        cbProGroup.setItems(listGroups);
        cbProGroup.getSelectionModel().select(0);
        var curDate = LocalDate.now();
        pickerEndDate.setValue(curDate);
        pickerStartDate.setValue(curDate);
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }


    @FXML
    public void onCreateProjectBtn() {
        var proNum = tfProNum.getText();
        var proName = tfProName.getText();
        var customer = tfProCustomer.getText();
        var groupId = cbProGroup.getSelectionModel().getSelectedItem();
        var members = tfProMember.getText();
        var status = cbProStatus.getSelectionModel().getSelectedItem();
        var startDate = pickerStartDate.getValue();
        var endDate = pickerEndDate.getValue();

        var project = new Project(groupId, Integer.parseInt(proNum), proName, customer, Status.getStatus(status), startDate, endDate);
        var listMember = members.split(",");
        var jsonData = new JSONObject();
        // using RestTemplate to consuming REST API
        var projectJson = new JSONObject(project);
        jsonData.put("project", projectJson);
        jsonData.put("listMember", listMember);

        var uri = BASE_URI + "/projects/save";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(jsonData.toString(),headers);
        System.out.println(httpEntity.getBody());
        var responseEntity = restTemplate.exchange(
                uri
                , HttpMethod.POST
                , httpEntity
                , String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Connection status : " + responseEntity.getStatusCode());
        }


    }


    @FXML
    public void onCancelProjectBtn() {

    }
}
