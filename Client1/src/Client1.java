import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.control.Label;
/*
This is the client class which is used to connect to the server .It contains the GUI for chat 
message The message from the this client is send to the next similar client being through the server 

*/

public class Client1 extends Application implements Runnable{
    Socket socket;// this is scocket varible decleratio n
    protected BufferedReader in;// this in buffer reader varible
    PrintWriter out;// this is print writer object

    
    Label lbl_uerName= new Label("Client Name          ");//this is label for client who uses 
    TextField text_username = new TextField();// textfield for client to insert their name;

   // Text serverIpMessage = new Text("IP Address ");
    
    Label lblIpAddress= new Label("IP Address To Connect");// label  for ip address
    TextField text_serverIp = new TextField("127.0.0.1");// Text fiel for ip address default ie for local host it is 127.0.0.1
    Button connect = new Button(" Here You Connect");// This is button for senteing signal to connect
  //  Button btn_disconnect = new Button("Disconnect");
    
    Label lbl= new Label("Here You Will Get Text Message from another Client which is Connected to above Ip Address");// This is 
    // label for text message 
    
    TextArea messageComing = new TextArea();// this is text area for incomming message from other client being through the server
    
    //TextArea chatBox = new TextArea();
    TextField text_message = new TextField();// this is test field for sending message 
    Button sendMessage = new Button("Send");// this is button for sending message to other client being through the server 

    public static void main(String[] args)
    {
        Application.launch(args);// this launches the application 
    }

    @Override
    public void start(final Stage stage)// this if start method for GUI
    {
        text_message.setMinWidth(550);// sets the width of message

        GridPane root = new GridPane();// making grid pane object
        root.setVgap(30);// setting v gap  for gridpane
        root.setHgap(30);// setting  h gap for gridpane

        GridPane pane = new GridPane();// 
        pane.setHgap(30);
        pane.setVgap(30);
        pane.add(lbl_uerName, 2,0);//seting position for username
        pane.add(text_username, 3, 0);// setting positioon for text field of username
        pane.add(lblIpAddress, 2, 1);// setting position for label of ip address
        
        pane.add(text_serverIp, 3, 1);// setting position for text field of ip address
        pane.add(connect, 2, 2);// setting position for button of connet button

        root.add(pane, 0,0,1,1);
        root.add(lbl,1,1);
        root.add(messageComing, 1,1,3,1);
        root.add(text_message, 1, 2, 2,1);
        root.add(sendMessage, 2, 2);


        sendMessage.setOnAction(event -> {// this is action when the sendmessage button in click
            out.println(text_message.getText());// message from text field is 
            text_message.setText("");// 
            text_message.requestFocus();
        });

        connect.setOnAction(event -> {// event for connect button 
            String username = text_username.getText();
            if (!username.isEmpty()) {
                Thread t = new Thread(this);// creats thread of this class
                t.start();//. starts the thread 
                System.out.println("Thread started...");
            }
            else{
                text_username.requestFocus();
            }
        });
//       

        Scene scene = new Scene(root);// creating scee
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("Chat Application for Client 1");
        // Display the Stage
        stage.show();// shows the display
    }
    
    /*
    this is method for assinging the the serverip address 
    */

    private String assignServerAddress() {
        String serverIp = text_serverIp.getText();//. get the ip address from the  text field
        if (serverIp.isEmpty())
            return "127.0.0.1";
        return serverIp;
    }


    /**
     * Connects to the server then enters the processing loop.
     */
    public void run() {


        // Make connection and initialize streams
        String serverAddress = assignServerAddress();// assign the ip address to a variable
        System.out.println("server address: " + serverAddress);

        try {
            socket = new Socket(serverAddress, 2000);// creats the socket of ip address  2000
            in = new BufferedReader(new InputStreamReader(// this in input stream reader object
            socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        }

        // Process all messages from server, according to the protocol.
        while (true) {
            String serverMessage = null;
            try {
                serverMessage = in.readLine();// seads line form the serverMessage
            } catch (IOException e) {
            }
            assert serverMessage != null;
            if (serverMessage.startsWith("USERNAME_REQUIRED")) {
                out.println(text_username.getText());
                System.out.println("Not connected");
            }
            else if (serverMessage.startsWith("USERNAME_ACCEPTED")) {
                
                connect.setDisable(true);//connection is disabled

               

                
                Platform.runLater(() -> text_message.requestFocus());// runs later
            }
            else if (serverMessage.startsWith("MESSAGE")) {
                messageComing.appendText(serverMessage.substring(8) + "\n");// message
            }
        }
    }
}