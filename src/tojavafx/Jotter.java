package tojavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Jotter extends Application {

    // menu bar items
    Menu fileMenu = new Menu("File");
    Menu editMenu = new Menu("Edit");
    Menu helpMenu = new Menu("Help");

    // sub menu bar items
    MenuItem newItem = new MenuItem("New");
    MenuItem openItem = new MenuItem("Open");
    MenuItem saveItem = new MenuItem("Save");
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem aboutItem = new MenuItem("About");
    Menu color = new Menu("Color");
    MenuItem bgColor = new MenuItem("Background Color");
    MenuItem fontColor = new MenuItem("Font Color");
    Menu lineWrap = new Menu("Line Wrap");
    MenuItem lineWrapOn = new MenuItem("ON");
    MenuItem lineWrapOff = new MenuItem("OFF");

    public static void main(String[] args) {
        launch(args);
    }

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
    }

    void design() {
        fileMenu.getItems().addAll(newItem, openItem, saveItem, exitItem);
    }
}
