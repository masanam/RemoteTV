package com.wartatv.peta.remotetv;

/**
 * Created by User Pc on 27/2/2017.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;



// Main class
public class CommandManager {

    private String serverIpAddress = null;
    private int    serverPort      = 0;

    private String responceString = null;     // here will be answer from
    // raspberry

    private int    readWaitSecTime = 0;       // wait for reply from raspberry
    private int    error           = 0;       // status of communication


    // getter, setter
    public String getServerIpAddress() {
        return serverIpAddress;
    }


    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }


    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }


    public int getError() {
        return error;
    }


    public int setReadWaitSecTime(int readWaitSecTime) {
        int prev = this.readWaitSecTime;

        this.readWaitSecTime = readWaitSecTime;
        return prev;
    }


    public String getResponceString() {
        return responceString;
    }



    // This method sends command to other side of the wire and
    // fetch response. If there was any error, then 'error' variable
    // is set.

    // When send -> recv will success, function returns 'true' if not,
    // 'false'.

    public boolean execute(String command) {

        InetAddress serverAddr              = null;
        Socket         serverSocket         = null;
        InputStream    serverInputStream    = null;
        OutputStream   serverOutputStream   = null;
        boolean        readOk               = false;

        InetSocketAddress inetAddr          = null;

        this.error           = 0;
        this.responceString  = null;

        try {

            serverAddr  = InetAddress.getByName(serverIpAddress);
            inetAddr    = new InetSocketAddress(serverAddr, serverPort);

        } catch (UnknownHostException e1) {

            this.error = -3;
            return readOk;

        }

        try {

            serverSocket = new Socket();
            serverSocket.connect(inetAddr, 100);

        } catch (IOException e) {

            this.error = -1;
            return readOk;

        }

        try {

            if (serverSocket.isConnected() == true) {

                serverInputStream  = serverSocket.getInputStream();
                serverOutputStream = serverSocket.getOutputStream();

                command+="\r\n";
                serverOutputStream.write(command.getBytes());

                // loop pools input stream for data
                for(int i = 0; i < readWaitSecTime * 50; i++) {

                    // weak, but working
                    int bytesToRead = serverInputStream.available();

                    if ( bytesToRead != 0) {

                        byte[] bytes = new byte[30];

                        serverInputStream.read(bytes,0, bytesToRead);

                        this.responceString = new String(bytes,0, bytesToRead );

                        readOk = true;
                        break;
                    }

                    Thread.sleep(10);
                }

                if (readOk == false)
                    this.error = -2;

                serverInputStream.close();
                serverOutputStream.close();

            } else {
                this.error = -1;
            }

            serverSocket.close();

        } catch (IOException e) {

            this.error = -4;
            return readOk;

        } catch (InterruptedException e) {

            this.error = -5;
            return readOk;

        }

        return readOk;
    }


}
