package com.example.lib;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MyClass {
    public static void main(String[] args) {


        GregorianCalendar gregCal = new GregorianCalendar(2023,

                5,//June (January is month 0)
                4,
                12,
                0);
        LotteryTicket t= new LotteryTicket("sherin",Week.calStrCreator.getCalStr(gregCal));
        System.out.println(t.getCalStr());
        System.out.println(t.getTelegramHandle());
        System.out.println(t.identity());


        LotteryEntry entry = new LotteryEntry("sherinuhhahaha", gregCal);
        System.out.println(entry.toString());

        LotteryEntry entry1 = new LotteryEntry("marcia_sunflower", gregCal);

        LotteryEntry entry2 = new LotteryEntry("Ravenex", gregCal);
        LotteryEntry entry3 = new LotteryEntry("alexanderwikstrom", gregCal);
        LotteryEntry entry4 = new LotteryEntry("jnssay", gregCal);
        LotteryEntry entry5 = new LotteryEntry("rosapeltola", gregCal);
        LotteryEntry entry6 = new LotteryEntry("Jalamieee", gregCal);
        LotteryEntry entry7 = new LotteryEntry("turragdewan", gregCal);
        LotteryEntry entry8 = new LotteryEntry("liewsoonhao", gregCal);
        LotteryEntry entry9 = new LotteryEntry("after232", gregCal);
        LotteryEntry entry10 = new LotteryEntry("Blackvines", gregCal);
        LotteryEntry entry11 = new LotteryEntry("asapruki", gregCal);
        LotteryEntry entry12 = new LotteryEntry("namoikonk", gregCal);
        LotteryEntry entry13 = new LotteryEntry("gunnyjan", gregCal);
        LotteryEntry entry14 = new LotteryEntry("Erick_t", gregCal);
        ArrayList<Identifiable> entryArrayList = new ArrayList<>();
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
        for (Identifiable e : entryArrayList) {
            System.out.println(e.identity());
        }

        create_pair cp = new create_pair();
        cp.Create(entryArrayList);
        for (int i = 0; i < cp.getStore_pair().size(); i++) {
            System.out.println("name: " + cp.getStore_pair().get(i).getId() + ", partner: " + cp.getStore_pair().get(i).getPartner());
        }
        System.out.println(cp.find_pair(cp.getStore_pair(), "jalamieee"));
        System.out.println(cp.find_pair(cp.getStore_pair(), "jnssay"));
    }
}
        /*Pair pair1 = new Pair (entry,entry1);
        Boolean SherininPair=pair1.inPair("sherinuhhahaha");
        System.out.println(SherininPair);
        Boolean RaveninPair = pair1.inPair("ravenex");
        System.out.println(RaveninPair);
        Entry sherinMatch=pair1.match("sherinuhhahaha");
        System.out.println(sherinMatch.displayResultToMatch());*/



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
        //String currentUser="sherinuhhahaha";


