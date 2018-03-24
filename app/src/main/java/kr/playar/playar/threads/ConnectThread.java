package kr.playar.playar.threads;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

import kr.playar.playar.BluetoothService;
import kr.playar.playar.MainActivity;

/**
 * Created by Erroneous on 2018-03-24.
 */

public class ConnectThread extends Thread{
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private MainActivity activity;
    public ConnectThread(BluetoothDevice device, MainActivity activity){
        this.device = device;
        try{
            activity.sendMessage("연결 중");
            this.socket = device.createRfcommSocketToServiceRecord(BluetoothService.PROTOCOL);
        }catch (IOException ex){
            return;
        }
        this.activity =activity;
    }
    @Override
    public void run() {
        try{
            socket.connect();
            activity.sendMessage("연결되었습니다.");
            SocketThread socketThread = new SocketThread(socket,activity);
            socketThread.start();
        }catch (IOException ex){
            activity.sendMessage(ex.getMessage());
            try{
                socket.close();
            }catch (IOException e){
                activity.sendMessage(ex.getMessage());
            }
        }
    }
}
