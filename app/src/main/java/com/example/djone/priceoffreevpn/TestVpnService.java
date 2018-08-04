package com.example.djone.priceoffreevpn;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.VpnService;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TestVpnService extends VpnService implements Handler.Callback, Runnable {
    private static final String TAG = "PriceOfFreeVPN";

    private PendingIntent configureIntentVpn;

    private Handler vpnHandler;
    private Thread vpnThread;

    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (vpnHandler == null){
            vpnHandler = new Handler(this);
        }//if for handler to show message

        if (vpnThread != null){
            vpnThread.interrupt();
        }//if a thread is already running interrupt it

        //starts a new session by creating a new thread
        vpnThread = new Thread(this, "VpnThread");
        vpnThread.start();
        return START_STICKY;
    }//onStart

    @Override
    public void onDestroy(){
        if (vpnThread != null){
            vpnThread.interrupt();
        }//if vpn still running when called interrupt it
    }//onDestroy

    @Override
    public boolean handleMessage(Message message){
        if (message != null){
            Toast.makeText(this, (String)message.obj, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public synchronized void run(){
        try {
            Log.i(TAG, "Starting Vpn Service!");
            String serverAddress = "127.0.0.1";
            int serverPort = 8087;
            InetSocketAddress server = new InetSocketAddress(serverAddress, serverPort);
            run(server);
        }catch (Exception e1){
            Log.i(TAG, "Got error " + e1.toString());
            try {
                vpnInterface.close();
            }catch (Exception e2){
                //ignore this error
            }
            Message messageObj = vpnHandler.obtainMessage();
            messageObj.obj = "Disconnected from VPN";
            vpnHandler.sendMessage(messageObj);
        }finally {

        }
    }//run

    DatagramChannel vpnTunnel = null;

    private boolean run(InetSocketAddress server) throws Exception {
        boolean vpnConnected;

        vpnTunnel = DatagramChannel.open();//open Datagram Channel to use as the VPN tunnel

        if (!protect(vpnTunnel.socket())){
            throw new IllegalStateException("Tunnel cannot be protected");
        }//attempts to protect the tunnel to avoid looping

        vpnTunnel.connect(server);//connects to the server

        vpnTunnel.configureBlocking(false);//stops the tunnel from blocking

        handshake();//configure the virtual networks interface

        //now connected so update flag and message
        vpnConnected = true;
        Message messageObj = vpnHandler.obtainMessage();
        messageObj.obj = "Connected to VPN";
        vpnHandler.sendMessage(messageObj);

        new Thread(){
            public void run(){
                //packets getting sent will be queued in an input stream
                FileInputStream inputStream = new FileInputStream(vpnInterface.getFileDescriptor());
                //Create ByteBuffer and allocate to a single packet
                ByteBuffer packet = ByteBuffer.allocate(32767);
                int length;
                try {
                    while (true){
                        while ((length = inputStream.read(packet.array())) >0 ){
                            //Outgoing packet gets wrote to the tunnel
                            packet.limit(length);
                            debugPacket(packet);//obtain information on the packet
                            vpnTunnel.write(packet);//places packet in the tunnel
                            packet.clear();//clear to allow next packet to be read
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            public void run(){
                DatagramChannel tunnel = vpnTunnel;
                ByteBuffer packet = ByteBuffer.allocate(8096);
                //Packets recieved need to be written to an output stream
                FileOutputStream outputStream = new FileOutputStream(vpnInterface.getFileDescriptor());

                while (true){
                    try {
                        //reading the packet from the tunnel
                        int length;
                        while ((length = tunnel.read(packet)) >0){
                            //write the packet to the output
                            outputStream.write(packet.array(), 0, length);

                            packet.clear();//empty to allow for new packet
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }//catch
                }//while
            }//run
        }.start();

        return vpnConnected;
    }//run

    private void handshake(){
        if (vpnInterface == null){
            Builder builder = new Builder();

            builder.setMtu(1500);
            builder.addAddress("10.0.0.2", 32);
            builder.addRoute("0.0.0.0", 0);//set to 0 to check all packets

            try {
                vpnInterface.close();
            }catch (Exception e){
                //just here to ensure vpnInterface is currently closed so exception is ignored
            }

            vpnInterface = builder.setSession("Price of Free VPN")
                    .setConfigureIntent(configureIntentVpn)
                    .establish();
        }
    }//handShake

    private void debugPacket(ByteBuffer packet){
        int buffer = packet.get();
        int version;
        int headerLength;
        version = buffer >>4;
        headerLength = buffer & 0x0F;
        headerLength *=4;

        Log.i(TAG, "IP Version: " + version);
        Log.i(TAG, "Header Length: " + headerLength);

        String status = "";
        status += "Header Length: " + headerLength;

        buffer = packet.get();
        buffer = packet.getChar();

        Log.i(TAG, "Total Length: " + buffer);

        buffer = packet.getChar(); //identification
        buffer = packet.getChar(); //flags
        buffer = packet.get(); //time to live
        buffer = packet.get();//protocol

        Log.i(TAG, "Protocol: " + buffer);

        status +=" Protocl: " + buffer;

        buffer = packet.getChar();//Header checksum

        String sourceIP = "";
        buffer = packet.get();//First section of IP
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();//Second section of IP
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();//Third section of IP
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();//Fourth section of IP
        sourceIP += buffer;

        Log.i(TAG, "Source IP: " + sourceIP);

        status += " Source IP: " + sourceIP;

        String destinationIP = "";
        buffer = packet.get();//Destination IP first Section
        destinationIP += buffer;
        destinationIP += ".";

        buffer = packet.get();//Destination IP second Section
        destinationIP += buffer;
        destinationIP += ".";

        buffer = packet.get();//Destination IP third Section
        destinationIP += buffer;
        destinationIP += ".";

        buffer = packet.get();//Destination IP fourth Section
        destinationIP += buffer;

        Log.i(TAG, "Destination IP: " + destinationIP);

        status += " Destination IP: " + destinationIP;

        Message messageObj = vpnHandler.obtainMessage();
        messageObj.obj= status;
        vpnHandler.sendMessage(messageObj);
    }
}
