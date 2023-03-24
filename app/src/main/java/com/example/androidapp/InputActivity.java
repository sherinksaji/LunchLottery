package com.example.androidapp;

import static android.content.ContentValues.TAG;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.TextView;
import android.widget.Toast;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{



    Button datePickerBtn,timePickerBtn,joinButton;



    DatabaseReference ref;


    String myUID;
    String telegramHandle;
    TextView priorInputTV;

    GregorianCalendar selectedDateTime;
    String weekNode;
    final int[] checkedItem= new int [1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Intent intent=getIntent();
        telegramHandle=intent.getStringExtra("telegramHandle");



        datePickerBtn = (Button) findViewById(R.id.dateButton);
        datePickerBtn.setOnClickListener(this);

        timePickerBtn = (Button) findViewById(R.id.timeButton);
        timePickerBtn.setOnClickListener(this);
     
        joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(this);

        priorInputTV=(TextView) findViewById(R.id.priorInputTV);



        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        weekNode="Week10";

        ref = FirebaseDatabase.getInstance().getReference();

        selectedDateTime=new GregorianCalendar();

        //userInfoRef =ref.child(weekNode).child(telegramHandle);


        readPriorInput();

        // single item array instance to store which element is selected by user initially
        // it should be set to zero meaning none of the element is selected by default
        checkedItem[0]=-1;

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.dateButton:
                datePicker();
                break;
            case R.id.timeButton:
                timePicker();
                break;
            case R.id.joinButton:
                checkInput();
                break;

        }
    }
    private void  readPriorInput(){
        ref.child(weekNode).child(telegramHandle).addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()){
                    Ticket ticket= dataSnapshot.getValue(Ticket.class);
                    Entry entry=new Entry(ticket);
                    Log.i(TAG, "entryValue: "+entry.toString());
                    priorInputTV.setText("Current entry: "+entry.calStr());
                    joinButton.setText("Edit entry");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void datePicker(){

        // on below line we are getting
        // the instance of our calendar.
        GregorianCalendar gregCal = new GregorianCalendar();

        // on below line we are getting
        // our day, month and year.
        int year = gregCal.get(GregorianCalendar.YEAR);
        int month = gregCal.get(GregorianCalendar.MONTH);
        int day = gregCal.get(GregorianCalendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        InputActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.

                                datePickerBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                selectedDateTime.set(GregorianCalendar.DAY_OF_MONTH,dayOfMonth);
                                selectedDateTime.set(GregorianCalendar.MONTH,monthOfYear);
                                selectedDateTime.set(GregorianCalendar.YEAR,year);




                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
        //https://www.geeksforgeeks.org/datepicker-in-android/




        //  I NEED THE WEEK LOGIC TO IMPLEMENTED FOR ME TO USE

        // Changing mCalendar date from current to
        // some random MIN day 15/08/2021 15 Aug 2021
        // If we want the same current day to be the MIN
        // day, then mCalendar is already set to today and
        // the below code will be unnecessary
        final int minDay = 27;
        final int minMonth = 3;
        final int minYear = 2023;
        gregCal.set(minYear, minMonth - 1, minDay);
        datePickerDialog.getDatePicker().setMinDate(
                gregCal.getTimeInMillis());

        // Changing mCalendar date from current to
        // some random MAX day 20/08/2021 20 Aug 2021
        final int maxDay = 31;
        final int maxMonth = 3;
        final int maxYear = 2023;
        gregCal.set(maxYear, maxMonth - 1, maxDay);
        datePickerDialog.getDatePicker().setMaxDate(
                gregCal.getTimeInMillis());
                datePickerDialog.show();
        Log.i("dayOfMonthSelectDp", String.valueOf(selectedDateTime.get(GregorianCalendar.DAY_OF_MONTH)));

        //https://www.geeksforgeeks.org/set-min-and-max-selectable-dates-in-datepicker-dialog-in-android/?ref=rp
    }

    public void timePicker(){
        // AlertDialog builder instance to build the alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(InputActivity.this);
        // title of the alert dialog
        alertDialog.setTitle("Select Time");

        // list of the items to be displayed to the user in the
        // form of list so that user can select the item from
        final String[] listItems = new String[]{"12:00", "12:30", "13:00","13:30"};

        // the function setSingleChoiceItems is the function which
        // builds the alert dialog with the single item selection

        alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
            // update the selected item which is selected by the user so that it should be selected
            // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
            checkedItem[0] = which;
            // now also update the TextView which previews the selected item
            timePickerBtn.setText(listItems[which]);

            switch(which){
                case 0:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,12);
                    selectedDateTime.set(GregorianCalendar.MINUTE,0);

                    break;
                case 1:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,12);
                    selectedDateTime.set(GregorianCalendar.MINUTE,30);

                    break;
                case 2:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                    selectedDateTime.set(Calendar.MINUTE,0);

                    break;
                case 3:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                    selectedDateTime.set(GregorianCalendar.MINUTE,30);

                    break;
            }

            // when selected an item the dialog should be closed with the dismiss method
            dialog.dismiss();

        });



        // set the negative button if the user is not interested to select or change already selected item
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> {

        });

        // create and build the AlertDialog instance with the AlertDialog builder instance
        AlertDialog customAlertDialog = alertDialog.create();

        // show the alert dialog when the button is clicked
        customAlertDialog.show();
    }
    //https://www.geeksforgeeks.org/alert-dialog-with-singleitemselection-in-android/


    public void checkInput(){
        String dateButtonStr=datePickerBtn.getText().toString().trim();
        String timeButtonStr = timePickerBtn.getText().toString().trim();


        if (dateButtonStr.equalsIgnoreCase("select date")){
            Toast.makeText(InputActivity.this,"Please select date!",Toast.LENGTH_LONG).show();
            return;
        }

        if (timeButtonStr.equalsIgnoreCase("select time")){
            Toast.makeText(InputActivity.this,"Please select time!",Toast.LENGTH_LONG).show();
            return;
        }

        addDataUnderWeek();
    }


    public void addDataUnderWeek()

    {
        Log.i("dayOfMonthSelectAd", String.valueOf(selectedDateTime.get(GregorianCalendar.DAY_OF_MONTH)));
        Entry entry=new Entry(telegramHandle,selectedDateTime);

        Log.i("calStr",entry.calStr());
        Ticket ticket=entry.fbTicket();

        ref.child("Week10").child(telegramHandle).setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(InputActivity.this,"Joined lottery successfully", Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(InputActivity.this,"Unable to join lottery! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });

    }






}