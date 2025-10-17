package com.metis.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimetableController {

    @FXML private TextField subjectField, timeField, locationField;
    @FXML private ComboBox<String> dayBox;
    @FXML private TableView<ClassEntry> timetableTable;
    @FXML private TableColumn<ClassEntry, String> dayCol, subjectCol, timeCol, locationCol;

    private final ObservableList<ClassEntry> classList = FXCollections.observableArrayList();
    private final File timetableFile = new File("data/timetable.json");

    @FXML
    public void initialize() {
        dayBox.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        timetableTable.setItems(classList);
        loadClasses();
    }

    @FXML
    private void addClass() {
        String day = dayBox.getValue();
        String subject = subjectField.getText();
        String time = timeField.getText();
        String location = locationField.getText();

        if (day == null || subject.isEmpty() || time.isEmpty() || location.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        ClassEntry newClass = new ClassEntry(day, subject, time, location);
        classList.add(newClass);
        saveClasses();
        clearFields();
    }

    @FXML
    private void deleteSelected() {
        ClassEntry selected = timetableTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            classList.remove(selected);
            saveClasses();
        } else {
            showAlert("Please select a class to delete.");
        }
    }

    private void clearFields() {
        subjectField.clear();
        timeField.clear();
        locationField.clear();
        dayBox.setValue(null);
    }

    private void saveClasses() {
        JSONArray arr = new JSONArray();
        for (ClassEntry entry : classList) {
            JSONObject obj = new JSONObject();
            obj.put("day", entry.getDay());
            obj.put("subject", entry.getSubject());
            obj.put("time", entry.getTime());
            obj.put("location", entry.getLocation());
            arr.put(obj);
        }

        try (FileWriter writer = new FileWriter(timetableFile)) {
            writer.write(arr.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClasses() {
        if (!timetableFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(timetableFile))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) json.append(line);

            JSONArray arr = new JSONArray(json.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                classList.add(new ClassEntry(
                        obj.getString("day"),
                        obj.getString("subject"),
                        obj.getString("time"),
                        obj.getString("location")
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // === Inner class for data model ===
    public static class ClassEntry {
        private final String day, subject, time, location;
        public ClassEntry(String day, String subject, String time, String location) {
            this.day = day; this.subject = subject; this.time = time; this.location = location;
        }
        public String getDay() { return day; }
        public String getSubject() { return subject; }
        public String getTime() { return time; }
        public String getLocation() { return location; }
    }
}
