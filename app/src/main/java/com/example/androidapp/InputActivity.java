package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
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


import androidx.appcompat.app.AppCompatActivity;


import com.example.lib.LotteryTicket;

import com.example.lib.Week;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


interface DbReadStringOnChange{
    void onDataChanged(String readString);
}




public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    Button datePickerBtn,timePickerBtn,joinButton;
    DatabaseReference ref;

    String myUID;
    String telegramHandle;
    TextView priorInputTV;
    TextView deleteEntryTV;
    GregorianCalendar selectedDateTime;
    String weekNode;
    TextView back;

    Week.WeekTitle week_title_next;

    Week.MaxDayForJoinLottery week_max;

    Week.MinDayForJoinLottery week_min;


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

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        myUID= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        // week the user can enter the lottery for
        week_title_next = new Week.NextWeek();
        weekNode = week_title_next.getWeekTitle();


        ref = FirebaseDatabase.getInstance().getReference();

        selectedDateTime=new GregorianCalendar(Locale.getDefault());

        //userInfoRef =ref.child(weekNode).child(telegramHandle);
        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        timer.schedule(new TimerTask() {
            public void run() {
                startActivity(new Intent(InputActivity.this,HomeActivity.class));
            }
        }, time);


        displayCurrentEntry();

        // single item array instance to store which element is selected by user initially
        // it should be set to zero meaning none of the element is selected by default
        checkedItem[0]=-1;

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                startActivity(new Intent(InputActivity.this, HomeActivity.class));
                break;
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

    public void displayCurrentEntry(){
        DatabaseOperations.readCurrentEntryPersistent(weekNode, telegramHandle, new DbReadStringOnChange() {
            @Override

            public void onDataChanged(String readString) {
                if (readString.equals(DatabaseOperations.SOMETHINGWRONG)){
                    Toast.makeText(InputActivity.this,"Cannot read prior input,log out and try again",Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(InputActivity.this, LoginActivity.class));
                    Log.i("displayCE",readString);
                }
                /**Already in XML, but in the instance that user delete entry, Join Lottery should be reset as if they have not entered the lottery*/
                else if (readString.equals(DatabaseOperations.NOTENTERED)){

                    priorInputTV.setText("You have not entered the Lottery.");
                    joinButton.setText("Join Lottery");
                    timePickerBtn.setText("Select Time");
                    datePickerBtn.setText("Select Date");
                    deleteEntryTV.setVisibility(View.GONE);
                    Log.i("displayCE",readString);
                }
                else{
                    priorInputTV.setText("Current entry: "+readString);
                    joinButton.setText("Edit entry");
                    deleteEntryTV.setVisibility(View.VISIBLE);
                    Log.i("displayCE",readString);
                }
            }
        });
    }

    public void datePicker(){

        // on below line we are getting
        // the instance of our calendar.
        GregorianCalendar gregCal = new GregorianCalendar(Locale.getDefault());

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
        week_min = new Week.MinDateForJoinLottery();
        final int minDay = week_min.getMinDay();
        final int minMonth = week_min.getMinMonth();
        final int minYear = week_min.getMinYear();
        gregCal.set(minYear, minMonth - 1, minDay);
        datePickerDialog.getDatePicker().setMinDate(
                gregCal.getTimeInMillis());

        // Changing mCalendar date from current to
        // some random MAX day 20/08/2021 20 Aug 2021
        /**
         *needed Week Method: public int maxDayForJoinLottery ()
         *needed Week Method: public int maxMonthForJoinLottery ()
         *needed Week Method: public int maxYearForJoinLottery ()
         **/
        week_max = new Week.MaxDateForJoinLottery();
        final int maxDay = week_max.getMaxDay();
        final int maxMonth = week_max.getMaxMonth();
        final int maxYear = week_max.getMaxYear();
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
                    selectedDateTime.set(GregorianCalendar.SECOND,0);

                    break;
                case 1:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,12);
                    selectedDateTime.set(GregorianCalendar.MINUTE,30);
                    selectedDateTime.set(GregorianCalendar.SECOND,0);

                    break;
                case 2:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                    selectedDateTime.set(Calendar.MINUTE,0);
                    selectedDateTime.set(GregorianCalendar.SECOND,0);

                    break;
                case 3:

                    selectedDateTime.set(GregorianCalendar.HOUR_OF_DAY,13);
                    selectedDateTime.set(GregorianCalendar.MINUTE,30);
                    selectedDateTime.set(GregorianCalendar.SECOND,0);

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

        joinLottery();
    }

    public void joinLottery(){
        DatabaseOperations.addDataUnderWeek(selectedDateTime, telegramHandle, weekNode, new PlainTaskCompleteListener() {
            @Override
            public void onBackendComplete(boolean success) {
                if (success){
                    Toast.makeText(InputActivity.this,"Joined lottery successfully", Toast.LENGTH_LONG).show();
                    timePickerBtn.setText("Select Time");
                    datePickerBtn.setText("Select Date");
                    Log.i("joinLottery","successful");
                }
                else{

                    Toast.makeText(InputActivity.this,"Unable to join lottery! Try again!",Toast.LENGTH_LONG).show();
                    Log.i("joinLottery","failed");
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
            deleteEntryDbCall();

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

    public void deleteEntryDbCall(){

        DatabaseOperations.deleteEntry(weekNode, telegramHandle, new PlainTaskCompleteListener() {
            @Override
            public void onBackendComplete(boolean success) {
                if (success){
                    Toast.makeText(InputActivity.this,"Deleted entry successfully!", Toast.LENGTH_LONG).show();
                    Log.i("deleteEntryDbCall","successful");
                }
                else{
                    Toast.makeText(InputActivity.this,"Unable to delete entry! ",Toast.LENGTH_LONG).show();
                    Log.i("deleteEntryDbCall","failed");
                }
            }
        });


    }






}

