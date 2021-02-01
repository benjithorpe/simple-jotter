package jotter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Jotter extends Application {

    // menu bar items
    Menu fileMenu = new Menu("File");
    Menu editMenu = new Menu("Edit");
    Menu helpMenu = new Menu("Help");
    FileChooser fileChooser = new FileChooser();

    // sub file menu items
    MenuItem newItem = new MenuItem("New");
    MenuItem openItem = new MenuItem("Open");
    MenuItem saveItem = new MenuItem("Save");
    MenuItem exitItem = new MenuItem("Exit");

    // sub edit menu items
    Menu color = new Menu("Color");
    // color menu items
    MenuItem bgColor = new MenuItem("Background Color");
    MenuItem fontColor = new MenuItem("Font Color");
    MenuItem replace = new MenuItem("Replace");
    Menu lineWrap = new Menu("Line Wrap");
    // line wrap menu items
    MenuItem lineWrapOn = new MenuItem("ON");
    MenuItem lineWrapOff = new MenuItem("OFF");

    //sub help menu item
    MenuItem aboutItem = new MenuItem("About");

    public static void main(String[] args) {
        launch(args);
    }

    // stores the name of the file that is currently open
    String currentOpenFile = null;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Simple Jotter");
        design();
        TextArea textArea = new TextArea();

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(textArea);

        Scene scene = new Scene(borderPane, 400, 300);

        stage.setScene(scene);
        stage.show();

        // actions for the buttons
        newItem.setOnAction((ActionEvent ae) -> {
            stage.setTitle("Jotter");
            textArea.clear();
            currentOpenFile = null;
        });

        saveItem.setOnAction((ActionEvent ae) -> {

            if (currentOpenFile != null) {
                try (FileWriter fileWriter = new FileWriter(new File(currentOpenFile));
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

                    bufferedWriter.write(textArea.getText());

                } catch (IOException e) {
                    // do nothing
                }
            } else {
                File selectedFile = fileChooser.showSaveDialog(stage);
                if (selectedFile != null) {
                    try (FileWriter fileWriter = new FileWriter(selectedFile.getAbsolutePath());
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

                        bufferedWriter.write(textArea.getText());
                        currentOpenFile = selectedFile.getAbsolutePath();
                        stage.setTitle(selectedFile.getName() + " - Jotter");
                    } catch (IOException e) {
                        // do nothing
                    }
                }
            }
        });

        openItem.setOnAction((ActionEvent ae) -> {
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                textArea.clear();

                try (FileReader fileReader = new FileReader(selectedFile);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);) {

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        textArea.appendText(line + "\n");
                    }

                    currentOpenFile = selectedFile.getAbsolutePath();
                    stage.setTitle(selectedFile.getName() + " - Jotter");
                } catch (IOException e) {
                    // do nothing
                }
            }
        });

        exitItem.setOnAction((ActionEvent ae) -> {
            Platform.exit();
        });

    }

    void design() {
        fileMenu.getItems().addAll(newItem, openItem, saveItem, exitItem);
        editMenu.getItems().addAll(color, replace, lineWrap);
        helpMenu.getItems().add(aboutItem);

        color.getItems().addAll(fontColor, bgColor);
        lineWrap.getItems().addAll(lineWrapOn, lineWrapOff);
    }
}
