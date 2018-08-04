package com.example.djone.priceoffreevpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class MyVpnService extends android.net.VpnService {

    private static final String VPNTAG = "PriceofFreeVPNService";


    private Thread vpnThread;

    private ParcelFileDescriptor vpnInterface;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("----------------------");
        System.out.println("STARTING MyVpnService!");
        System.out.println("----------------------");
        vpnThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Log.i(VPNTAG, "Running VPN Service");
                try {
                    runVpnConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        vpnInterface.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vpnInterface = null;

//                    vpnHandler.sendEmptyMessage(R.string.app_name);
                    Log.i(VPNTAG, "Exiting");
                }
            }

        }, "MyVpnRunnable");

        //start the service
        vpnThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (vpnThread != null) {
            vpnThread.interrupt();
        }
        super.onDestroy();
    }


    public void handleMessage(Message message) {
        if (message != null) {
            Toast.makeText(this, message.what, Toast.LENGTH_SHORT).show();
        }
    }

    private void runVpnConnection() throws Exception {
        configureVpnService();

        FileInputStream inputStream = new FileInputStream(vpnInterface.getFileDescriptor());//packets to be monitored get queued in this inputStream
        DatagramChannel tunnel = DatagramChannel.open();
        protect(tunnel.socket());
        tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
        tunnel.configureBlocking(false);
        ByteBuffer packet = ByteBuffer.allocate(32767);//buffer allocated to a single packet
        int length;
        try {
            while (true) {
                while ((length = inputStream.read(packet.array())) > 0) {
                    packet.limit(length);
                    debugPacket(packet);
                    tunnel.write(packet);
                    packet.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        DatagramChannel outtunnel = tunnel;
//        ByteBuffer outPacket = ByteBuffer.allocate(8096);
//        //FileOutputStream out = new FileOutputStream(outtunnel());
//        while (true)
//        {
//            try
//            {
//                int lengthOut;
//                while ((lengthOut = tunnel.read(outPacket)) > 0)
//                {
//                    out.write(outPacket.array(), 0, lengthOut);
//                    outPacket.clear();
//                }
//            }catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
    }


    private void debugPacket(ByteBuffer packet) {
        /*
        for(int i = 0; i < length; ++i)
        {
              byte buffer = packet.get();

            Log.d(TAG, "byte:"+buffer);
        }*/
        int buffer = packet.get();
        int version;
        int headerlength;
        version = buffer >> 4;
        headerlength = buffer & 0x0F;
        headerlength *= 4;
        Log.d(VPNTAG, "IP Version:" + version);
        Log.d(VPNTAG, "Header Length:" + headerlength);

        String status = "";
        status += "Header Length:" + headerlength;

        buffer = packet.get();      //DSCP + EN
        buffer = packet.getChar();  //Total Length

        Log.d(VPNTAG, "Total Length:" + buffer);

        buffer = packet.getChar();  //Identification
        buffer = packet.getChar();  //Flags + Fragment Offset
        buffer = packet.get();      //Time to Live
        buffer = packet.get();      //Protocol

        Log.d(VPNTAG, "Protocol:" + buffer);

        status += "  Protocol:" + buffer;

        buffer = packet.getChar();  //Header checksum

        String sourceIP = "";
        buffer = packet.get();  //Source IP 1st Octet
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();  //Source IP 2nd Octet
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();  //Source IP 3rd Octet
        sourceIP += buffer;
        sourceIP += ".";

        buffer = packet.get();  //Source IP 4th Octet
        sourceIP += buffer;

        Log.d(VPNTAG, "Source IP:" + sourceIP);

        status += "   Source IP:" + sourceIP;

        String destIP = "";
        buffer = packet.get();  //Destination IP 1st Octet
        destIP += buffer;
        destIP += ".";

        buffer = packet.get();  //Destination IP 2nd Octet
        destIP += buffer;
        destIP += ".";

        buffer = packet.get();  //Destination IP 3rd Octet
        destIP += buffer;
        destIP += ".";

        buffer = packet.get();  //Destination IP 4th Octet
        destIP += buffer;

        Log.d(VPNTAG, "Destination IP:" + destIP);

        status += "   Destination IP:" + destIP;
        /*
        msgObj = mHandler.obtainMessage();
        msgObj.obj = status;
        mHandler.sendMessage(msgObj);
        */

        //Log.d(TAG, "version:"+packet.getInt());
        //Log.d(TAG, "version:"+packet.getInt());
        //Log.d(TAG, "version:"+packet.getInt());

    }

    private void configureVpnService() {
        if (vpnInterface != null) {
            Log.i(VPNTAG, "Using previous interface");
            return;
        }//if function to use previous interface if it has the same parameters
        Builder vpnBuilder = new Builder();
        vpnBuilder.setMtu(1500);
        vpnBuilder.addAddress("10.0.2.0", 24);
        vpnBuilder.addRoute("0.0.0.0", 0);
        vpnBuilder.addDnsServer("8.8.8.8");
        try {
            vpnInterface.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        vpnInterface = vpnBuilder.setSession(VPNTAG).establish();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface netIntrf = en.nextElement();
                for (Enumeration<InetAddress> enIpAdd = netIntrf.getInetAddresses();
                     enIpAdd.hasMoreElements(); ) {
                    InetAddress inetAddress = enIpAdd.nextElement();
                    Log.i(VPNTAG, "INET ADDRESS");
                    Log.i(VPNTAG, "address: " + inetAddress.getHostAddress());
                    Log.i(VPNTAG, "hostname: " + inetAddress.getHostName());
                    Log.i(VPNTAG, "address.toString(): " + inetAddress.getHostAddress().toString());
                    if (!inetAddress.isLoopbackAddress()) {
                        //IPAddresses.setText(inetAddress.getHostAddress().toString());
                        Log.i(VPNTAG, "IS NOT LOOPBACK ADDRESS: " + inetAddress.getHostAddress().toString());
                        //return inetAddress.getHostAddress().toString();
                    } else {
                        Log.i(VPNTAG, "It is a loopback address");
                    }
                }
            }
        } catch (SocketException socEx) {
            String LOG_TAG = null;
            Log.e(LOG_TAG, socEx.toString());
        }
        return null;
    }
}

