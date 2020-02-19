package com.example.teampj_1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView tvReadCard, tvTextReadCard;
    ImageView ivRFID, ivBluetooth;
    int loginSuccess;
    String strLoginStatus;
    AlertDialog ad;

    Button btnSend;
    EditText edtSendMsg;
    TextView tvReceive;
    String buffer;
    ImageView ivBT;
    TextView tvSignup, tvLogin, tvLogout;
   // Button btnSend;
   // EditText edtSendMsg;
    TextView tvMsg;
    Button btnLogin,btnSignup,btnEtc;
    ImageView ivCard;
    TextView tvBluetoothEx;

    SQLiteDatabase sqlDB;
    BluetoothDB btDB;

    BluetoothAdapter bluetoothAdapter;

    static final int REQUEST_ENABLE_BT = 100;
    static final int REQUEST_LOGIN = 200;
    static final int REQUEST_SIGNUP = 300;

    int mPairedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    Thread mWorkerThread = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';
    byte readBuffer[];
    int readBufferPosition;
    View dialogView;
    boolean isSignup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserData data = DataManager.getInstance().getUserData(); //현재데이타
//        data.id = strId;
//        data.password = strPassword;
//        data.user_name = strUserName;
//        data.rfid = strRFID;

        ivBluetooth = (ImageView) findViewById(R.id.ivBluetooth);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        tvTextReadCard = (TextView) findViewById(R.id.tvTextReadCard);
        ivRFID = (ImageView) findViewById(R.id.ivRFID);
        ivCard=(ImageView)findViewById(R.id.ivCard);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvBluetoothEx=(TextView)findViewById(R.id.tvBluetoothEx);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent getIntent = getIntent();
        loginSuccess = getIntent.getIntExtra("piLOGIN", 0);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSuccess == 0) {  //로그인 전
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, REQUEST_LOGIN);
                } else {
                    showToast("로그아웃되었습니다");
                    loginSuccess = 0;
                    btnLogin.setText("Login");

                }
            }
        });

        ivCard.setOnClickListener(new View.OnClickListener() { //카드등록
            @Override
            public void onClick(View v) {
                createCard();
            }
        });

