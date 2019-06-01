package server;

import java.io.*;
import java.net.*;

public class Server {

    
    public static void main(String[] args) {
        ServerSocket server;
        Monitor monitor = new Monitor(50);
        
        try{
            server = new ServerSocket(1337);
            
            while(true){
                monitor.startNew(server.accept());
                System.out.println("accettato");
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } finally {
            monitor.stop();
        }  
    }
    
}
