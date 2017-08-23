/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicastpeer;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author davi
 */
public class MulticastPeer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// args give message contents and destination multicast group (e.g. "228.5.6.7")
        MulticastSocket s = null;
        ReceiveThread t = new ReceiveThread(args[1]);
        t.start();
        try {
            InetAddress group = InetAddress.getByName(args[1]);
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            String message;
            do {
                Scanner sc = new Scanner(System.in);
                message = sc.nextLine();
                DatagramPacket messageOut = new DatagramPacket(message.getBytes(), message.getBytes().length, group, 6789);
                s.send(messageOut);
            } while (!message.trim().equals("sair"));
            s.leaveGroup(group);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

}
