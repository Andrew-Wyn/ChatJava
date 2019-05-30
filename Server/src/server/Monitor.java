package server;

// Monitor

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class Monitor {
    private ThreadPoolExecutor executor = null; 
    private ArrayList<ThreadServer> clients = null;
    
    public Monitor(int maxNumber){
        clients = new ArrayList();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxNumber);
    }
    
    public synchronized ThreadServer addInPool(ThreadServer ts){
        if(clients.add(ts)) return ts;
        else return null;
    }
    
    public void startNew(Socket client){
        executor.submit(addInPool(new ThreadServer(client, this))); // submit(run) accetta oggetti di interfaccia callable
    }
    
    public void stop(){
        executor.shutdown();
    }
    
    public synchronized void sendAllPartecipants(ThreadServer th){
        String out = "";
        for(ThreadServer thread : clients){
            if(thread != th){
                out += th.nome + "\n";
            }
        }
        try{
            th.dos.writeBoolean(false);
            th.dos.writeUTF(out);
            th.dos.flush(); 
        } catch(IOException e){
            
        }
        
    }
    
    public synchronized void delete(ThreadServer th){
        clients.remove(th);
        System.out.println("oh no un client se ne e' andata fu**k");
        try{
            th.dos.writeBoolean(false);
            th.dos.writeUTF(th.nome);
            th.dos.flush();
        }catch(IOException e){}
    }
    
    public synchronized void broadCast(ThreadServer th, String msg){
        for(ThreadServer thread : clients){
            if(thread != th){
                try{
                    thread.dos.writeBoolean(true);
                    thread.dos.writeUTF(th.nome + ">" + msg + "\n");
                    thread.dos.flush();
                }catch(IOException e){}
            }
        }
    }
    
    public synchronized void ptp(String dest, String msg){
        for(ThreadServer thread : clients){
            if(thread.nome.equals(dest)){
                try{
                    thread.dos.writeBoolean(true);
                    thread.dos.writeUTF(thread.nome + " [pvt]> " + msg + "\n");
                    thread.dos.flush();
                }catch(IOException e){}
                return;
            }
        }
    }
}
