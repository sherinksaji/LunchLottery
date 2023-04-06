package com.example.androidapp;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lib.Identifiable;
import com.example.lib.IdentifiableComparator;
import com.example.lib.LotteryTicket;
import com.example.lib.Pair;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import okhttp3.internal.concurrent.TaskRunner;

interface DbReadArrayListComplete {
        public void onBackendComplete(ArrayList<Identifiable> identifiables);
    }

   public class create_pair {
       private static final String SOMETHINGWRONG = "Something wrong";

       public static String getLotteryResult() {
           return lotteryResult;
       }

       public static void setLotteryResult(String lotteryResult) {
           create_pair.lotteryResult = lotteryResult;
       }

       public static void setStore_pair(ArrayList<Pair> store_pair) {
           Store_pair = store_pair;
       }

       private static String lotteryResult;
       private static ArrayList<Pair> Store_pair = new ArrayList<Pair>();

       public static void Create(ArrayList<Identifiable> lst) { //create pairs from entry array list
           Store_pair.clear();
           Log.i("Store pair size", String.valueOf(Store_pair.size()));
           for (Identifiable i:lst){
               Log.i("CreateInCreatePair",i.identity());
           }
           Collections.sort(lst, new IdentifiableComparator());
           if (lst.size() > 1) {
               Collections.shuffle(lst, new Random(100)); // to have a randomise the pairs
           }
           if (lst.size() == 1) {
               Store_pair.add(new Pair((lst.get(0).identity()), "No one signed up"));
           } else if (lst.size() % 2 == 1) {
               Store_pair.add(new Pair((lst.get(0).identity()), (lst.get(1).identity()), (lst.get(2).identity())));
               Store_pair.add(new Pair((lst.get(1).identity()), (lst.get(0).identity()), (lst.get(2).identity())));
               Store_pair.add(new Pair((lst.get(2).identity()), (lst.get(0).identity()), (lst.get(1).identity())));
               for (int i = 3; i < lst.size(); i += 2) {
                   Store_pair.add(new Pair(lst.get(i).identity(), lst.get(i + 1).identity()));
                   Store_pair.add(new Pair(lst.get(i + 1).identity(), lst.get(i).identity()));
               }
           } else {
               for (int i = 0; i < lst.size(); i += 2) {
                   Store_pair.add(new Pair(lst.get(i).identity(), lst.get(i + 1).identity()));
                   Store_pair.add(new Pair(lst.get(i + 1).identity(), lst.get(i).identity()));
               }
           }

           for (int i=0;i<Store_pair.size();i++){
               Log.i("Store pair item",Store_pair.get(i).getId());
           }
       }

       public static ArrayList<Pair> getStore_pair() { //return array list with all the stored pairs
           return Store_pair;
       }

       public static String find_pair(ArrayList<Pair> lst, String id) { // return the partner paired to
           for (int i = 0; i < lst.size(); i++) {
               if (lst.get(i).getId().equalsIgnoreCase(id)) {
                   return lst.get(i).getPartner();
               }
           }
           return "No Pair found"; //everyone should have a partner unless only one person in the time slot, which we would show no partner.
       }
        /**identifiables cannot be zero when reading result, bcos only users who have a result are allowed to enter the
         * OutputActivity. So if user is entering the page somehow and does not even have his own identifiable instance read
         * into the identifiables, then there is a problem.*/
       public static void processLotteryResult(String currentWeek, String priorInput,final String currentUser, final PlainTaskCompleteListener listener) {
           DatabaseOperations.readWeekResultOnce(currentWeek, priorInput, new DbReadArrayListComplete() {
               @Override
               public void onBackendComplete(ArrayList<Identifiable> identifiables) {
                   if (identifiables==null||identifiables.size() == 0) {
                       create_pair.setLotteryResult(SOMETHINGWRONG);
                       listener.onBackendComplete(false);
                   } else {
                       for (Identifiable i:identifiables){
                           Log.i("processLR",i.identity());
                       }
                       create_pair.Create(identifiables);
                       create_pair.setLotteryResult(create_pair.find_pair(create_pair.getStore_pair(), currentUser));
                       listener.onBackendComplete(true);
                   }
                   Log.i("processLotteryResult",getLotteryResult());
               }
           });

       }
   }