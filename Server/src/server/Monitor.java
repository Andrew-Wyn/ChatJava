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
    
    public ThreadServer addInPool(ThreadServer ts){
        if(clients.add(ts)) return ts;
        else return null;
    }
    
    public void startNew(Socket client){
        executor.submit((new ThreadServer(client, this))); // submit(run) accetta oggetti di interfaccia callable
    }
    
    public void stop(){
        executor.shutdown();
    }
    
    public void sendAllPartecipants(ThreadServer th){
        String out = "tutti\n";
        for(ThreadServer thread : clients){
            if(thread != th){
                out += thread.nome + "\n";
            }
        }
        try{
            th.dos.writeBoolean(false);
            th.dos.writeUTF(out);
            th.dos.flush(); 
        } catch(IOException e){
            
        }
        
    }
    
    public synchronized void aggiungiInLista(ThreadServer th){
        for(ThreadServer thread : clients){
            if(thread != th){
                try{
                    System.out.println("eccomi");
                    thread.dos.writeBoolean(false);
                    thread.dos.flush();
                    thread.dos.writeBoolean(true);
                    thread.dos.flush();
                    thread.dos.writeUTF(th.nome);
                    thread.dos.flush();
                }catch(IOException e){}
            }
        }
    }
    
    public synchronized void delete(ThreadServer th){
        clients.remove(th);
        System.out.println("oh no un client se ne e' andata fu**k");
        for(ThreadServer thread : clients){
                try{
                    thread.dos.writeBoolean(false);
                    thread.dos.writeBoolean(false);
                    thread.dos.writeUTF(th.nome);
                    thread.dos.flush();
                }catch(IOException e){}
        }
    }
    
    public synchronized void broadCast(ThreadServer th, String msg){
        for(ThreadServer thread : clients){
            if(thread != th){
                
                try{
                    thread.dos.writeBoolean(true);
                    thread.dos.writeUTF(th.nome + ">" + msg + "\n");
                    thread.dos.flush();
                }catch(IOException e){
                System.out.println(e.getMessage());
                }
            }
        }
    }
    
    public synchronized void ptp(String dest, String msg, String nome){
        for(ThreadServer thread : clients){
            if(thread.nome.equals(dest)){
                try{
                    thread.dos.writeBoolean(true);
                    thread.dos.writeUTF(nome + " [pvt]> " + msg + "\n");
                    thread.dos.flush();
                }catch(IOException e){}
                return;
            }
        }
    }
}
