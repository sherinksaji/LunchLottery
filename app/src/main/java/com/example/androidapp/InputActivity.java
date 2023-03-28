package com.example.androidapp;

import static android.content.ContentValues.TAG;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.DialogInterface;
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

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{



    Button datePickerBtn,timePickerBtn,joinButton;



    DatabaseReference ref;


    String myUID;
    String telegramHandle;
    TextView priorInputTV;
    TextView deleteEntryTV;
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

        deleteEntryTV=(TextView) findViewById(R.id.deleteEntryTV);
        deleteEntryTV.setOnClickListener(this);

        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        /**
         *needed Week Method: public String weekForJoinLottery ()
         */
        weekNode="Week11";

        ref = FirebaseDatabase.getInstance().getReference();

        selectedDateTime=new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());

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
            case R.id.deleteEntryTV:
                deleteEntryDialog();
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
                    deleteEntryTV.setVisibility(View.VISIBLE);

                }
                else{
                    priorInputTV.setText("This is the first time your are entering the Lottery.");
                    joinButton.setText("Join Lottery");
                    timePickerBtn.setText("Select Time");
                    datePickerBtn.setText("Select Date");
                    deleteEntryTV.setVisibility(View.GONE);
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
        /**
         *needed Week Method: public int minDayForJoinLottery ()
         *needed Week Method: public int minMonthForJoinLottery ()
         *needed Week Method: public int minYearForJoinLottery ()
         */
        final int minDay = 3;
        final int minMonth = 4;
        final int minYear = 2023;
        gregCal.set(minYear, minMonth - 1, minDay);
        datePickerDialog.getDatePicker().setMinDate(
                gregCal.getTimeInMillis());

        // Changing mCalendar date from current to
        // some random MAX day 20/08/2021 20 Aug 2021
        /**
         *needed Week Method: public int maxDayForJoinLottery ()
         *needed Week Method: public int maxMonthForJoinLottery ()
         *needed Week Method: public int maxYearForJoinLottery ()
         */
        final int maxDay = 7;
        final int maxMonth = 4;
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
            Log.i("Timezone", String.valueOf(selectedDateTime.getTimeZone()));

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
        Log.i("Hour", String.valueOf(selectedDateTime.get(GregorianCalendar.HOUR_OF_DAY)));
        Entry entry=new Entry(telegramHandle,selectedDateTime);


        Log.i("calStr",entry.calStr());
        Ticket ticket=entry.fbTicket();
        Log.i("Before Db", String.valueOf(ticket.getBookingTimeStamp()));

        ref.child(weekNode).child(telegramHandle).setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(InputActivity.this,"Joined lottery successfully", Toast.LENGTH_LONG).show();
                    timePickerBtn.setText("Select Time");
                    datePickerBtn.setText("Select Date");


                }
                else {

                    Toast.makeText(InputActivity.this,"Unable to join lottery! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    public void deleteEntryDialog(){
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(InputActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to delete your entry into the lottery?");


        // Set Alert Title
        builder.setTitle("Delete Entry");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            deleteEntry();

        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    public void deleteEntry(){
        ref.child(weekNode).child(telegramHandle).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(InputActivity.this,"Deleted entry successfully!", Toast.LENGTH_LONG).show();


                }
                else {

                    Toast.makeText(InputActivity.this,"Unable to delete entry! "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }
            }
        });

    }






}

