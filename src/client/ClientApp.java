package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import shared.Constants;

public class ClientApp {
    public static void main(String[] args) {

        Socket socket = null;

        try {

            InetAddress myIp = InetAddress.getLocalHost();
            socket = new Socket(myIp, Constants.SERVER_PORT);

            Scanner scanner = new Scanner(System.in);
            DataOutputStream toServerStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream fromServerStream = new DataInputStream(socket.getInputStream());
            
            MessageHandler messageHandler = new MessageHandler(fromServerStream, socket);
            MessageSender messageSender = new MessageSender(toServerStream);

            System.out.print("¿Cómo te llamas?: ");

            messageHandler.start();
            messageSender.start();

            
        } catch (IOException e) {
            System.out.println("==================");
            System.out.println("ERROR DE CONEXION!");
            System.out.println("LEVANTA EL SERVER!!");
            System.out.println("==================");
        } 
    }
}