import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class Main extends Application {

    public static void main(String [] aStrings)
    {
        launch(aStrings);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vnc.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("VNC");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
