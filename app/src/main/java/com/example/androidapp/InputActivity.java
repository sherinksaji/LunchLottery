package com.example.androidapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextItemName, editTextQuantity;
    Button addButton;

    DatabaseReference ref;
    String myUID;
    String telegramHandle;
    TextView priorInputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Intent intent=getIntent();
        telegramHandle=intent.getStringExtra("telegramHandle");
        editTextItemName=(EditText) findViewById(R.id.item);
        editTextQuantity=(EditText) findViewById(R.id.qty);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        priorInputTV=(TextView) findViewById(R.id.priorInputTV);



        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("Week2").child(telegramHandle);


        readPriorInput();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.addButton:
                checkInput();
                break;

        }
    }
    private void  readPriorInput(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()){
                    Item item = dataSnapshot.getValue(Item.class);
                    Log.d(TAG, "Value is: " + item.toString());
                    priorInputTV.setText(item.toString());
                }
                else{
                    priorInputTV.setText("You did not enter any item yet");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void checkInput(){
        String itemName=editTextItemName.getText().toString().trim();
        String qtyStr = editTextQuantity.getText().toString().trim();
        int qty;

        if (itemName.isEmpty()){
            Toast.makeText(InputActivity.this,"Title is required!",Toast.LENGTH_LONG).show();
            return;
        }

        if (qtyStr.isEmpty()){
            Toast.makeText(InputActivity.this,"Quantity is required!",Toast.LENGTH_LONG).show();
            return;
        }

        try{
            qty = Integer.parseInt(qtyStr);

        } catch (NumberFormatException e) {
            Toast.makeText(InputActivity.this,"For quantity, enter an integer!",Toast.LENGTH_LONG).show();
            return;
        }

        addDataToFirebase(itemName,qty);
    }


    public void addDataToFirebase(String itemName,int qty)
    {


        Item item = new Item(itemName,qty,telegramHandle);

        ref.setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(InputActivity.this,"Item has been added successfully", Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(InputActivity.this,"Failed to add item! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });

        startActivity(new Intent(this, HomeActivity.class));




    }




}