package com.metis.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.HashMap;

public class NotesController {

    @FXML private TextField noteTitleField;
    @FXML private TextArea noteContentArea;
    @FXML private ListView<String> notesList;

    private final HashMap<String, String> notes = new HashMap<>();

    @FXML
    private void initialize() {
        System.out.println("✅ NotesController initialized");
    }

    @FXML
    private void saveNote() {
        String title = noteTitleField.getText().trim();
        String content = noteContentArea.getText();

        if (title.isEmpty()) {
            showAlert("Missing title", "Please enter a title before saving.");
            return;
        }

        notes.put(title, content);
        if (!notesList.getItems().contains(title)) {
            notesList.getItems().add(title);
        }

        noteTitleField.clear();
        noteContentArea.clear();
        showAlert("Saved", "Note saved successfully!");
    }

    @FXML
    private void loadSelectedNote() {
        String selected = notesList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            noteTitleField.setText(selected);
            noteContentArea.setText(notes.get(selected));
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
