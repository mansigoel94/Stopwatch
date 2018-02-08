package com.example.mansigoel.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private var miliSec: Int = 0
    private var sec: Int = 0
    private var min: Int = 0;

    private var miliSecStr: String=""
    private var secStr: String = "";
    private var minStr: String = "";

    private var ctr: Int = 0;

    private var miliSecD: Int = 0;
    private var secD: Int = 0;
    private var minD: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalSeconds: Long = 86400;
        //no of seconds in one day
        val intervalSeconds: Long = 2;
        val timer = object : CountDownTimer(
                totalSeconds * 1000, intervalSeconds) {
            override fun onTick(p0: Long) {
                miliSec++;
                if (miliSec == 59) {
                    sec++;
                    miliSec = 0;
                }
                if (sec == 59) {
                    sec = 0;
                    min++;
                }
                tv_timer.text = formatTime(min, sec, miliSec);
            }

            override fun onFinish() {
            }
        }

        val lapDifferenceTimer =
                object : CountDownTimer(
                        totalSeconds * 1000, intervalSeconds) {
                    override fun onTick(p0: Long) {
                        miliSecD++;
                        if (miliSecD == 59) {
                            secD++;
                            miliSecD = 0;
                        }
                        if (secD == 59) {
                            secD = 0;
                            minD++;
                        }
                        tv_diff_timer.text = formatTime(minD, secD, miliSecD);
                    }

                    override fun onFinish() {
                    }
                }

        //creating blank laps
        val laps = ArrayList<Lap>()
        //assigning layout manager
        rv_time_records.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        //creating adapter
        var adapter = LapAdapter();
        //setting adapter to recycler view
        rv_time_records.adapter = adapter;

        btn_start_stop.setOnClickListener {
            if (btn_start_stop.text.toString().equals(getString(R.string.start))) {
                //if timer is starting it will contain start text on button
                btn_start_stop.text = getString(R.string.stop);
                btn_lap_reset.text = getString(R.string.lap);
                timer.start();
                lapDifferenceTimer.start();
            } else if (btn_start_stop.text.toString().equals(getString(R.string.stop))) {
                //if timer is starting it will contain stop text on button
                btn_start_stop.text = getString(R.string.start);
                btn_lap_reset.text = getString(R.string.reset);
                min = 0;
                sec = 0;
                miliSec = 0;
                minD=0;
                secD=0;
                miliSecD=0;
                timer.cancel();
                lapDifferenceTimer.cancel();
            }
        }

        btn_lap_reset.setOnClickListener {
            if (btn_lap_reset.text.toString().equals(getString(R.string.lap)) &&
                    btn_start_stop.text.toString().equals(getString(R.string.stop))) {
                /*if button has text "LAP" and "STOP" means timer is running
                 then we are calculating laps and updating recycler view accordingly*/
                laps.add(Lap(++ctr, tv_timer.text.toString()));
                lapDifferenceTimer.cancel();
                miliSecD=0;
                secD=0;
                minD=0;
                lapDifferenceTimer.start();
            } else if (btn_lap_reset.text.toString().equals(getString(R.string.reset)) &&
                    btn_start_stop.text.toString().equals(getString(R.string.start))) {
                /*if button has text "RESET" and "START" means timer has stopped
                    then we are clearing all laps and updating recycler view accordingly*/
                laps.clear();
                tv_timer.text = getString(R.string._00_00_00);
                tv_diff_timer.text = getString(R.string._00_00_00);
                btn_lap_reset.text = getString(R.string.lap);
                ctr = 0;
            }
            adapter.updateList(laps);
            adapter.notifyDataSetChanged();
        }
    }

    fun formatTime(hour: Int, sec: Int, miliSec: Int): String {
        //formatting hours
        if (hour == 0) {
            //if hour is 0 uptil now
            minStr = "00";
        } else if (hour / 10 == 0) {
            //if hour is one digit
            minStr = "0" + hour;
        } else {
            //of hour is two digit
            minStr = hour.toString();
        }

        //formatting secutes
        if (sec == 0) {
            //if sec is 0 until now
            secStr = "00";
        } else if (sec / 10 == 0) {
            //if sec is one digit
            secStr = "0" + sec;
        } else {
            //if sec is two digit
            secStr = sec.toString();
        }

        //formatting miliSeconds
        if (miliSec == 0) {
            //if miliSeconds is 0 until now
            miliSecStr = "00";
        } else if (miliSec / 10 == 0) {
            //if miliSeconds is one digit
            miliSecStr = "0" + miliSec;
        } else {
            //of miliSeconds is two digit
            miliSecStr = miliSec.toString();
        }
        return "$minStr:$secStr:$miliSecStr";
    }
}