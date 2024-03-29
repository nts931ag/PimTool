package com.elca.internship.client.controllers;

import com.elca.internship.client.consume.EmployeeRestConsume;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/tagBar.fxml")
@RequiredArgsConstructor
public class TagBarController implements Initializable {

    private ObservableList<String> tags;
    private ObservableList<String> visaName;
    @FXML
    private FlowPane flowPaneLayoutTags;
    @FXML
    private TextField tfInputTag ;

    private final EmployeeRestConsume employeeRestClient;

    public void fillMemberIntoMembersField(ObservableList<String> tags){
        this.tags.clear();
        this.tags.addAll(tags);
    }

    public List<String> getVisas(){
        return tags.stream().map(element ->
                element.substring(0, 3)
        ).toList();
    }

    AutoCompletionBinding<String> autoCompletionBinding;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tags = FXCollections.observableArrayList();
        visaName = FXCollections.observableArrayList();
        flowPaneLayoutTags.getStyleClass().setAll("tag-bar");
        flowPaneLayoutTags.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            tfInputTag.requestFocus();
        });
        flowPaneLayoutTags.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                tfInputTag.requestFocus();
            }
        });
        /*tfInputTag.setOnAction(evt -> {
            String text = tfInputTag.getText();
            if (!text.isEmpty() && !tags.contains(text)) {
                tags.add(text);
                tfInputTag.clear();
            }
        });*/

        visaName.addAll(employeeRestClient.retrieveVisaAndNameOfAllEmployees());

        autoCompletionBinding = TextFields.bindAutoCompletion(
                tfInputTag
                , visaName
        );
        autoCompletionBinding.setVisibleRowCount(3);
        autoCompletionBinding.minWidthProperty().bind(flowPaneLayoutTags.widthProperty().subtract(10));
        autoCompletionBinding.maxWidthProperty().bind(flowPaneLayoutTags.widthProperty().subtract(10));
        autoCompletionBinding.prefWidthProperty().bind(flowPaneLayoutTags.widthProperty().subtract(10));
        autoCompletionBinding.setOnAutoCompleted(event -> {
            String content = event.getCompletion();
            if(!tags.contains(content)){
                tags.add(content);
            }
            tfInputTag.clear();
        });

        tfInputTag.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.BACK_SPACE)){
                var text = tfInputTag.getText().trim();
                if(text.length()==0){
                    var sizeTag = tags.size();
                    if(sizeTag != 0){
                        tags.remove(sizeTag-1);
                    }
                }
            }
        });

        flowPaneLayoutTags.setHgap(5);
        flowPaneLayoutTags.setVgap(5);
        HBox.setHgrow(tfInputTag, Priority.ALWAYS);
        tfInputTag.setBackground(null);
        tags.addListener((ListChangeListener.Change<? extends String> change) -> {

            while (change.next()) {
                if (change.wasPermutated()) {
                    ArrayList<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.add(null);
                    }
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.set(change.getPermutation(i), flowPaneLayoutTags.getChildren().get(i));
                    }
                    flowPaneLayoutTags.getChildren().subList(change.getFrom(), change.getTo()).clear();
                    flowPaneLayoutTags.getChildren().addAll(change.getFrom(), newSublist);
                } else {
                    if (change.wasRemoved()) {
                        flowPaneLayoutTags.getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                    }
                    if (change.wasAdded()) {
                        flowPaneLayoutTags.getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).toList());
                    }
                }
            }
        });
    }

    private class Tag extends HBox {

        public Tag(String tag) {
            getStyleClass().setAll("tag");
            Button removeButton = new Button("X");
            removeButton.setOnAction(evt -> {
                tags.remove(tag);
                Platform.runLater(()->tfInputTag.requestFocus());
            });
            removeButton.getStyleClass().add("lb-super-link");
            Text text = new Text(tag);
            HBox.setMargin(text, new Insets(0, 0, 0, 5));
            getChildren().addAll(text, removeButton);
        }
    }

}
