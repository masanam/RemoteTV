package com.wartatv.peta.remotetv;

/**
 * Created by User Pc on 27/2/2017.
 */
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.support.v4.view.GestureDetectorCompat;

import android.view.GestureDetector;
import android.view.MotionEvent;


import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

// Main class
public class Control_1 extends Activity implements OnCheckedChangeListener {

    private GestureDetectorCompat mDetector;

    // arrays for buttons and thier descriptions
    ArrayList<ToggleButton> buttons     = new ArrayList<ToggleButton>();
    ArrayList<TextView>     buttonsText = new ArrayList<TextView>();

    TextView       tvStatus;

    final int      WAIT_SHORT     = 1;
    final int      WAIT_LONG      = 4;

    int            screenNum      = 0;
    boolean        screenChange   = false;

    KeyguardManager   keyguardManager   = null;
    KeyguardLock      lock              = null; ;

    // create new object to communicate with raspberry
    CommandManager cm = new CommandManager();



    // This method refreshes button prefixes, descriptions and states
    // Once we've make a gesture (left, right), screen prefix was changed
    // and button prefixes, description and states needs to be updated.

    private void refreshButtons(int screenNum) {

        short i;

        // update new screen number
        this.setTitle("Control " + screenNum);

        // set all buttons to OFF
        for (ToggleButton b : buttons)
            b.setChecked(false);

        // clear all buttons descriptions
        for (TextView t : buttonsText)
            t.setText("");

        // check if is worth to fetch descriptions and staes.
        if( !cm.execute("GET PING CONN") ) {
            showError();
            return;
        }


        // send, recv. and set names for buttons with
        // new prefixes
        i = 1;
        for (TextView t : buttonsText)
            if( cm.execute("GET NAME B" + (4*screenNum + i++)) )
                t.setText(cm.getResponceString());


        // send, recv. and set states for buttons with
        // new prefixes
        i = 1;
        for (ToggleButton b : buttons)
            if( cm.execute("GET STATE B" + (4*screenNum + i++ )) )
                b.setChecked( cm.getResponceString().contains("ON"));

        // show last error
        showError();
    }


    @Override
    protected void onPause() {
        // when app goes into pause, reenable screen lock
        lock.reenableKeyguard();
        super.onPause();
    }


    @Override
    protected void onResume() {

        // refresh button desc. , names and states
        screenChange = true; // this will prevent to fire
        // OnCheckedChangeListener logic

        refreshButtons(screenNum);
        screenChange = false;

        // disable screen lock
        lock.disableKeyguard();

        super.onResume();
    }


    @Override
    protected void onDestroy() {
        // when app goes off, reenable screen lock
        lock.reenableKeyguard();
        super.onDestroy();
    }


    @Override
    public void finish() {
        // when app goes off, reenable screen lock
        lock.reenableKeyguard();
        super.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

        keyguardManager   = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
        lock              = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);

        lock.disableKeyguard();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        cm.setServerIpAddress("192.168.1.104");
        cm.setServerPort(8000);

        cm.setReadWaitSecTime(WAIT_SHORT);

        buttons.add( (ToggleButton) findViewById(R.id.tb_1) );
        buttons.add( (ToggleButton) findViewById(R.id.tb_2) );
        buttons.add( (ToggleButton) findViewById(R.id.tb_3) );
        buttons.add( (ToggleButton) findViewById(R.id.tb_4) );

        // add listener
        for (ToggleButton b : buttons)
            b.setOnCheckedChangeListener(this);

        tvStatus = (TextView)    findViewById(R.id.tv_Status);

        buttonsText.add( (TextView) findViewById(R.id.tv_B1) );
        buttonsText.add( (TextView) findViewById(R.id.tv_B2) );
        buttonsText.add( (TextView) findViewById(R.id.tv_B3) );
        buttonsText.add( (TextView) findViewById(R.id.tv_B4) );

        setStatus("empty");
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener implements OnGestureListener {

        @Override
        public boolean onFling(MotionEvent arg0, MotionEvent arg1,
                               float arg2, float arg3) {

            // slide from right to left on screen
            if ((arg0.getX() - arg1.getX()) > 0 ) {

                // slide on upper 1/5 of screen will jump by 5 screens
                if(arg0.getY() < 110)
                    screenNum+=5;
                else  // slide on 4/5 of screen will jump by 1 screen
                    screenNum++;

                screenChange = true; // prevent onChecked... logic
                refreshButtons(screenNum);
                screenChange = false;
            }

            // slide from left to right
            if ((arg1.getX() - arg0.getX()) > 0 ) {

                if(arg0.getY() < 110)
                    screenNum-=5;
                else
                    screenNum--;

                if (screenNum < 0) {
                    screenNum = 0;
                }
                screenChange = true;
                refreshButtons(screenNum);
                screenChange = false;
            }

            return false;
        }


        public  boolean onDoubleTap(MotionEvent e) {
            // on double tap, refresh current screen
            screenChange = true;
            refreshButtons(screenNum);
            screenChange = false;
            return false;
        }


        public  void onLongPress(MotionEvent e) {
            screenNum = 0;
            screenChange = true;
            refreshButtons(screenNum);
            screenChange = false;
        }


        @Override
        public void onGesture(GestureOverlayView arg0, MotionEvent arg1) {}


        @Override
        public void onGestureCancelled(GestureOverlayView arg0, MotionEvent arg1) {}


        @Override
        public void onGestureEnded(GestureOverlayView arg0, MotionEvent arg1) {}


        @Override
        public void onGestureStarted(GestureOverlayView arg0, MotionEvent arg1) {}
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        // do nothing when screen is changed
        if (screenChange == true ) {
            return;
        }

        String bName = "";

        // fetch prefix of changed button
        switch(buttonView.getId()) {
            case  R.id.tb_1: { bName = "B"+(4*screenNum + 1 ); } break;
            case  R.id.tb_2: { bName = "B"+(4*screenNum + 2 ); } break;
            case  R.id.tb_3: { bName = "B"+(4*screenNum + 3 ); } break;
            case  R.id.tb_4: { bName = "B"+(4*screenNum + 4 ); } break;
        }

        int prev = cm.setReadWaitSecTime(WAIT_SHORT);

        // send new state, recv. respnoce and set new button status
        if( cm.execute("SET STATE " + bName + " " + isChecked) ) {
            buttonView.setChecked( cm.getResponceString().contains("ON"));
        } else {
            showError();
            buttonView.setChecked(false);
        }

        cm.setReadWaitSecTime(prev);
    }


    public void setStatus(String status) {
        tvStatus.setText("Status: " +status);
    }


    public boolean showError() {
        if(cm.getError() == 0) {
            setStatus("ok");
            return false;
        }

        switch(cm.getError()) {
            case -1: setStatus("Server not reached"); break;
            case -2: setStatus("Read timeout"); break;
            case -3: setStatus("Unknown host"); break;
            case -4: setStatus("Interrupted");break;
        };

        return true;
    }


}
