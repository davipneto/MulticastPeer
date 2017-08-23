/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastpeer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author geova
 */
public class ReceiveThread extends Thread {
    
    private String address;
    
    public ReceiveThread (String address) {
        this.address = address;
    }

    @Override
    public void run() {
        try {
            MulticastSocket s = new MulticastSocket(6789);
            InetAddress group = InetAddress.getByName(address);
            s.joinGroup(group);
            String message;
            DatagramPacket messageIn = null;
            do{		// get messages from others in group
                byte[] buffer = new byte[1000];
                messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                message = new String(messageIn.getData());
                System.out.println("Received:\t" + message);
            }while(!message.trim().equals("sair"));
            s.close();
            this.interrupt();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
