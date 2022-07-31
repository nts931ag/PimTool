package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.consume.ProjectRestConsume;
import com.elca.internship.client.i18n.I18nKey;
import com.elca.internship.client.i18n.I18nManager;
import com.elca.internship.client.models.entity.Project;
import com.elca.internship.client.models.entity.ProjectTable;
import com.elca.internship.client.common.AlertDialog;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    private FxControllerAndView<RemoveItemPaneController, Node> removeItemPaneCV;
    int itemPerPage = 5;


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        this.stage = event.getStage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayout();
    }


    private void initLayout() {

        AnchorPane.setTopAnchor(vbListProject, 0.0);
        AnchorPane.setRightAnchor(vbListProject, 0.0);
        AnchorPane.setBottomAnchor(vbListProject, 0.0);
        AnchorPane.setLeftAnchor(vbListProject, 0.0);
        hbFilterListProject.setPadding(new Insets(25,0,25,0));
        cbStatus.setPrefWidth(200);
        btnSearch.setPrefWidth(200);
        tfSearch.setFocusTraversable(false);

        tbProject.setFixedCellSize(45);
        System.out.println(tbProject.getPrefHeight());
        System.out.println(paginationTableProject.getPrefHeight());
        /*tbProject.setFixedCellSize(50);
        tbProject.setPrefHeight((50.0 * (itemPerPage+1)) + 2);*/

        /*tbProject.skinProperty().addListener(((observable, oldValue, newValue) -> {
            Pane header =(Pane) tbProject.lookup("TableHeaderRow");
            header.prefHeightProperty().bind(tbProject.heightProperty().divide(6));
        }));*/

        /*tbProject.setFixedCellSize(45);
        tbProject.prefHeightProperty().bind(tbProject.fixedCellSizeProperty().multiply(6));
        tbProject.minHeightProperty().bind(tbProject.prefHeightProperty());
        tbProject.maxHeightProperty().bind(tbProject.prefHeightProperty());

        paginationTableProject.setPrefHeight(tbProject.getPrefHeight() + 50.0);
        paginationTableProject.setMinWidth(tbProject.getPrefHeight() + 50.0);
        paginationTableProject.setMaxWidth(tbProject.getPrefHeight() + 50.0);
*/
        colCheck.setMinWidth(30);
        colCheck.setMaxWidth(30);
        colProNum.setMinWidth(80);
        colProNum.setMaxWidth(80);
        colProName.setMinWidth(270);
        colProName.setPrefWidth(270);
        colProStatus.setMinWidth(70);
        colProStatus.setMaxWidth(70);
        colProCustomer.setMinWidth(170);
        colProCustomer.setPrefWidth(170);
        colProStart.setMinWidth(120);
        colProStart.setMaxWidth(120);
        colProDel.setMinWidth(90);
        colProDel.setMaxWidth(90);

        colCheck.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        colProDel.setCellValueFactory(new PropertyValueFactory<>("icDelete"));
        colProNum.setCellValueFactory(new PropertyValueFactory<>("lbProNumLink"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        colProStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colProStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        colCheck.getStyleClass().add("table-column-align-center");
        colProDel.getStyleClass().add("table-column-align-center");
        colProNum.getStyleClass().add("table-column-align-right");

        removeItemPaneCV = fxWeaver.load(RemoveItemPaneController.class, i18nManager.bundle());
        removeItemPaneCV.getView().ifPresent(view -> {
            vbTableView.getChildren().add(view);
            removeItemPaneCV.getController().setBindingItemQuantity(numCheckBoxesSelected);
            removeItemPaneCV.getController().iconNode.addEventHandler(MouseEvent.MOUSE_CLICKED, deleteMultiItemHandler());
            view.setVisible(false);
        });

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

        numCheckBoxesSelected.addListener(((observable, oldValue, newValue) -> {
            if(numCheckBoxesSelected.intValue() > 0){
                removeItemPaneCV.getController().hbLayout.setVisible(true);
            }else{
                removeItemPaneCV.getController().hbLayout.setVisible(false);
            }
        }));


    }

    private Node createPage(int pageIndex, String tfSearch, String cbStatus){
        selectedCheckBoxes.clear();
        var projects = projectRestConsume.retrieveProjectsWithPagination(tfSearch, cbStatus,pageIndex, itemPerPage);
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
                dataProject.getIcDelete().setOnMouseClicked(deleteItemHandler());
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

        tbProject.setItems(dataProjects);

        return vbTableView;
    }


    public void fillDataProjectToTable(String tfSearch, String status){
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());

        int size = projectRestConsume.getNumberOfResultSearch(tfSearch, status);
        if(size % 5 == 0){
            paginationTableProject.setPageCount((size/5));
        }else{
            paginationTableProject.setPageCount((size/5) + 1);
        }

        paginationTableProject.setPageFactory((pageIndex)->this.createPage(pageIndex, tfSearch,status));

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
    public void onBtnSearchClicked() {
        var tfSearchValue = tfSearch.getText();
        var cbStatusValue = cbStatus.getSelectionModel().getSelectedItem();
        log.info("Value of tfSearch {}, value of cbStatus {}", tfSearchValue, cbStatusValue);
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

    public Alert createConfirmationDeleteProjectDialog(){
        var alertDialog = new AlertDialog(
                i18nManager.text(I18nKey.ALERT_DIALOG_CONFIRMATION_TITLE)
                ,i18nManager.text(I18nKey.ALERT_DIALOG_CONFIRMATION_HEADER_TEXT)
                ,i18nManager.text(I18nKey.ALERT_DIALOG_CONFIRMATION_CONTENT_TEXT),
                Alert.AlertType.CONFIRMATION);
        return alertDialog.getConfirmationDeleteProjectDialog();
    }

    public EventHandler<MouseEvent> deleteItemHandler(){
        return event -> {
            var projectTableDeleted = dataProjects.get(tbProject.getSelectionModel().getSelectedIndex());
            var confirm = this.createConfirmationDeleteProjectDialog();
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES){
                if (projectTableDeleted.getCheckBox().isSelected()){
                    selectedCheckBoxes.remove(projectTableDeleted.getCheckBox());
                }
                if(projectRestConsume.removeProjectById(projectTableDeleted.getId()).getTypeError() != -1){
                    dataProjects.remove(projectTableDeleted);
                    onBtnSearchClicked();
                }
            }
        };
    }

    public EventHandler<MouseEvent> deleteMultiItemHandler(){
        return event -> {
            var confirm = this.createConfirmationDeleteProjectDialog();
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES){
                var dataProjectDeleted = dataProjects.stream().filter(e -> e.getCheckBox().isSelected()).toList();
                var listIdDelete = dataProjectDeleted.stream().map(ProjectTable::getId).toList();
                if(projectRestConsume.removeProjectsByIds(listIdDelete).getTypeError()!=-1){
                    dataProjects.removeAll(dataProjectDeleted);
                    selectedCheckBoxes.clear();
                    removeItemPaneCV.getController().hbLayout.setVisible(false);
                    onBtnSearchClicked();
                }
            }

        };
    }

    public void switchLanguage(){
        tfSearch.setPromptText(i18nManager.text(I18nKey.TEXTFIELD_PROMPT_TEXT_SEARCH));
        var listStatus = FXCollections.observableArrayList(
                i18nManager.text(I18nKey.PROJECT_STATUS_NEW)
                , i18nManager.text(I18nKey.PROJECT_STATUS_PLANNED)
                , i18nManager.text(I18nKey.PROJECT_STATUS_IN_PROGRESS)
                , i18nManager.text(I18nKey.PROJECT_STATUS_FINISHED)
        );
        var idxStatusCurrent = cbStatus.getSelectionModel().getSelectedIndex();
        cbStatus.setItems(listStatus);
        cbStatus.getSelectionModel().select(idxStatusCurrent);
        btnSearch.setText(i18nManager.text(I18nKey.BUTTON_SEARCH_PROJECT));
        lbBtnResetSearch.setText(i18nManager.text(I18nKey.BUTTON_RESET_SEARCH_PROJECT));
        colProNum.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_NUMBER));
        colProStatus.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_STATUS));
        colProDel.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_DELTE));
        colProCustomer.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_CUSTOMER));
        colProName.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_NAME));
        colProStart.setText(i18nManager.text(I18nKey.PROJECT_TABLE_COL_START_DATE));
    }

}
