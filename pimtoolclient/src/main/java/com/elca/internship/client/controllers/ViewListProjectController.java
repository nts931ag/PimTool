package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.models.entity.Project;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    public VBox vbListProject;
    @FXML
    public HBox hbFilterListProject;
    @FXML
    public AnchorPane apContentTableListProject;
    @FXML
    public Pagination paginationTableProject;
    @FXML
    public TableColumn<Project,CheckBox> colCheck;
    @FXML
    public TableColumn<Project,Integer> colProNum;
    @FXML
    public TableColumn<Project,String> colProName;
    @FXML
    public TableColumn<Project, String> colProStatus;
    @FXML
    public TableColumn<Project,String> colProCustomer;
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
    public Label lbBtnResetSearch;


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
        fillValueToLayout();

    }

    private void initLayout() {
        hbFilterListProject.setPadding(new Insets(25,0,25,0));

        colCheck.setPrefWidth(30);
        colProDel.setPrefWidth(70);
        colProNum.setPrefWidth(100);
        colProCustomer.setPrefWidth(150);
        colProName.setPrefWidth(400);
        colProStatus.setPrefWidth(100);
        colProStart.setPrefWidth(150);

        colCheck.getStyleClass().add("table-column-align-center");
        colProDel.getStyleClass().add("table-column-align-center");
        colProNum.getStyleClass().add("table-column-align-right");
        System.out.println(tbProject.getWidth() + " " + tbProject.getHeight());
    }

    private void fillValueToLayout() {
        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbStatus.setItems(listStatus);
        cbStatus.getSelectionModel().select(0);

        createTableProject();
    }

    public void createTableProject(){
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
        colProCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colProStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        tbProject.setItems(dataProjects);




    }



    private FxControllerAndView<DashboardController, Node> dashboardPageCV;


    public void onBtnSearchClicked(ActionEvent actionEvent) {
    }

    public void onLbBtnResetSearchClicked(MouseEvent mouseEvent) {
    }
}
