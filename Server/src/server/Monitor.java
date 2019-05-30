package server;

// Monitor

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
}
