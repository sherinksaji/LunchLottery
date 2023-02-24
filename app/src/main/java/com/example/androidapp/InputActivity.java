package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextItemName, editTextQuantity;
    Button addButton;

    DatabaseReference ref;
    String myUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editTextItemName=(EditText) findViewById(R.id.item);
        editTextQuantity=(EditText) findViewById(R.id.qty);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);



        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("Week1");

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.addButton:
                checkInput();
                break;

        }
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


        Item item = new Item(itemName,qty,myUID);

        ref.child(myUID).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
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