package com.metis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainDashboardController {

    @FXML
    private StackPane contentArea;

    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDashboard() {
        loadView("DashboardView.fxml");
    }

    @FXML
    private void openCalendar() {
        loadView("CalendarView.fxml");
    }

    @FXML
    private void openTodo() {
        loadView("TodoView.fxml");
    }

    @FXML
    private void openNotes() {
        loadView("NotesView.fxml");
    }

    @FXML
    private void openGpa() {
        loadView("GpaView.fxml");
    }

    @FXML
    private void openTimetable() {
        loadView("TimetableView.fxml");
    }

    @FXML
    private void openSettings() {
        loadView("SettingsView.fxml");
    }

    @FXML
    private void openBackup() {
        loadView("BackupView.fxml");
    }
}
