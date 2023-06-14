package com.levietduc.multithread_exam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.levietduc.multithread_exam1.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Random random = new Random();

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,LinearLayout.LayoutParams.WRAP_CONTENT);

    // Main Thread #1
    //Main Thread / UI Thread
    /*Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            //Update UI
            int percent = message.arg1;
            int randNumb = (int) message.obj;

            binding.txtPercent.setText(percent + " %");
            if(percent == 100) binding.txtPercent.setText("DONE!");
            binding.pbPercent.setProgress(percent);

            ImageView imv = new ImageView(MainActivity.this);
            imv.setLayoutParams(params);
            if(randNumb %2 == 0){
                imv.setImageResource(R.drawable.ic_baseline_brightness_3_24);
            }else {
                imv.setImageResource(R.drawable.ic_baseline_cloud_24);
            }
            //imv.setImageResource(R.drawable.ic_baseline_cloud_24);

            binding.container.addView(imv);

            return true;
        }
    });*/

    //Using post
    int percent, randNumb;
    Handler handler = new Handler();
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            //Update UI
            binding.txtPercent.setText(percent + " %");
            if(percent == 100) binding.txtPercent.setText("DONE!");
            binding.pbPercent.setProgress(percent);

            ImageView imv = new ImageView(MainActivity.this);
            imv.setLayoutParams(params);
            if(randNumb %2 == 0){
                imv.setImageResource(R.drawable.ic_baseline_brightness_3_24);
            }else {
                imv.setImageResource(R.drawable.ic_baseline_cloud_24);
            }
            binding.container.addView(imv);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw();
            }
        });
    }
    private void draw() {
        // Worker Thread / Background Thread
        // Using sendMessage
        /*int numb = Integer.parseInt(binding.edtNumbOfView.getText().toString());
        binding.container.removeAllViews();
        Thread bgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<= numb;i++){
                    Message message = handler.obtainMessage();

                    message.arg1 = i *100/numb; // percent
                    message.obj = random.nextInt(100); //random number

                    handler.sendMessage(message);
                    SystemClock.sleep(100);
                }
            }
        });*/

        //bgThread.start();

        //Using post
        int numb = Integer.parseInt(binding.edtNumbOfView.getText().toString());
        binding.container.removeAllViews();
        Thread bgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<= numb;i++){
                    percent = i *100/numb; // percent
                    randNumb = random.nextInt(100); //random number

                    handler.post(foregroundThread);
                    SystemClock.sleep(100);
                }
            }
        });

        bgThread.start();
    }
}