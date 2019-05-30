package provaclient;

import java.net.*;
import java.io.*;

public class ProvaClient {
    
    
    public static void main(String[] args) {
        
        try {
            
            Socket culo = new Socket("localhost", 1337);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(culo.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(culo.getOutputStream()));
            
            dos.writeUTF("ciao");
            dos.flush();
            System.out.println(dis.readUTF());
            
            
            
        } catch(IOException e){
        //niente.
        }
        
        
        
    }
    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
}
