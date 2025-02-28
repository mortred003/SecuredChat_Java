package peer2peerChat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServerSocket(){
        try{
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Personi i ri eshte regjistruar ne server.");
                MbajtesKlientit MbajtesKlientit = new MbajtesKlientit(socket);
                Thread clientThread = new Thread(MbajtesKlientit);
                clientThread.start();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        Server server = new Server(serverSocket);
        server.startServerSocket();
    }
}