//        btnSend.setOnClickListener(new View.OnClickListener() { // 블루투스 연결 시 메시지 통신을 할 수 있지만 현재는 보류......
//            @Override
//            public void onClick(View v) {
//                String msg = edtSendMsg.getText().toString();
////                tvMsg.setText(msg);
//                sendData(msg);
//                edtSendMsg.setText("");
//            }
//        });

        btnSignup.setOnClickListener(new View.OnClickListener() { //회원가입
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class); //signup ACT
                startActivityForResult(intent, REQUEST_SIGNUP);

            }
        });

        ivBluetooth.setOnClickListener(new View.OnClickListener() { // 블루투스 연결 할 기기 선택
            @Override
            public void onClick(View v) {
                checkBluetooth();
            }
        });
    } //onCreate End


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //승인요청 수락시 메서드 실행
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT: //10
                if (resultCode == RESULT_OK) { //승인
                    selectDevice();
                } else if (resultCode == RESULT_CANCELED) { //취소
                    showToast("블루투스 연결을 취소하였습니다");
                }
                break;
            case REQUEST_LOGIN:
                if (resultCode == 100) {
                    showToast("로그인하였습니다.");
                    btnLogin.setText("Logout");
                    loginSuccess = 1;
                } else if (resultCode == 101) {
                    //showToast("로그인 실패");
                    btnLogin.setText("Login");
                    loginSuccess = 0;
                }
                break;
            case REQUEST_SIGNUP:
                if (resultCode == 100) {

                    /*TODO: 계정 성공 시 즉시 로그인
                     * 1. 로그인을 해준다.
                     * 2. 카드 등록 다이얼로그를 출력한다.*/
                    isSignup = true;
                    createCard();

                } else {
                    showToast("계정 생성 취소하였습니다.");
                }
                break;
        }
    } //onActivityResult END


    void checkRFID(String rfid) { //회원가입한 후의 카드등록, 로그인한 후의 카드등록

        if (isSignup) { //회원가입성공시
            buffer = "회원 가입 성공 \n" + buffer;

            sqlDB = btDB.getWritableDatabase();
            Cursor cursor = sqlDB.rawQuery("SELECT id FROM bluetoothUserTBL", null);
            cursor.moveToLast();
            UserData data = DataManager.getInstance().getUserData();

            String id = data.id;
            Log.i("test","등록할 rfid값: "+rfid);
            data.rfid = rfid;
            Log.i("test","등록된 rfid값: "+data.rfid);
            buffer = "user.id: "+data.id+" user.rfid: "+data.rfid+"\n" + buffer;

            sqlDB.execSQL("UPDATE bluetoothUserTBL SET rfid='" + rfid + "' WHERE id='" + id + "';");
            ad.dismiss();
            showToast("등록 되셧습니다. "+rfid);
            isSignup = false;

        } else { //로그인성공시
            UserData data = DataManager.getInstance().getUserData();

            buffer = "로그인 성공 \n" + buffer;
            buffer = "user.rfid:    "+data.rfid+"\n" + buffer;
            buffer = "receive.rfid: "+rfid+"\n" + buffer;
            Log.i("test","받은 rfid값: "+rfid);
            Log.i("test","등록된 rfid값: "+data.rfid);

            if(rfid.equals(data.rfid)){
                sendData("true");
                showToast("아두이노에게 열라고 명령함");
            } else {
                sendData("false");
                showToast("아두이노에게 열지마 ~~~~!!!");
            }
        }

        tvMsg.setText(buffer);
    }

    void createCard() { //신규카드 등록
        AlertDialog.Builder builder_createCard = new AlertDialog.Builder(this);
        builder_createCard.setTitle("");
        dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog_createcard, null);
        tvReadCard = (TextView) dialogView.findViewById(R.id.tvReadCard);
        //   mDevices = bluetoothAdapter.getBondedDevices();
        //   mPairedDeviceCount = mDevices.size();
        btDB = new BluetoothDB(this); //update

        builder_createCard.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                showToast("등록을 취소하였습니다");
            }
        });
        /*builder_createCard.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //showToast("카드를 등록하였습니다");
//                Intent getIntent = getIntent();
//                loginSuccess = getIntent.getIntExtra("piLOGINSUCCESS", 0);
                if (loginSuccess == 1) {
                    // tvLogin.setText("LogOut");
                    Log.i("test", "로그인성공할시 값이 1있어야햄" + loginSuccess);
                    //  finish();
                    // TODO: 2020-02-07 재연결 막아야함
                    btDB.BluetoothUpdateRFIDDB("업데이트 끝"); //update
                } else {
                    showToast("로그인을 먼저 해주세요.");
                }
                // TODO: 2020-02-07 로그아웃시 ??값을보냄
            }
        });
*/

        // TODO: 2020-01-28 RFID값을 DB에 저장해야함
        builder_createCard.setView(dialogView);
        builder_createCard.setCancelable(false);
        ad = builder_createCard.create();
        ad.show();
    }

    void checkBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) { //블루투스서비스를 지원하지않는다
            showToast("현재 기기는 블루투스서비스를 지원하지않습니다");
        } else {
            if (!bluetoothAdapter.isEnabled()) { //블루투스가 활성화되어있지않다
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); //블루투스 실행권한 요청 창
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT); //블루투스 승인요청 창
            } else {
                selectDevice();
            }
        }
    } //checkBluetooth() END

    void selectDevice() {
        mDevices = bluetoothAdapter.getBondedDevices(); //페어링된 장치목록
        mPairedDeviceCount = mDevices.size(); //페어링된 장치의 갯수

        if (mPairedDeviceCount == 0) { //연결된 디바이스가 없는경우
            showToast("연결할 블루투스 장치가 하나도 없습니다");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this); //Dialog 생성
            builder.setTitle("블루투스 장치 선택");
            List<String> listItems = new ArrayList<String>(); //동적배열
            for (BluetoothDevice device : mDevices) {
                listItems.add(device.getName()); //페어링된 장치이름
            }
            listItems.add("취소");
            final CharSequence items[] = listItems.toArray(new CharSequence[listItems.size()]); //

            builder.setItems(items, new DialogInterface.OnClickListener() { //아이템항목 선택하기위해
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == mPairedDeviceCount) { //카운트의 개수는 4개이고, 다이어로그의 인덱스4는 취소이다
                        showToast("취소를 선택하였습니다");
                    } else {
                        connectToSelectDevice(items[which].toString()); //선택한 장치와 연결시도 (페어링 작업)
                    }
                }
            });
            builder.setCancelable(false); //다이어로그창 띄어지고 프레임밖 부분을 터치해도 안사라지게
            builder.show();
        }
    } //selectDevice() END


    BluetoothDevice getDeviceBondedList(String name) { //페어링된 블루투스 장치 이름으로 찾기
        BluetoothDevice selectedDevice = null;
        for (BluetoothDevice device : mDevices) {
            if (name.equals(device.getName())) { //
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }

    void beginListenForData() { //데이터를 수신 준비 및 처리
        final Handler handler = new Handler(); //
        readBuffer = new byte[1024]; //
        readBufferPosition = 0; //
        mWorkerThread = new Thread(new Runnable() { //
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) { //정상적이게 통신중일때 //Interrupted:가로막힌, 중단된
                    try {
                        int bytesAvailable = mInputStream.available(); //
                        if (bytesAvailable > 0) { //받을자료가 있으면
                            byte paketBytes[] = new byte[bytesAvailable];
                            mInputStream.read(paketBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = paketBytes[i];
                                if (b == mCharDelimiter) { //데이타를 다 받앗다는 뜻으로 (?
                                    byte encodeBytes[] = new byte[readBufferPosition]; //
                                    System.arraycopy(readBuffer, 0, encodeBytes, 0, encodeBytes.length);
                                    final String data = new String(encodeBytes, "UTF-8"); //US-ASCII : 아스키코드 //UTF-8 :한글안깨짐
                                    readBufferPosition = 0; //
                                    handler.post(new Runnable() { //아두이노에 작성한 전송부분을 수신하여 작업할 곳
                                        @Override
                                        public void run() { //수신된 문자열 데이터에 대한 처리작업
//                                            tvReadCard.setText(data); //test //대충 아두이노가 핸드폰의 RFID 를 읽어온 값
//                                            Log.i("test", "tvReadCard.setText(data) 성공");
//                                            tvTextReadCard.setText("");
//                                            Log.i("test", "tvTextReadCard.setText()성공");
//                                            ivRFID.setVisibility(View.INVISIBLE);
//                                            Log.i("test", "ivRFID.setVisibility(View.INVISBLE) 성공");
//                                            // read=data;
//                                            read = "1234-123-44312";
                                            Log.i("test", "데이터 수신:"+data);
                                            buffer = data+"\n"+ buffer;
                                            tvMsg.setText(buffer);
                                            checkRFID(data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException e) {
                        showToast("데이터 수신 중 오류가 발생하였습니다");
                    }
                }
            }
        });
        mWorkerThread.start();
    }

    void sendData(String msg) { //데이터를 송신
        Log.i("test", msg);
        msg += mStrDelimiter; //mStrDelimiter 문자 끝을 알리는...

        try {
            mOutputStream.write(msg.getBytes()); //문자 전송
            buffer = msg +buffer;
            buffer = "문자전송: " + buffer;

        } catch (Exception e) {
            buffer = "문자 전송 실패\n"+ buffer;
            showToast("데이터 전송 중 오류가 발생하였습니다");
        }
        tvMsg.setText(buffer);
    }

    void connectToSelectDevice(String selectedDeviceName) { //선택된 장치 연결(페어링) 메서드
        mRemoteDevice = getDeviceBondedList(selectedDeviceName); //
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//블루투스 소켓 통신 *************

        try {
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid); //
            mSocket.connect(); //연결시도
            mOutputStream = mSocket.getOutputStream(); //송신 //ex led 조종
            mInputStream = mSocket.getInputStream(); //수신 //ex 온도습도 값
            beginListenForData(); //
            ivBluetooth.setImageResource(R.drawable.bluetooth_icon);
            tvBluetoothEx.setText("");
        } catch (Exception e) {
           // showToast("블루투스 연결 중 오류가 발생하였습니다");
            tvBluetoothEx.setText("블루투스 연결 중 요류가 발생하였습니다.\n다시한번 연결을 시도해주세요");
            ivBluetooth.setImageResource(R.drawable.bluetooth_grayicon);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mWorkerThread.interrupt(); //데이터 수신 쓰레드를 종료 thread
            mInputStream.close();
            mOutputStream.close();
            mSocket.connect();

        } catch (Exception e) {
        }
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}

