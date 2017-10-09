package messenger.server;

import messenger.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {


    private PrintWriter output;
    private BufferedReader input;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private final String name = "[ADAM] - ";

    private boolean isConnected;
    private TextArea tx;
    private TextField inputField;

    public Server(String IP, int port) {
        isConnected = false;
        createGUI();
        waitForConnection(IP, port);
        setupStreams();
        stayConnected();

    }

    private void stayConnected() {

            while (isConnected) {
                try {
                    String message = input.readLine();
                    tx.append(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }

    private void setupStreams() {
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createGUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(875, 545));
        contentPanel.setBackground(new Color(0, 0, 0));

        fillContentPanel(contentPanel);

        this.setContentPane(contentPanel);
        this.setTitle("Messenger");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }

    private void fillContentPanel(JPanel panel) {


        Box contentBox = ObjectCreator.getBox(panel.getWidth(), panel.getHeight(), ObjectCreator.VBOX);

        tx = new TextArea();
        tx.setEditable(false);
        tx.setBackground(new Color(45,45,45));
        tx.setForeground(Color.CYAN);

        tx.setText("Yo");
        contentBox.add(tx);

        inputField = new TextField();
        inputField.setForeground(Color.CYAN);
        inputField.setBackground(new Color(45,45,45));

        contentBox.add(ObjectCreator.getSpacer(15, ObjectCreator.VBOX));

        inputField.addActionListener(Event -> {

            String messageText = inputField.getText();
            sendMessage(messageText);

            tx.append(name + messageText + "\n");
            inputField.setText("");

        });



        contentBox.add(inputField);
        panel.add(contentBox);
    }

    private void sendMessage(String message) {
        output.println(name + message);
    }

    private void waitForConnection(String IP, int port) {

        try {
            System.out.println("Waiting for connection...");
            serverSocket = new ServerSocket(port);


            clientSocket = serverSocket.accept();

            System.out.println("Connection established with " + clientSocket.getInetAddress());

            isConnected = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





















    public static void main(String[] args) {

        SwingUtilities.invokeLater( () -> new Server("127.0.0.1", 1555));


    }



}
