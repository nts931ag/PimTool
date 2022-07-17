package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.api.RestTemplateConsume;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import org.springframework.web.client.ResourceAccessException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@FxmlView("/views/viewListProject.fxml")
@RequiredArgsConstructor
public class ViewListProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private final ProjectRestConsume projectRestConsume;
    private Stage stage;
    private final I18nManager i18nManager;
    private FxControllerAndView<CreateProjectController, Node> tabCreateProjectCV;
    private final RestTemplateConsume restTemplateConsume;
    @FXML
    public VBox vbListProject;
    @FXML
    public HBox hbFilterListProject;
    @FXML
    public Pagination paginationTableProject;
    @FXML
    public TableColumn<ProjectTable,CheckBox> colCheck;
    @FXML
    public TableColumn<ProjectTable,Label> colProNum;
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

        tbProject.setFocusTraversable(false);
        tbProject.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbProject.getSelectionModel().setCellSelectionEnabled(true);

        createTableProject();
    }

    public void createTableProject(){
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
        var projects = restTemplateConsume.getAllProject();
        dataProjects = FXCollections.observableArrayList(Arrays
                                                            .stream(projects)
                                                            .map(e ->
                                                                new ProjectTable(
                                                                        new CheckBox(),
                                                                        e.getId(),
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
                                                            .toList());


        for(var dataProject: dataProjects){
            dataProject.getIcDelete().getStyleClass().add("icon-node");
            dataProject.getIcDelete().setOnMouseClicked(event -> {
                var projectTableDeleted = dataProjects.get(tbProject.getSelectionModel().getSelectedIndex());
                dataProjects.remove(projectTableDeleted);
                projectRestConsume.removeProjectById(projectTableDeleted.getId());
            });
            dataProject.getLbProNumLink().getStyleClass().add("lb-super-link");
            dataProject.getLbProNumLink().setOnMouseClicked(event -> {
                navigateToTabEditProject(dataProject);
            });
        }

        colCheck.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        colProDel.setCellValueFactory(new PropertyValueFactory<>("icDelete"));
        colProNum.setCellValueFactory(new PropertyValueFactory<>("lbProNumLink"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colProStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        tbProject.setItems(dataProjects);
    }



    private void navigateToTabEditProject(Project dataProject) {
        // get and set contentContainer
        var borderPane = (BorderPane) stage.getScene().getRoot().getChildrenUnmodifiable().get(2);
        var titleContentContainer =  (Label)borderPane.getChildren().get(1);
        var contentContainer = (Pane) borderPane.getChildren().get(0);

        tabCreateProjectCV = fxWeaver.load(CreateProjectController.class, i18nManager.bundle());
        tabCreateProjectCV.getView().ifPresent(view ->{
            contentContainer.getChildren().clear();
            contentContainer.getChildren().add(view);
        });
        tabCreateProjectCV.getController().initEditProjectLayout(dataProject);
        titleContentContainer.setText("Edit project screen");
    }




    private FxControllerAndView<DashboardController, Node> dashboardPageCV;

    @FXML
    public void onBtnSearchClicked(ActionEvent actionEvent) {
        var tfSearchValue = tfSearch.getText();
        var cbStatusValue = cbStatus.getValue();
        if(tfSearchValue.isBlank() && cbStatusValue == null){
            createTableProject();
        }else{
            if(tfSearchValue.isBlank()){
                tfSearchValue = "all";
            }else{
                if(cbStatusValue == null){
                    cbStatusValue = "all";
                }
            }

            var listProject = restTemplateConsume.searchProjectByCriteria(tfSearchValue, cbStatusValue);
            System.out.println(listProject);

        }
    }

    @FXML
    public void onLbBtnResetSearchClicked(MouseEvent mouseEvent) {
        tfSearch.clear();
        cbStatus.getSelectionModel().clearSelection();
        cbStatus.setPromptText("Project status");
        createTableProject();

    }

    @FXML
    public void displaySelected(MouseEvent mouseEvent) {

    }
}
