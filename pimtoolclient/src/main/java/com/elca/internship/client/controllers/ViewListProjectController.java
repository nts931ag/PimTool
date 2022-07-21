package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

import java.net.URL;
import java.util.ResourceBundle;


@Component
@FxmlView("/views/viewListProject.fxml")
@RequiredArgsConstructor
public class ViewListProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
    private final ProjectRestConsume projectRestConsume;
    private Stage stage;
    private final I18nManager i18nManager;
    @FXML
    public VBox vbListProject;
    @FXML
    public HBox hbFilterListProject;
    @FXML
    public VBox vbTableView;

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

    private ObservableList<ProjectTable> dataProjects;

    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(numCheckBoxesSelected.getValue());

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
        fillValueToLayout();
    }

    private FxControllerAndView<RemoveItemPaneController, Node> removeItemPaneCV;

    private void initLayout() {
        hbFilterListProject.setPadding(new Insets(25,0,25,0));
        cbStatus.setPrefWidth(200);
        btnSearch.setPrefWidth(200);
        tfSearch.setFocusTraversable(false);

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

        removeItemPaneCV = fxWeaver.load(RemoveItemPaneController.class, i18nManager.bundle());
        removeItemPaneCV.getView().ifPresent(view -> {
            vbTableView.getChildren().add(view);
            removeItemPaneCV.getController().setBindingItemQuantity(numCheckBoxesSelected);
            view.setVisible(false);
        });
    }


    private void fillValueToLayout() {

        var listStatus = FXCollections.observableArrayList(
                i18nManager.text(I18nKey.PROJECT_STATUS_NEW)
                , i18nManager.text(I18nKey.PROJECT_STATUS_PLANNED)
                , i18nManager.text(I18nKey.PROJECT_STATUS_IN_PROGRESS)
                , i18nManager.text(I18nKey.PROJECT_STATUS_FINISHED)
        );
        cbStatus.setItems(listStatus);
        cbStatus.getSelectionModel().select(null);
        cbStatus.setButtonCell(new ListCell<>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(i18nManager.text(I18nKey.PROJECT_STATUS));
                }else{
                    setText(item);
                }
            }
        });
        tbProject.setFocusTraversable(false);
        tbProject.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tbProject.getSelectionModel().setCellSelectionEnabled(true);

        fillDataProjectToTable(null, null);
    }


    public void fillDataProjectToTable(String tfSearch, String status){
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
        var projects = projectRestConsume.searchProjectByCriteriaSpecified(tfSearch, status);
        dataProjects = FXCollections.observableArrayList(projects.stream()
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
            if(dataProject.getStatus().toString().equalsIgnoreCase("new")){
                dataProject.getIcDelete().getStyleClass().add("icon-node");
                dataProject.getIcDelete().setOnMouseClicked(event -> {
                    var projectTableDeleted = dataProjects.get(tbProject.getSelectionModel().getSelectedIndex());
                    dataProjects.remove(projectTableDeleted);
                    projectRestConsume.removeProjectById(projectTableDeleted.getId());
                });
                configureCheckBox(dataProject.getCheckBox());
            }else{
                dataProject.setIcDelete(null);
                dataProject.getCheckBox().setDisable(true);
            }
            dataProject.getLbProNumLink().getStyleClass().add("lb-super-link");
            dataProject.getLbProNumLink().setOnMouseClicked(event -> {
                DashboardController.navigationHandler.handleNavigateToEditProject(dataProject);
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

        numCheckBoxesSelected.addListener(((observable, oldValue, newValue) -> {
            if(numCheckBoxesSelected.intValue() > 0){
                removeItemPaneCV.getController().hbLayout.setVisible(true);
            }else{
                removeItemPaneCV.getController().hbLayout.setVisible(false);
            }
        }));
    }

    private void configureCheckBox(CheckBox checkBox){
        if(checkBox.isSelected()){
            selectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                selectedCheckBoxes.add(checkBox);
            }else{
                selectedCheckBoxes.remove(checkBox);
            }
        }));
    }

    @FXML
    public void onBtnSearchClicked(ActionEvent actionEvent) {
        var tfSearchValue = tfSearch.getText();
        var cbStatusValue = cbStatus.getSelectionModel().getSelectedItem();

        if(tfSearchValue.isBlank() && cbStatusValue == null){
            fillDataProjectToTable(null, null);
        } else if (!tfSearchValue.isBlank() && cbStatusValue != null) {
            fillDataProjectToTable(tfSearchValue, Project.Status.convertStringStatusToStatus(cbStatusValue, i18nManager).name());
        } else{
            if(tfSearchValue.isBlank() && cbStatusValue != null){
                fillDataProjectToTable(null, Project.Status.convertStringStatusToStatus(cbStatusValue, i18nManager).name());
            }else{
                fillDataProjectToTable(tfSearchValue, null);
            }
        }
    }



    @FXML
    public void onLbBtnResetSearchClicked(MouseEvent mouseEvent) {
        tfSearch.clear();
        cbStatus.getSelectionModel().select(-1);
        fillDataProjectToTable(null, null);

    }

    @FXML
    public void displaySelected(MouseEvent mouseEvent) {

    }
}
