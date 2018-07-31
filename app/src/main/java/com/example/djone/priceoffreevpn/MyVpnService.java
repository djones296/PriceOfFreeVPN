package com.example.djone.priceoffreevpn;

import android.content.Intent;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    try {
                        vpnInterface.close();
                    }
                    catch (Exception e){
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

        FileInputStream inputStream = new FileInputStream(vpnInterface.getFileDescriptor());
        FileOutputStream outputStream = new FileOutputStream(vpnInterface.getFileDescriptor());

        DatagramChannel tunnel = DatagramChannel.open();
        tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
        protect(tunnel.socket());

        ByteBuffer packet = ByteBuffer.allocate(32767);

        while (true){
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
            int length = inputStream.read(packet.array());
            if (length > 0){
                while (packet.hasRemaining()){
                    //Log.i(VPNTAG, "" + packet.get());
                    byte[] bytes = new byte[packet.remaining()];
                    packet.get(bytes);
                    Log.i(VPNTAG, "Intercepted: " + new String(bytes, "ISO-8859-1"));
                }
                packet.clear();
            }
            Thread.sleep(50);
        }
    }
}

    private void configureVpnService(){
        if (vpnInterface != null){
            Log.i(VPNTAG, "Using previous interface");
            return;
        }//if function to use previous interface if it has the same parameters
        Builder vpnBuilder = new Builder();
        vpnBuilder.setMtu(1500);
        vpnBuilder.addAddress("10.0.2.0",24);
        vpnBuilder.addRoute("0.0.0.0", 0);
        vpnBuilder.addDnsServer("8.8.8.8");
        try {
            vpnInterface.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        vpnInterface = vpnBuilder.setSession(VPNTAG).establish();
    }

    public String getLocalIpAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements();){
                NetworkInterface netIntrf = en.nextElement();
                for (Enumeration<InetAddress> enIpAdd = netIntrf.getInetAddresses();
                     enIpAdd.hasMoreElements();){
                    InetAddress inetAddress = enIpAdd.nextElement();
                    Log.i(VPNTAG, "INET ADDRESS");
                    Log.i(VPNTAG, "address: " + inetAddress.getHostAddress());
                    Log.i(VPNTAG, "hostname: " + inetAddress.getHostName());
                    Log.i(VPNTAG, "address.toString(): " + inetAddress.getHostAddress().toString());
                    if (!inetAddress.isLoopbackAddress()){
                        //IPAddresses.setText(inetAddress.getHostAddress().toString());
                        Log.i(VPNTAG, "IS NOT LOOPBACK ADDRESS: " + inetAddress.getHostAddress().toString());
                        //return inetAddress.getHostAddress().toString();
                    }else {
                        Log.i(VPNTAG, "It is a loopback address");
                    }
                }
            }
        }catch (SocketException socEx){
            String LOG_TAG = null;
            Log.e(LOG_TAG, socEx.toString());
        }
        return null;
    }
}
