package com.elca.internship.client.controllers;

import com.elca.internship.client.StageReadyEvent;
import com.elca.internship.client.adapter.ProjectAdapter;
import com.elca.internship.client.api.news.ProjectRest;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


@Component
@FxmlView("/views/viewListProject.fxml")
@RequiredArgsConstructor
@Slf4j
public class ViewListProjectController implements Initializable, ApplicationListener<StageReadyEvent> {
    private final FxWeaver fxWeaver;
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
    public TableColumn<ProjectTable, LocalDate> colProStart;
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
    private final ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private final IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private FxControllerAndView<RemoveItemPaneController, Node> removeItemPaneCV;
    int itemPerPage = 5;
    private final ProjectAdapter projectAdapter;


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
        tbProject.prefHeightProperty().bind(tbProject.fixedCellSizeProperty().multiply(6));
        tbProject.minHeightProperty().bind(tbProject.prefHeightProperty());
        tbProject.maxHeightProperty().bind(tbProject.prefHeightProperty());

        colCheck.setMinWidth(30);
        colCheck.setMaxWidth(30);
        colProNum.setMinWidth(100);
        colProNum.setMaxWidth(100);
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
        colProStart.setCellFactory(column -> new TableCell<>() {
                    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    @Override
                    protected void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(format.format(item));
                        }
                    }
                }
        );

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
            removeItemPaneCV.getController().hbLayout.setVisible(numCheckBoxesSelected.intValue() > 0);
        }));

        colProNum.setComparator((o1, o2) -> {
            var proNumber1 = Long.parseLong(o1.getText());
            var proNumber2 = Long.parseLong(o2.getText());
            if(proNumber1 >= proNumber2){
                return -1;
            }else{
                return 1;
            }
        });

    }


    private void createPage(int pageIndex, String tfSearch, String cbStatus){
        selectedCheckBoxes.clear();
        paginationTableProject.setCurrentPageIndex(pageIndex);
        dataProjects = projectAdapter.getAndConfigAllProjectTableData(this);
        tbProject.setItems(dataProjects);

    }

    public void fillDataProjectToTable(String tfSearch, String status){
        IconFontFX.register(GoogleMaterialDesignIcons.getIconFont());
//        int size = projectRestConsume.getNumberOfResultSearch(tfSearch, status);
        int size = 20;
        paginationTableProject.setPageCount((size/5));
        // set contains 20 item
        this.createPage(0, tfSearch, status);
        paginationTableProject.currentPageIndexProperty().addListener((observableValue, oldIdx, newIdx) -> {
            if(oldIdx.intValue() != newIdx.intValue()){
                this.createPage(newIdx.intValue(),tfSearch, status);
            }
        });
    }

    private ObservableSet projectDataTable;

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
        projectDataTable = projectAdapter.retrieveProjectWithCriteriaAndStatusSpecified(tfSearchValue, cbStatusValue);
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
                /*if(projectRestConsume.removeProjectById(projectTableDeleted.getId()).getTypeError() != -1){
                    dataProjects.remove(projectTableDeleted);
                    onBtnSearchClicked();
                }*/

                projectAdapter.deleteProjectById(projectTableDeleted.getId());
                // check if delete success
                dataProjects.remove(projectTableDeleted);
                onBtnSearchClicked();

            }
        };
    }

    public void configureCheckBox(CheckBox checkBox){
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

    public EventHandler<MouseEvent> deleteMultiItemHandler(){
        return event -> {
            var confirm = this.createConfirmationDeleteProjectDialog();
            confirm.showAndWait();
            if(confirm.getResult() == ButtonType.YES){
                var dataProjectDeleted = dataProjects.stream().filter(e -> e.getCheckBox().isSelected()).toList();
                var listIdDelete = dataProjectDeleted.stream().map(ProjectTable::getId).toList();
                /*if(projectRestConsume.removeProjectsByIds(listIdDelete).getTypeError()!=-1){
                    dataProjects.removeAll(dataProjectDeleted);
                    selectedCheckBoxes.clear();
                    removeItemPaneCV.getController().hbLayout.setVisible(false);
                    onBtnSearchClicked();
                }*/

                projectAdapter.deleteProjectByIds(listIdDelete);

                dataProjects.removeAll(dataProjectDeleted);
                selectedCheckBoxes.clear();
                removeItemPaneCV.getController().hbLayout.setVisible(false);
                onBtnSearchClicked();

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
        removeItemPaneCV.getController().lbDeleteItemSelected.setText(i18nManager.text(I18nKey.MSG_DELETE_ITEM_SELECTED_TITLE));
    }

}
