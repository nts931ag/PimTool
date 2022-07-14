package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@FxmlView("/views/viewListProject.fxml")
@RequiredArgsConstructor
public class ViewListProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private Stage stage;

    private FxControllerAndView<CreateProjectController, Node> tabCreateProjectCV;
    private final RestTemplate restTemplate;
    @FXML
    public VBox vbListProject;
    @FXML
    public HBox hbFilterListProject;
    @FXML
    public Pagination paginationTableProject;
    @FXML
    public TableColumn<ProjectTable,CheckBox> colCheck;
    @FXML
    public TableColumn<ProjectTable,Integer> colProNum;
    @FXML
    public TableColumn<ProjectTable,String> colProName;
    @FXML
    public TableColumn<ProjectTable, String> colProStatus;
    @FXML
    public TableColumn<ProjectTable,String> colProCustomer;
    @FXML
    public TableColumn<ProjectTable, String> colProStart;
    @FXML
    public TableColumn<ProjectTable, IconNode> colProDel;
    @FXML
    public TableView<ProjectTable> tbProject;
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
        cbStatus.setPrefWidth(200);
        btnSearch.setPrefWidth(200);

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
    }

    private ObservableList<ProjectTable> dataProjects;

    private void fillValueToLayout() {
        var listStatus = FXCollections.observableArrayList("New", "Planned", "In progress", "Finished");
        cbStatus.setItems(listStatus);
        cbStatus.setPromptText("Project status");
        cbStatus.getSelectionModel().select(0);

        createTableProject();
    }

    public void createTableProject(){
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
        var responseForProjects = restTemplate.getForEntity(BASE_URI + "/api/projects", Project[].class);
        var projects = responseForProjects.getBody();
        dataProjects = FXCollections.observableArrayList(Arrays
                                                            .stream(projects)
                                                            .map(e ->
                                                                new ProjectTable(
                                                                        new CheckBox(),
                                                                        e.getGroupId(),
                                                                        e.getProjectNumber(),
                                                                        e.getName(),
                                                                        e.getCustomer(),
                                                                        e.getStatus(),
                                                                        e.getStartDate(),
                                                                        e.getEndDate(),
                                                                        e.getVersion(),
                                                                        new IconNode(GoogleMaterialDesignIcons.DELETE)
                                                                )
                                                            )
                                                            .collect(Collectors.toList()));


        for(var dataProject: dataProjects){
            dataProject.getIcDelete().getStyleClass().add("icon-node");
            dataProject.getIcDelete().setOnMouseClicked(event -> {
                dataProjects.remove(tbProject.getSelectionModel().getSelectedIndex());
            });
        }

        colCheck.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        colProDel.setCellValueFactory(new PropertyValueFactory<>("icDelete"));
        colProNum.setCellValueFactory(new PropertyValueFactory<>("projectNumber"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colProStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        tbProject.setItems(dataProjects);
        tbProject.setFocusTraversable(false);
        tbProject.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbProject.getSelectionModel().setCellSelectionEnabled(true);

    }




    private FxControllerAndView<DashboardController, Node> dashboardPageCV;

    @FXML
    public void onBtnSearchClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void onLbBtnResetSearchClicked(MouseEvent mouseEvent) {

    }

    @FXML
    public void displaySelected(MouseEvent mouseEvent) {
        var project = tbProject.getSelectionModel().getSelectedCells();
//        System.out.println(project);
//        System.out.println(project.get(0).getColumn());
        /*if(project == null){
            System.out.println("Nothing Selected");
        }else{
            System.out.println(project);
        }*/
    }
}
