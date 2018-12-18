package com.wartatv.peta.remotetv;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Socket socket =null;
    private Thread mThread = null;
    private byte [] recive_buffer = new byte[1024];
    private OutputStream outputStream=null;
    private InputStream inputStream=null;
    private String str_recive;
    private boolean is_connect = false;
    private TextView TextIP = null;
    private EditText socket_recive =null;
    private Button btn_close=null;

    private Button btn_power = null;
    private Button btn_mute = null;
    private Button btn_list = null;

    private Button btn_1 = null;
    private Button btn_2 = null;
    private Button btn_3 = null;
    private Button btn_4 = null;
    private Button btn_5 = null;
    private Button btn_6 = null;
    private Button btn_7 = null;
    private Button btn_8 = null;
    private Button btn_9 = null;
    private Button btn_0 = null;

    private Button btn_last = null;
    private Button btn_next = null;
    private Button btn_up = null;
    private Button btn_down = null;
    private Button btn_left = null;
    private Button btn_right = null;

    int temp_connect;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_layout);
        TextIP = (TextView)findViewById(R.id.TextIP);
        //socket_recive =(EditText)findViewById(R.id.editText3);
        btn_close=(Button)findViewById(R.id.button24);
        btn_close.setEnabled(false);

        //Intent
        Intent intent = getIntent();
        String ip_serverStr = intent.getStringExtra("ip");
        String port_serverStr = intent.getStringExtra("port");
        //IPPortֵ
        TextIP.setText("IP Address :"+ip_serverStr);
        //Socket
        ip_serverStr.trim();
        port_serverStr.trim();
        if(ip_serverStr.length()==0||port_serverStr.length()==0)
            Log.d("MyApp","ip_server edit is empty or port_server is empty!");
        else
        {
            int port= Integer.parseInt(port_serverStr);
            Log.d("MyApp","ip :"+ip_serverStr+" port :"+port);
            try {
                //Socket IP
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip_serverStr, port), 1000);

                if(socket.isConnected()==true){
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    btn_close.setEnabled(true);
                    is_connect = true;
                    Log.d("MyApp","connect!");
                }

            } catch (Exception e) {
                if(e.getClass()==SocketTimeoutException.class){
                    Log.d("MyApp","connect Exception!");
                    Toast toast = Toast.makeText(MainActivity.this,"Connect hosts abnormal.Please check whether open the network or host address errors!",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                e.printStackTrace();
            }
        }
        btn_close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                try{
                    is_connect = false;
                    socket.shutdownInput();
                    socket.shutdownOutput();
                    socket.close();

                    btn_close.setEnabled(false);
                    //socket_recive.setText("");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btn_power=(Button)findViewById(R.id.btn_power);
        btn_power.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             char power = 'a';
                                             try {
                                                 outputStream.write(power);
                                                 outputStream.flush();
                                                 //socket_recive.setText("");
                                             }
                                             catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
        );

        btn_mute=(Button)findViewById(R.id.btn_mute);
        btn_mute.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char mute = 'o';
                                            try {
                                                outputStream.write(mute);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_list=(Button)findViewById(R.id.btn_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char menu = 'q';
                                            try {
                                                outputStream.write(menu);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_1=(Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char one = '1';
                                         try {
                                             outputStream.write(one);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_2=(Button)findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char two = '2';
                                         try {
                                             outputStream.write(two);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_3=(Button)findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char three = '3';
                                         try {
                                             outputStream.write(three);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_4=(Button)findViewById(R.id.btn_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char four = '4';
                                         try {
                                             outputStream.write(four);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_5=(Button)findViewById(R.id.btn_5);
        btn_5.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char five = '5';
                                         try {
                                             outputStream.write(five);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_6=(Button)findViewById(R.id.btn_6);
        btn_6.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char six = '6';
                                         try {
                                             outputStream.write(six);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_7=(Button)findViewById(R.id.btn_7);
        btn_7.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char seven = '7';
                                         try {
                                             outputStream.write(seven);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_8=(Button)findViewById(R.id.btn_8);
        btn_8.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char eight = '8';
                                         try {
                                             outputStream.write(eight);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_9=(Button)findViewById(R.id.btn_9);
        btn_9.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char nine = '9';
                                         try {
                                             outputStream.write(nine);
                                             outputStream.flush();
                                             //socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_0=(Button)findViewById(R.id.btn_0);
        btn_0.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         char zero = '0';
                                         try {
                                             outputStream.write(zero);
                                             outputStream.flush();
                                             // socket_recive.setText("");
                                         }
                                         catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        btn_last=(Button)findViewById(R.id.btn_last);
        btn_last.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char last = 'd';
                                            try {
                                                outputStream.write(last);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char next = 'e';
                                            try {
                                                outputStream.write(next);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_up=(Button)findViewById(R.id.btn_up);
        btn_up.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          char up = 'h';
                                          try {
                                              outputStream.write(up);
                                              outputStream.flush();
                                              //socket_recive.setText("");
                                          }
                                          catch (Exception e) {
                                              e.printStackTrace();
                                          }
                                      }
                                  }
        );
        btn_down=(Button)findViewById(R.id.btn_down);
        btn_down.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char down = 'i';
                                            try {
                                                outputStream.write(down);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_left=(Button)findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            char left = 'j';
                                            try {
                                                outputStream.write(left);
                                                outputStream.flush();
                                                //socket_recive.setText("");
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
        btn_right=(Button)findViewById(R.id.btn_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
                                         public void onClick(View v) {
                                             char right = 'k';
                                             try {
                                                 outputStream.write(right);
                                                 outputStream.flush();
                                                 //socket_recive.setText("");
                                             }
                                             catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }
        );

        mThread = new Thread(mRunnable);
        mThread.start();
    }

    private Runnable	mRunnable	= new Runnable()
    {
        public void run()
        {

            while (true)
            {
                try
                {
                    if(is_connect){
                        System.out.println("is_connect is :"+is_connect);
                        temp_connect = inputStream.read(recive_buffer, 0, recive_buffer.length);
                        if(temp_connect!=-1){
                            str_recive=new String(recive_buffer,0,temp_connect);
                            str_recive.trim();
                            Log.d("MyApp","recive datas!"+temp_connect+str_recive);
                        }
                        else {
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            inputStream.close();
                            outputStream.close();
                            socket.close();
                            is_connect=false;
                        }
                        mHandler.sendMessage(mHandler.obtainMessage());
                    }
                    Thread.sleep(100);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    };

    Handler		mHandler	= new Handler()
    {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            try
            {
                Log.d("MyApp","mHandler!");
                if(temp_connect==-1){
                    btn_close.setEnabled(false);
                    //socket_recive.setText("");
                }
                else {
                    //socket_recive.setText(str_recive);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };

}



