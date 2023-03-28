package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.lib.Entry;
import com.example.lib.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import com.example.lib.User;
public class HomeActivity extends AppCompatActivity{
    DatabaseReference userRef;
    DatabaseReference weekRef;
    String myUID;
    Button OutputButton;
    Button InputButton;
    Button LogoutButton;

    TextView TV;
    String telegramHandle;
    String weekNode;
    String priorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        /**
         *needed Week Method: public String weekForViewResult ()
         */
        weekNode="Week10";

        userRef= FirebaseDatabase.getInstance().getReference("Users").child(myUID);
        weekRef=FirebaseDatabase.getInstance().getReference().child(weekNode);


        TV=(TextView)findViewById(R.id.textName);

        readTelegramHandle();


        TV.setText("Welcome, "+telegramHandle);





        LogoutButton=(Button)findViewById(R.id.signOut);
        LogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));

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

    }
    private void  readTelegramHandle(){

        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Cannot read user telegram handle", task.getException());
                    Toast.makeText(HomeActivity.this,"Cannot read your telegram handle,log out and try again",Toast.LENGTH_LONG);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                else {
                    Log.i("firebase", String.valueOf(task.getResult().getValue()));
                    User user=task.getResult().getValue(User.class);
                    telegramHandle=user.getTelegramHandle();
                    TV.setText("Welcome, "+telegramHandle);
                    readPriorInput();
                }
            }
        });
    }

    private void readPriorInput(){
        weekRef.child(telegramHandle).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Cannot read user telegram handle", task.getException());
                    Log.i("readPriorInputError","problem if dataSnapshot does not exist");
                    priorInput="somethingWrong";
                    Toast.makeText(HomeActivity.this,"Cannot read prior input,log out and try again",Toast.LENGTH_LONG);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                else {
                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){
                        Ticket ticket= dataSnapshot.getValue(Ticket.class);
                        Entry entry=new Entry(ticket);
                        Log.i(TAG, "entryValue: "+entry.toString());
                        priorInput=entry.calStr();

                    }
                    else{
                        priorInput="noPriorInput";
                        OutputButton.setVisibility(View.GONE);

                    }
                    Log.i("priorInput",priorInput);
                }
            }
        });
    }
    //https://firebase.google.com/docs/database/web/read-and-write

}