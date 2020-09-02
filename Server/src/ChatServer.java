import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/** This is the server class which is used to connect to the other clients and get and send message being throung it 
 * This class use multithreading concept/
 * This class is the server class.
 * here multi threading concept is used.
 * for each  client a new thread is started to send receive message.
 * This server is not GUI .This is just to server the all clients which wants for chatin 
 * It can server many clients at a time 
 * When a message is received from the one client then it passes that message to the other clients
 * In similar fashion it works
 *
 */
public class ChatServer {

    private static final int PORT =2000; // port number
    private static final HashSet<String> names = new HashSet<>(); // this is tore all the  name of client .
    private static HashSet<PrintWriter> writers = new HashSet<>(); // to store all the outmessage stream for each client.
    public static void main(String[] args) throws Exception {// main method

        System.out.println("Server Started. Ready to accept clients.");

        ServerSocket serverSocket = new ServerSocket(PORT);// serverSocket object created
        try {
            while (true) {// runs infinite time 
                new ClientHandler(serverSocket.accept()).start();// pass the socket object and starts the thread
            }
        } finally {
            serverSocket.close();// socket closes
        }
    }

    //thread that gets started after each client is added.
    private static class ClientHandler extends Thread {
        private String username;    //username of the client.
        private Socket socket;      // socket
        private BufferedReader inMessage;   //to read all message sent from the client.
        private PrintWriter outMessage;     //to send message to the clients.

        ClientHandler(Socket socket) {
            this.socket = socket;// assing the server object
        }

        public void run() {
            try {
                inMessage = new BufferedReader(new InputStreamReader(socket.getInputStream())); // read the input stream from the clients.

                outMessage = new PrintWriter(socket.getOutputStream(), true);   //out stream the message to the clients. the first time for reading the username.

                while (true) {// runs untill there is connection
                    outMessage.println("USERNAME_REQUIRED");    // if there are no user
                    username = inMessage.readLine();        //this will ask again
                    if (username == null) {// checks if null
                        return;// returns 
                    }
                    synchronized (names) {// synchronises
                        if (!names.contains(username)) {        //saves  clients names.
                            names.add(username);// add client names to list
                            System.out.println(" connection OK.");// message for successfull connection
                            
                            break;
                        }
                    }
                }

                outMessage.println("You_ Are_ACCEPTED");    // when username is accepted.
                writers.add(outMessage);    //storing all the outstream for all the clients.

                while (true) { // reads the message until username is null
                    String input = inMessage.readLine();
                    if (input == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {            //send message to all the outstream to all the clients.
                        writer.println("MESSAGE " + username + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);// prints  exception if there is exception
            } finally {
                if (username != null) {// checls if null
                    names.remove(username);// remove the usernames
                }
                if (outMessage != null) {
                    writers.remove(outMessage);
                }
                try {
                    socket.close();
                } catch (IOException e) {// for exception
                }
            }
        }
    }
}