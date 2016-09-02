package com.joy.app.activity.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.joy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daisw on 16/8/29.
 * <p/>
 * HandlerThread自带Looper使它可以通过消息机制重复使用线程，节省开支；
 */

public class HandlerThreadActivity extends AppCompatActivity {

    @BindView(R.id.tvPrompt) TextView mTvPrompt;
    @BindView(R.id.btnStart) Button   mBtnStart;
    @BindView(R.id.btnStop)  Button   mBtnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_act_handler_thread);
        ButterKnife.bind(this);

        HandlerThread handlerThread = new HandlerThread("handlerThread-1");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        Runnable work = () -> doWork();

        mBtnStart.setOnClickListener((v) -> handler.post(work));
        mBtnStop.setOnClickListener((v) -> {
            if (handlerThread.isAlive())
                handlerThread.interrupt();
        });
    }

    private void doWork() {

        while (true) {

            runOnUiThread(() -> mTvPrompt.append("* "));

            if (Thread.currentThread().isInterrupted())
                break;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
