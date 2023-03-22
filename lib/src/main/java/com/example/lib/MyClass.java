package com.example.lib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MyClass {
    public static void main(String[] args) {
        GregorianCalendar gregCal=new GregorianCalendar(2023,
                5,//June (January is month 0)
                4,
                12,
                0);

        Entry entry = new Entry ("sherinuhhahaha",gregCal);
        System.out.println(entry.toString());

        Entry entry1 = new Entry ("marcia_sunflower",gregCal);

        Entry entry2 = new Entry ("Ravenex",gregCal);
        Entry entry3= new Entry ("alexanderwikstrom",gregCal);
        Entry entry4= new Entry ("jnssay",gregCal);
        Entry entry5 = new Entry ("rosapeltola",gregCal);
        Entry entry6= new Entry ("Jalamieee",gregCal);
        Entry entry7= new Entry ("turragdewan",gregCal);
        Entry entry8 =new Entry("liewsoonhao",gregCal);
        Entry entry9= new Entry("after232",gregCal);
        Entry entry10=new Entry("Blackvines",gregCal);
        Entry entry11= new Entry("asapruki",gregCal);
        Entry entry12 = new Entry("namoikonk",gregCal);
        Entry entry13= new Entry("gunnyjan",gregCal);
        Entry entry14= new Entry ("Erick_t",gregCal);
        ArrayList<Entry> entryArrayList=new ArrayList<>();
        entryArrayList.add(entry);
        entryArrayList.add(entry1);
        entryArrayList.add(entry2);
        entryArrayList.add(entry3);
        entryArrayList.add(entry4);
        entryArrayList.add(entry5);
        entryArrayList.add(entry6);
        entryArrayList.add(entry7);
        entryArrayList.add(entry8);
        entryArrayList.add(entry9);
        entryArrayList.add(entry10);
        entryArrayList.add(entry11);
        entryArrayList.add(entry12);
        entryArrayList.add(entry13);
        entryArrayList.add(entry14);
        for (Entry e: entryArrayList){
            System.out.println(e.toString());
        }

        Pair pair1 = new Pair (entry,entry1);
        Boolean SherininPair=pair1.inPair("sherinuhhahaha");
        System.out.println(SherininPair);
        Boolean RaveninPair = pair1.inPair("ravenex");
        System.out.println(RaveninPair);
        Entry sherinMatch=pair1.match("sherinuhhahaha");
        System.out.println(sherinMatch.displayResultToMatch());



        /**
         List of things to do:
         1. Implement EntryComparator to sort entryArrayList by telehandle. (Shivani)
         2. Implement Methods to convert an entryArrayList into a PairArrayList.
         3. Assuming the currentUser is in entryArrayList, implement methods to show the currentUser only their own result.
         4. Implement WeekClass: instructions in WeekClass
         5. Implement an abstract class called Group that is similar to Pair.
         (later we can even change item number 2 of this list to include polymorphism)
         *
         *
         *
         *
         TODO: Implement the EntryComparator
         sort Entry Array by telegramHandle: Shivani
         */

        /**
         TODO: Implement Methods/Classes to create pairs
         * There is a list of entries called entryArrayList.
         * While waiting for the EntryComparator class to be implemented, use the current order for entryArrayList
         * Create matchArrayList that takes in
         *If entryArrayList length= odd number,
         * create an Entry that takes in only the gregCal (just use the gregCal instantiated above) as input
         * whichever Entry object with telegramHandle gets paired with the above empty TelegramHandle wont \
         *have a match (already implemented in toString method in Entry class)
         *add this Entry object with no telegramHandle into the entryArrayList
         * now that the entryArrayList length is even,
         *use entryArrayList and Pair class to create pairs
         *the created pairs will be added to a pair array list called pairArrayList
         * Feel free to modify any classes
         */
        /**
         TODO: Implement Methods/classes to display result to current user
         *currently the user is set to sherinuhhahaha
         * input pairArrayList, entryArrayList
         * if user is not in entryArrayList, they should not see any result
         * if user is in entryArrayList,
         *usePairArrayList to find out who the currentUser's match is using methods in Pair class
         * make changes to Pair class and Entry class as u wish
         * Feel free to modify any classes
         */
        String currentUser="sherinuhhahaha";


    }

}