package com.example.androidapp;

import static android.content.ContentValues.TAG;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

    EditText editTextItemName, editTextQuantity;

    Button datePickerBtn,timePickerBtn,joinButton;



    DatabaseReference ref;
    DatabaseReference userInfoRef;
    DatabaseReference writeRef;

    String myUID;
    String telegramHandle;
    TextView priorInputTV;

    GregorianCalendar selectedDateTime;
    String weekNode;



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
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.dateButton:
                datePicker();
                break;
            case R.id.timeButton:
                showTimePickerDialog();
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




    private void showTimePickerDialog() {

        DialogFragment dialogFragment = new TimePickerDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "time_picker");

        switch(TimePickerDialogFragment.getIndex()){

            case -1:
                timePickerBtn.setText("Select time");

            case 0:
                timePickerBtn.setText("12:00");
                selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,12);
                selectedDateTime.set(GregorianCalendar.MINUTE,0);

                break;
            case 1:
                timePickerBtn.setText("12:30");
                selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,12);
                selectedDateTime.set(GregorianCalendar.MINUTE,30);

                break;
            case 2:
                timePickerBtn.setText("13:00");
                selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                selectedDateTime.set(Calendar.MINUTE,0);

                break;
            case 3:
                timePickerBtn.setText("13:30");
                selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                selectedDateTime.set(GregorianCalendar.MINUTE,30);

                break;

        }

    }

    public static class TimePickerDialogFragment extends DialogFragment {

        private static int index=-1;

        public static int getIndex() {
            return index;
        }

        public static void setIndex(int index) {
            TimePickerDialogFragment.index = index;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pickTime)
                    .setItems(R.array.timings_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            setIndex(which);
                        }
                    });
            return builder.create();
        }
    }






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