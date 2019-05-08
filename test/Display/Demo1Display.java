package test.Display;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Demo1Display extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("/Users/Boris.Leonard/Desktop/vivien/STAGE/fedx-master"));

        GridPane gPane = new GridPane();
        Button bFile = new Button("Configuration file ...");
        Label lChooser = new Label("File :    ");
        Label textField = new Label();
        Label info = new Label("Working directory : "+fileChooser.getInitialDirectory().getName());
        gPane.add(info,0,0);
        gPane.add(lChooser,0,3);
        gPane.add(bFile,1,3);
        gPane.add(textField, 2,3);

        bFile.setOnAction(event -> {
            fileChooser.setInitialDirectory(new File("/Users/Boris.Leonard/Desktop/vivien/STAGE/fedx-master"));
            File f = fileChooser.showOpenDialog(primaryStage);

            if (f != null){
                textField.setText(f.getName());
            }
        });

        VBox vGlobal = new VBox(gPane);
        vGlobal.setPadding(new Insets(10,10,10,10));
        primaryStage.setScene(new Scene(vGlobal,800,700));
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
