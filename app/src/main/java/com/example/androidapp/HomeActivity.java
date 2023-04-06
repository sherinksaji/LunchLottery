package com.example.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lib.Week;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

interface DbReadStringComplete {
    public void onBackendComplete(String result);
}
public class HomeActivity extends AppCompatActivity{
    DatabaseReference userRef;
    DatabaseReference weekRef;
    String myUID;
    Button OutputButton;
    Button InputButton;
    Button LogoutButton;
    Button PopulatedSlotsButton;

    TextView TV;
    String telegramHandle;
    String weekNode;
    String priorInput;

    private static final String SOMETHINGWRONG="Something wrong";
    private static final String NOTENTERED="Did not enter lottery for that week";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        /**
         *needed Week Method: public String weekForViewResult ()
         */
        weekNode= new Week.CurrentWeek().getWeekTitle();
        //weekNode = "week10"; //manual display
        Log.i("weekNode",weekNode);

        userRef= FirebaseDatabase.getInstance().getReference("Users").child(myUID);
        weekRef=FirebaseDatabase.getInstance().getReference().child(weekNode);


        TV=(TextView)findViewById(R.id.textName);





        getTelegramHandle();
        TV.setText("Welcome, "+telegramHandle);

        PopulatedSlotsButton=(Button)findViewById(R.id.populatedSlots);

        PopulatedSlotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ViewPopulatedSlots.class));
            }
        });



        LogoutButton=(Button)findViewById(R.id.signOut);
        LogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                displayLogout("Logging out");
            }
        });


        InputButton=(Button)findViewById(R.id.Input);
        InputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent InputIntent=new Intent(HomeActivity.this, InputActivity.class);
                InputIntent.putExtra("telegramHandle",telegramHandle);
                startActivity(InputIntent);
            }
        });




        OutputButton=(Button)findViewById(R.id.Output);
        OutputButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent OutputIntent=new Intent(HomeActivity.this, OutputActivity.class);
                OutputIntent.putExtra("telegramHandle",telegramHandle);
                OutputIntent.putExtra("priorInput",priorInput);
                startActivity(OutputIntent);


            }
        });

        if (Week.detectDay.isWeekend()){
            PopulatedSlotsButton.setVisibility(View.VISIBLE);
            OutputButton.setVisibility(View.GONE);
        }
        else{
            PopulatedSlotsButton.setVisibility(View.GONE);
            OutputButton.setVisibility(View.VISIBLE);
        }

    }

    private void getTelegramHandle() {
        if (AuthenticationOperations.getCurrentUserUid() == null) {
            displayLogout("Cannot read current user, logging out");
            Log.i("getTelegramHandle","null uid");
        }
        else {
            DatabaseOperations.readTelegramHandle(AuthenticationOperations.getCurrentUserUid(), new DbReadStringComplete() {
                @Override
                public void onBackendComplete(String result) {
                    if (result == DatabaseOperations.SOMETHINGWRONG) {
                        displayLogout("Cannot read current user, logging out");
                        Log.i("getTelegramHandle","readTelegramHandle didnt work");
                    } else {
                        Log.i("getTelegramHandle","in the else statement");
                        Log.i("getTelegramHandleResult",result);
                        telegramHandle = result;
                        Log.i("telegramHandle",result);
                        TV.setText("Welcome, "+telegramHandle);
                        getPriorInput();
                    }
                }
            });
        }
    }

    private void getPriorInput(){
        DatabaseOperations.readPriorInputOnce(weekNode, telegramHandle, new DbReadStringComplete() {
                @Override
                public void onBackendComplete(String result) {
                    if (result.equals(SOMETHINGWRONG)){
                        displayLogout("Cannot read prior input.Try again. Logging out.");
                    }
                    else if (result.equals(NOTENTERED)){
                        OutputButton.setVisibility(View.GONE);

                    }
                    else{
                        priorInput=result;
                        Log.i("priorInput",priorInput);
                    }
                }
            });
    }
    private void displayLogout(String msg){
        Toast.makeText(HomeActivity.this,msg,Toast.LENGTH_LONG).show();
        AuthenticationOperations.logout();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }


}