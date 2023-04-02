package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Identifiable;
import com.example.lib.LotteryEntry;
import com.example.lib.LotteryTicket;
import com.example.lib.Ticket;
import com.example.lib.Week;
import com.example.lib.create_pair;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OutputActivity extends AppCompatActivity {

    DatabaseReference ref;
    TextView TV;
    TextView timeTV;
    TextView result;
    TextView eat;
    ImageView left_spark;
    ImageView right_spark;
    TextView back;

    pl.droidsonroids.gif.GifImageView fail;
    String currentUser;
    String priorInput;
    ArrayList<Identifiable> ticketsWithSameCalStr;


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
        String weekNode= new Week.CurrentWeek().getWeekTitle();
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
        readWeek();
    }

    private void  readWeek(){
        ticketsWithSameCalStr = new ArrayList<Identifiable>();
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Cannot read user telegram handle", task.getException());
                    Toast.makeText(OutputActivity.this,"Cannot read your result, log out and try again",Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(OutputActivity.this, LoginActivity.class));
                }
                else {
                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            LotteryTicket ticket = postSnapshot.getValue(LotteryTicket.class);
                            //Log.i("time",entry.calStr());
                            if (ticket!=null&&ticket.getCalStr().equals(priorInput)){
                                ticketsWithSameCalStr.add(ticket);
                            }

                        }

                        create_pair cp = new create_pair();
                        cp.Create(ticketsWithSameCalStr);
                        Log.i("identifiable","works");

                        /**
                         * TODO: Call your processing methods here
                         * (ticketsWithSameCalStr exists as populated here)
                         * */
                        //TV.setText("none");
                        TV.setText(cp.find_pair(cp.getStore_pair(),currentUser));
                        if (cp.find_pair(cp.getStore_pair(),currentUser).equalsIgnoreCase("No one signed up")){
                            result.setText("Oh No");
                            fail.setImageResource(R.drawable.sorry);
                            eat.setText("Try Again");
                            left_spark.setImageResource(R.drawable.plain);
                            right_spark.setImageResource(R.drawable.plain);
                        }
                        Log.i("test",cp.find_pair(cp.getStore_pair(),currentUser));
                        timeTV.setText(priorInput.replace(" ","\n"));
                        //Log.i("test2",priorInput.replace(" ","\n"));
                        Log.i("test2",priorInput);
                    }
                    else{
                        //dont know why it gets blank
                        Log.i("else", "Still in else block.");//didnt work
                        TV.setText("Some technical error,\n try again");
                    }
                }

            }
        });

    }
    //https://firebase.google.com/docs/database/web/read-and-write


}



/**TODO: Process entryArrayList using Pair and create_Pair
 * Please import the classes from the lib module so that we
 * can keep backend separate from frontend
 * done
 *
 * */