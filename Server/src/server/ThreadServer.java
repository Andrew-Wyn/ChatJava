package server;

// Thread Server
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ThreadServer implements Callable<Void>{
    public Socket client;
    private Monitor monitor;
    
    public ThreadServer(Socket clients, Monitor monitor){
        this.client = clients;
        this.monitor = monitor;
    }
    
    @Override
    public Void call() throws Exception {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(client.getInputStream()));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        
        dos.writeUTF(dis.readUTF());
        dos.flush();
         
        return null;
    }
}
