package kr.playar.playar;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Erroneous on 2018-03-24.
 */

public class BluetoothService {
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final UUID PROTOCOL = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int REQUEST_ENABLE_BT = 2;
    private BluetoothLeScanner scanner;
    private MainActivity activity;
    private BluetoothAdapter adapter;
    private HashMap<String,BluetoothDevice> deviceMap;
    public BluetoothService(MainActivity activity){
        this.activity = activity;
        this.deviceMap = new HashMap<>();
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        if(this.adapter == null) {
            sendMessage("이 기기는 블루투스를 지원하지 않습니다.");
            return;
        }
        if(! this.adapter.isEnabled()){
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(i, REQUEST_ENABLE_BT);
        }
    }

    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public void setScanner(BluetoothLeScanner scanner) {
        this.scanner = scanner;
    }

    public void startScan(){
        for(BluetoothDevice device : adapter.getBondedDevices()){
            activity.getList().add(device.getName());
            activity.getAdapter().notifyDataSetChanged();
            deviceMap.put(device.getName(),device);
        }
    }
    private void sendMessage(String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
    }

    public HashMap<String, BluetoothDevice> getDeviceMap() {
        return deviceMap;
    }
}
