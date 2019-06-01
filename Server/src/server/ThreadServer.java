package server;

// Thread Server
import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class ThreadServer implements Callable<Void>{
    public Socket client;
    private Monitor monitor;
    DataInputStream dis;
    DataOutputStream dos;
    public String nome;
    
    public ThreadServer(Socket clients, Monitor monitor){
        this.client = clients;
        this.monitor = monitor;
    }
    
    @Override
    public Void call() throws Exception {
        try {
            
            dis = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
            
            dis.readBoolean();
            nome = dis.readUTF(); // leggo il nome dal client
            System.out.println(nome);
            monitor.addInPool(this);
            monitor.aggiungiInLista(this);
            monitor.sendAllPartecipants(this);
            
            while(true){
                if(dis.readBoolean()){
                    monitor.broadCast(this, dis.readUTF());
                } else {
                    String [] in = dis.readUTF().split("-");
                    String dest = in[0].trim();
                    monitor.ptp(dest, in[1].trim(), nome);
                }
                
            }
            
        }catch(IOException e){
            // scollegato
        } finally{
            monitor.delete(this);
            dis.close();
            dos.close();
            client.close();
        }
        
        return null;
    }
}
