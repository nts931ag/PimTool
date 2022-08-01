package com.elca.internship.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
@FxmlView("/views/tagBar.fxml")
@RequiredArgsConstructor
public class TagBarController implements Initializable {

    private final ObservableList<String> tags = FXCollections.observableArrayList();
    public FlowPane flowPaneLayoutTags;
    public TextField tfInputTag ;


    public ObservableList<String> getTags() {
        return tags;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowPaneLayoutTags.getStyleClass().setAll("tag-bar");
//        getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//        tags = FXCollections.observableArrayList();
//        inputTextField = new TextField();
        tfInputTag.setOnAction(evt -> {
            String text = tfInputTag.getText();
            if (!text.isEmpty() && !tags.contains(text)) {
                tags.add(text);
                tfInputTag.clear();
            }
        });
//        flowPaneLayoutTags.setPrefHeight(50);
//        flowPaneLayoutTags.setPrefWrapLength(30);
//        tfInputTag.prefHeightProperty().bind(flowPaneLayoutTags.heightProperty());
        flowPaneLayoutTags.setHgap(5);
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
//        hboxLayoutTags.getChildren().add(tfInputTag);
    }

    private class Tag extends HBox {

        public Tag(String tag) {
            getStyleClass().setAll("tag");
            Button removeButton = new Button("X");
            removeButton.setOnAction(evt -> tags.remove(tag));
            Text text = new Text(tag);
            HBox.setMargin(text, new Insets(0, 0, 0, 5));
            getChildren().addAll(text, removeButton);
        }
    }

}
