# SecuredChat_Java
Peer-to-Peer Chat Application 💬

A simple Java-based peer-to-peer chat system that allows multiple clients to communicate in a group chat or send private messages. The project demonstrates socket programming and multi-threading in Java.
Features ✨

✔ Real-time communication: Clients can send and receive messages instantly.
✔ Multi-client support: The server manages multiple client connections.
✔ Private messaging: Users can send direct messages using _OneM <username> <message>.
✔ User list retrieval: Clients can view all connected users by sending _list.
✔ Threaded architecture: Each client runs in a separate thread for concurrent messaging.
Project Structure 📂

    Server.java – Manages client connections and starts the server.
    Clients.java – Handles client-side communication and message transmission.
    MbajtesKlientit.java – Manages active clients, broadcasts messages, and handles private messaging.

How to Run 🏃‍♂️

Start the server:
      java Server
Start clients:
      java Clients

Enter your username and start chatting! 
