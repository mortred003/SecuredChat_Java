package peer2peerChat;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MbajtesKlientit implements Runnable{
    public static ArrayList<MbajtesKlientit> MbajtesKlientitList = new ArrayList<>();
    private Socket socket;
    private String username;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    MbajtesKlientit(Socket socket){
        try{
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            username = dataInputStream.readUTF();
            MbajtesKlientitList.add(this);
            messageToAll("SERVER :\n"+username+" has entered the group chat...");
        }catch(IOException ex){
            closeEverything(socket,dataInputStream,dataOutputStream);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()){
            try {
                String sample;
                sample = dataInputStream.readUTF();
                if(sample.substring(0,5).equals("_OneM")){
                    String[] messagePortions = sample.split(" ");
                    String sendingTo = messagePortions[1];
                    String pMessage= username +" (Private Message For You) :";
                    for (int i=2;i<messagePortions.length; i++){
                        pMessage += messagePortions[i]+ " ";
                    }
                    privateMessage(sendingTo, pMessage);
                }
                else if(sample.substring(0,5).equals("_list")){
                    listOfUsers();
                }
                else{
                    String message = username +" : "+sample;
                    messageToAll(message);
                }
            }catch (IOException ex){
                System.out.println("Client has been disconnected !!!");
                closeEverything(socket,dataInputStream,dataOutputStream);
                break;
            }
        }
    }

    public void messageToAll(String message){
        for(MbajtesKlientit MbajtesKlientit: MbajtesKlientitList){
            try{
                if(!MbajtesKlientit.equals(this)){
                    MbajtesKlientit.dataOutputStream.writeUTF(message);
                    MbajtesKlientit.dataOutputStream.flush();
                }
            }catch (IOException ex){
                closeEverything(socket,dataInputStream,dataOutputStream);
            }
        }
    }

    public void privateMessage(String sendingTo, String message){
        for (MbajtesKlientit MbajtesKlientit : MbajtesKlientitList){
            if(MbajtesKlientit.username.equals(sendingTo)){
                try{
                    MbajtesKlientit.dataOutputStream.writeUTF(message);
                    MbajtesKlientit.dataOutputStream.flush();
                }catch (IOException ex){
                    closeEverything(socket,dataInputStream,dataOutputStream);
                }
            }
        }
    }

    public void listOfUsers() throws IOException {
        String userList ="";
        for(MbajtesKlientit MbajtesKlientit : MbajtesKlientitList){
            userList += MbajtesKlientit.username+"\n";
        }
        userList += "---------------------------------\n";
        dataOutputStream.writeUTF(userList+'\n');
        dataOutputStream.flush();
    }

    public void closeEverything(Socket socket, DataInputStream dataInputStream,DataOutputStream dataOutputStream){
        removeClient();
            try{
                if(dataInputStream != null){
                    dataInputStream.close();
                }
                if(dataOutputStream != null){
                    dataOutputStream.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException ex){
                ex.printStackTrace();
            }
    }

    public void removeClient(){
        MbajtesKlientitList.remove(this);
        messageToAll("Server: "+username+" has left the chat");
    }
}
