package chatclient1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class ChatFrame extends javax.swing.JFrame implements ActionListener{
    private String key = "";
    private String initVector = "";
    static final String HOST = "localhost";
    static final int PORT = 1337;
    static DataInputStream in;
    static DataOutputStream out;
    public DefaultListModel modelList = new DefaultListModel();
    public String nome = "";
    
    private boolean inserireNome = true;
    
    Socket s;
    
    
    public ChatFrame() {
        initComponents();
        jTextField1.addActionListener(this);
        jTextArea1.setText("Connessione ...");
    }
    
    public void initServer(){
        try{
            s = new Socket(HOST,PORT);
            new ClientThread(s, this).start();
            out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            
        }catch(Exception e){
            System.out.println("EXCEPTION -> Errore durante la connessione con il SERVER!");
        }
    }
    
    public void initList(String s){
        System.out.println(s);
        String[] elements;
        elements=s.split("\n");
        
        for(String i : elements){
            modelList.addElement(i);
        }
        jList1.setSelectedIndex(0);
    }
    
    public void updateList(boolean bAdd, String name){ // controllo un altra volta se devo aggiungere o togliere
        try{            
            if(bAdd){
                
                modelList.addElement(name);
            } else {
                for(int i=0; i<modelList.getSize(); i++){
                    if(modelList.elementAt(i).equals(name)){
                        modelList.removeElementAt(i);
                    }
                }
            }
        }catch(Exception e){}
    }
    
    public void insertMessage(String msg){
        String [] in = msg.split(" > ");
        String mitt = in[0].trim() + " > ";
        String msgIn = "";

        for(int i=1; i<in.length; i++){
            msgIn += in[i].trim();
        }
        msgIn = decrypt(msgIn);
        if(msgIn == null)
            jTextArea1.append("*** Messaggio Criptato ***" + "\n");
        else
            jTextArea1.append(mitt + msgIn + "\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>(modelList);
        txtPsw = new javax.swing.JTextField();
        txtIV = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout(2, 0));

        jTextField1.setText("testo");
        jPanel1.add(jTextField1, java.awt.BorderLayout.PAGE_END);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane2.setViewportView(jList1);

        txtPsw.setFont(new java.awt.Font("Noto Sans", 0, 16)); // NOI18N

        jButton1.setText("CHANGE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Password (16 ch)");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("InitVector");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPsw, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIV, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIV, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(txtPsw, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        key = txtPsw.getText();
        while(key.length() < 16)
            key += " ";
        initVector = txtIV.getText();
        while(initVector.length() < 16)
            initVector += " ";
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChatFrame c = new ChatFrame();
                c.setVisible(true);
                c.initServer();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtIV;
    private javax.swing.JTextField txtPsw;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((!(jTextField1.getText().equals(""))) && (e.getSource() instanceof JTextField) && ((JTextField) e.getSource() == jTextField1)) {   
            
            String outputCrypted;
            
            if(inserireNome){
                outputCrypted = jTextField1.getText();
                inserireNome = false;
                jTextArea1.setText("");
            } else {
                String out = jTextField1.getText();
                outputCrypted = encrypt(out);
                System.out.println(outputCrypted);
                jTextArea1.append("> " + out + "\n");
            }
            
            try{
                if(modelList.getSize() == 0 || jList1.getSelectedValue().toLowerCase().equals("tutti")){
                    out.writeBoolean(true);
                    out.writeUTF(outputCrypted);
                    out.flush();
                    jTextField1.setText("");
                } else {
                    out.writeBoolean(false);
                    out.writeUTF(jList1.getSelectedValue().toLowerCase() + "-" + outputCrypted);
                    out.flush();
                    jTextField1.setText("");
                }
            } catch(IOException exc){}
        }
    }
    
    
    public String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        }catch(BadPaddingException e){
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public void initTxtArea(){
        jTextArea1.setText("****** Connession Avvenuta con successo ******\n\nInserisci il tuo nome...");
    }
    
}

class ClientThread extends Thread{
    
    Socket conn;
    ChatFrame chatFrame;
   
    DataInputStream in;
    
    public ClientThread(Socket s, ChatFrame chatFrame){
        conn=s;        
        this.chatFrame = chatFrame;
        chatFrame.initTxtArea();
    }
    
    @Override
    public void run(){
        try{
            in=new DataInputStream(new BufferedInputStream(conn.getInputStream()));
                        
            in.readBoolean();
            chatFrame.initList(in.readUTF());
                        
            while(true){
                if(in.readBoolean())
                    chatFrame.insertMessage((in.readUTF())); // change with reference to the object passed
                else {
                    chatFrame.updateList(in.readBoolean(), in.readUTF().trim());
                }
            }
        }catch(IOException e){
            System.out.println("exception -> " + e.getMessage());
        }
    }
}