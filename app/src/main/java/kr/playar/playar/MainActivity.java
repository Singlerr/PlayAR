package kr.playar.playar;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import kr.playar.playar.threads.ConnectThread;

public class MainActivity extends AppCompatActivity {
    private static BluetoothService service;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static VibrationService vibrationService;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(service == null)
            service = new BluetoothService(this);
        listView =findViewById(R.id.deviceList);
        list = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i <= getList().size()){
                    BluetoothDevice device =  service.getDeviceMap().get(getList().get(i));
                    ConnectThread connectThread = new ConnectThread(device,MainActivity.this);
                    connectThread.start();
                }
            }
        });
        this.adapter = arrayAdapter;
        Button button = findViewById(R.id.scanButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.startScan();
            }
        });
        vibrationService = new VibrationService(this);
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public void sendMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    public static VibrationService getVibrationService() {
        return vibrationService;
    }

    public static BluetoothService getService() {
        return service;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public  ListView getListView() {
        return listView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case BluetoothService.REQUEST_ENABLE_BT:
                service.setScanner(service.getAdapter().getBluetoothLeScanner());
        }
    }
}
