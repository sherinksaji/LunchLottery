package com.example.androidapp;

import static android.content.ContentValues.TAG;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lib.Identifiable;
import com.example.lib.LotteryTicket;
import com.example.lib.Slottable;
import com.example.lib.User;
import com.example.lib.Week;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class DatabaseOperations {

    public static final String SOMETHINGWRONG="Something wrong";
    public static final String NOTENTERED="Did not enter lottery for that week";

    public static void readTelegramHandle(String uid,final DbReadStringComplete listener){
        Log.i("readTelegramHandle","uid : "+uid);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("telegramHandle");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    listener.onBackendComplete(SOMETHINGWRONG);
                    Log.i("readTelegramHandle",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");

                }
                else {
                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){
                        String telegramHandle= dataSnapshot.getValue(String.class);
                        listener.onBackendComplete(telegramHandle);
                        Log.i("readTelegramHandle",telegramHandle);

                    }
                    else{
                        listener.onBackendComplete(SOMETHINGWRONG);
                        Log.i("readTelegramHandle","dataSnapshot does not exist");
                    }

                }
            }
        });
    }


    public static void readPriorInputOnce(String currentWeek,String telegramHandle, final DbReadStringComplete listener ){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(currentWeek).child(telegramHandle).child("calStr");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    listener.onBackendComplete(SOMETHINGWRONG);
                    Log.i("readPriorInputOnce",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");
                    Log.i("readPriorInputOnce",SOMETHINGWRONG);
                }
                else {
                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){
                        String calStr= dataSnapshot.getValue(String.class);
                        listener.onBackendComplete(calStr);
                        Log.i("readPriorInputOnce",calStr);

                    }
                    else{
                       listener.onBackendComplete(NOTENTERED);
                       Log.i("readPriorInputOnce",NOTENTERED);
                    }

                }
            }
        });

    }

    public static void readWeekResultOnce(String currentWeek,final String priorInput,final DbReadArrayListComplete listener){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(currentWeek);
        Log.i("RWResultOnce",currentWeek);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {


                if (task.isSuccessful()) {
                    ArrayList<Identifiable> identifiables=new ArrayList<>();

                    DataSnapshot dataSnapshot=task.getResult();
                    if (dataSnapshot.exists()){

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            LotteryTicket ticket = postSnapshot.getValue(LotteryTicket.class);
                            //Log.i("time",entry.calStr());
                            Log.i("priorInput",priorInput);
                            if (ticket!=null&&ticket.getCalStr().equals(priorInput)){
                                identifiables.add(ticket);
                                Log.i("ticketInRWRonce",ticket.getTelegramHandle());
                                Log.i("readWeekResultOnce",identifiables.toString());
                            }

                        }
                    }
                    listener.onBackendComplete(identifiables);

                }
                else{
                    Log.i("readWeekResultOnce",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");
                    listener.onBackendComplete(null);
                }

            }
        });

    }



    public static void addDataUnderWeek(GregorianCalendar selectedDateTime,String telegramHandle,String weekNode,final PlainTaskCompleteListener listener)
    {
        Log.i("selectedDateTime", String.valueOf(selectedDateTime.getTime().toString()));


        LotteryTicket ticket= new LotteryTicket(telegramHandle,Week.calStrCreator.getCalStr(selectedDateTime));
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(weekNode).child(telegramHandle);
        ref.setValue(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    listener.onBackendComplete(true);
                    Log.i("addDataUnderWeek","successful");
                }
                else {
                    listener.onBackendComplete(false);
                    Log.i("addDataUnderWeek",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");
                }
            }
        });
    }

    public static void addUser(User user,final PlainTaskCompleteListener listener){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        ref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    listener.onBackendComplete(true);
                    Log.i("addUser","successful");
                }
                else{
                    listener.onBackendComplete(false);
                    Log.i("addUser",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");
                }
            }
        });
    }



    public static void deleteEntry(String weekNode, String telegramHandle,final PlainTaskCompleteListener listener){
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child(weekNode).child(telegramHandle);
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    listener.onBackendComplete(true);
                    Log.i("deleteEntry","successful");
                }
                else {

                    listener.onBackendComplete(false);
                    Log.i("deleteEntry",(task.getException().getMessage()==null)?task.getException().getMessage():"Exception Not Found");
                }
            }
        });

    }

    public static void  readCurrentEntryPersistent(String weekNode,String telegramHandle,final DbReadStringOnChange listener){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(weekNode).child(telegramHandle).child("calStr");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()){
                    String calStr= dataSnapshot.getValue(String.class);
                    listener.onDataChanged(calStr);
                    Log.i("readCEPersistent",calStr);
                }
                else{
                    listener.onDataChanged(NOTENTERED);
                    Log.i("readCEPersistent",NOTENTERED);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                listener.onDataChanged(SOMETHINGWRONG);
                Log.i("readCEPersistent",SOMETHINGWRONG);
                Log.i("readCEPersistent", "Failed to read value.", error.toException());
            }
        });
    }

    public static void readWeekPersistent(String weekNode,final DbReadArrayListOnChange listener){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child(weekNode);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            @NonNull
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated
                ArrayList <Slottable> slottablesAL=new ArrayList<>();
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        LotteryTicket ticket = postSnapshot.getValue(LotteryTicket.class);
                        slottablesAL.add(ticket);

                    }
                    Log.i("RWPersistent","Datasnapshot exists");
                }
                else{
                    Log.i("RWPersistent","Datasnapshot does not exist");
                }
                listener.onDataChanged(true,slottablesAL);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i(TAG, "Failed to read value.", error.toException());
                Log.i("RWPersistent","failed");
                listener.onDataChanged(false,null);
            }
        });
    }
}

//https://firebase.google.com/docs/database/web/read-and-write
