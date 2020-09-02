# Chat-Server
This is socket or networking programming. This program consists of two client namely client1 and client3 and one server called as ChatServer.Here user can send message from client1 to client2 and vice versa being through server. In order to run the chat service following process should be done :
•	First the server should be Run
•	Then after client1 should be Run 
•	Now user should give the user name and click the connect button to connect with server
•	Similarly for client 3  , first it should be run and username should be typed and finally click connect button.
•	Now both client are connected to same server so they can exchange message through the GUI
•	When we have to send message then we can type message in textfield and click send message 
•	Once we get  message from the other client it will be displayed in the textbox with user name.


              
      
When the server is run :
When the server is run then it starts the server and waits for the client to accept the socket object .If the client is not available then it waits for the server .Once the server got the client then it performs the bufferwriter or reader operation .And create another server socket object to accept the next client .Hence this process continue and the chacting process between the two client is serve by the chat server.

 



Following is  Client1:
 The client1 in which it consist of the GUI for the chatting process,It consist of name textfield to insert the name of person who is using the clilent and ip address for the connection with the server of the network.Here we are using the same server and client in the same computer so we are using the local host .Another item in this program is connect button which help to connect the server with the this client.Once this is connected to the server then now we go for chating .Any message coming from the server is displayed in the text box and if we have the send message then we can user textfield to type message and send the message by clicking the send message  button.



Client3:	
the client3 in which it consist of the GUI for the chatting process,It consist of name textfield to insert the name of person who is using the clilent and ip address for the connection with the server of the network.Here we are using the same server and client in the same computer so we are using the local host .Another item in this program is connect button which help to connect the server with the this client.Once this is connected to the server then now we go for chating .Any message coming from the server is displayed in the text box and if we have the send message then we can user textfield to type message and send the message by clicking the send message  button.



This is the client class which is used to connect to the server .It contains the GUI for chat 
message The message from the this client is send to the next similar client being through the server 
