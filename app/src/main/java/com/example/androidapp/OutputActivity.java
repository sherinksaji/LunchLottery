package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.lib.Week;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class OutputActivity extends AppCompatActivity {

    DatabaseReference ref;
    TextView TV;
    TextView timeTV;
    TextView result;
    TextView eat;
    ImageView left_spark;
    ImageView right_spark;
    TextView back;
    String weekNode;
    pl.droidsonroids.gif.GifImageView fail;
    String currentUser;
    String priorInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent=getIntent();
        currentUser=intent.getStringExtra("telegramHandle");
        priorInput=intent.getStringExtra("priorInput");
        /**
         *needed Week Method: public String weekForViewResult ()
         */
        weekNode= new Week.CurrentWeek().getWeekTitle();
        Log.i("weekNode",weekNode);
        //String weekNode = "Week10"; //manual display
        ref = FirebaseDatabase.getInstance().getReference().child(weekNode);
        TV=(TextView) findViewById(R.id.outputTV);
        timeTV=(TextView) findViewById(R.id.timeTV);
        result=(TextView) findViewById(R.id.congrats);
        fail =(pl.droidsonroids.gif.GifImageView) findViewById(R.id.gifImageView);
        eat = findViewById(R.id.eat);
        left_spark = findViewById(R.id.left_spark);
        right_spark = findViewById(R.id.right_spark);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutputActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        displayResults();

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(OutputActivity.this,HomeActivity.class));
            }
        }, time);
    }

    private void displayResults(){
        create_pair.processLotteryResult(weekNode, priorInput, currentUser, new PlainTaskCompleteListener() {
            @Override
            public void onBackendComplete(boolean success) {
                Log.i("displayResults",create_pair.getLotteryResult());
                TV.setText(create_pair.getLotteryResult());
                timeTV.setText(priorInput.replace(" ","\n"));
                if (!success){
                    Toast.makeText(OutputActivity.this,"Something wrong: Logging out. Try again.",Toast.LENGTH_LONG).show();
                    AuthenticationOperations.logout();
                    startActivity(new Intent(OutputActivity.this,LoginActivity.class));
                }
                if (create_pair.getLotteryResult().equalsIgnoreCase("No one signed up")){
                    result.setText("Oh No");
                    fail.setImageResource(R.drawable.sorry);
                    eat.setText("Try Again");
                    left_spark.setImageResource(R.drawable.plain);
                    right_spark.setImageResource(R.drawable.plain);
                }

            }
        });
    }





}



