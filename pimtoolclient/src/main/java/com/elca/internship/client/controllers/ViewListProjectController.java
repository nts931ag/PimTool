package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.models.entity.Project;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/viewListProject.fxml")
@RequiredArgsConstructor
public class ViewListProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private Stage stage;

    private FxControllerAndView<CreateProjectController, Node> tabCreateProjectCV;


    @FXML
    public TableColumn<Project,CheckBox> colCheck;
    @FXML
    public TableColumn<Project,Integer> colProNum;
    @FXML
    public TableColumn<Project,String> colProName;
    @FXML
    public TableColumn<Project, String> colProSta;
    @FXML
    public TableColumn<Project,String> colProCus;
    @FXML
    public TableColumn<Project, String> colProStart;
    @FXML
    public TableColumn<Project,?> colProDel;
    @FXML
    public TableView<Project> tbProject;
    @FXML
    public TextField tfSearch;
    @FXML
    public ComboBox<String> cbStatus;
    @FXML
    public Button btnSearch;
    @FXML
    public Button btnResetSearch;




    public void createTableProject(){
        //  TODO
        // (long groupId, Integer projectNumber, String name, String customer, Status status, LocalDate startDate, LocalDate endDate, int version)
        var startDate = LocalDate.now();
        var endDate = startDate.plusYears(1);
        var dataProjects = FXCollections.observableArrayList(
                new Project(1, 1111,"firstProject", "Elca1", Project.Status.NEW, startDate, endDate, 1),
                new Project(2, 2222,"secondProject", "Elca2", Project.Status.NEW, startDate, endDate, 1),
                new Project(3, 3333,"thirdProject", "Elca3", Project.Status.NEW, startDate, endDate, 1),
                new Project(4, 4444,"fourProject", "Elca4", Project.Status.NEW, startDate, endDate, 1),
                new Project(5, 5555,"fiveProject", "Elca5", Project.Status.NEW, startDate, endDate, 1),
                new Project(6, 6666,"sixProject", "Elca6", Project.Status.NEW, startDate, endDate, 1),
                new Project(7, 7777,"sevenProject", "Elca7", Project.Status.NEW, startDate, endDate, 1)
                );


        colProNum.setCellValueFactory(new PropertyValueFactory<>("projectNumber"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProCus.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colProSta.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        tbProject.setItems(dataProjects);




    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbStatus.setItems(listStatus);
        cbStatus.getSelectionModel().select(0);
        createTableProject();


    }

    @FXML
    public void onBtnSearch() {
        System.out.println("on btn search clicked");
    }

    private FxControllerAndView<DashboardController, Node> dashboardPageCV;

    @FXML
    public void onBtnResetSearch() {

    }
}
