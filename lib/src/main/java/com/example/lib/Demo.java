package com.example.lib;

import java.util.GregorianCalendar;

public class Demo {
    public static void main(String[] args) {
        GregorianCalendar gregCal=new GregorianCalendar(2023,
                5,
                4,
                12,
                0);
        System.out.println(gregCal.MONTH);
        LotteryEntry e = new LotteryEntry("sherinuhhahaha",gregCal);

         System.out.println(e.calStr());
//       System.out.println(e.displayResultToMatch());


        LotteryEntry blankEntry = new LotteryEntry(gregCal);
//       System.out.println(blankEntry.toString());
//        System.out.println(blankEntry.displayResultToMatch());







        LotteryEntry e1 =  new LotteryEntry("daisy",gregCal);
        //Pair pairWMatch = new Pair (e,e1);
        //Boolean userInPair=pairWMatch.inPair("sherinuhhahaha");
//        System.out.println(userInPair);
//        Boolean userInPair2=pairWMatch.inPair("marcia");
//        System.out.println(userInPair2);
        //Entry userMatch=pairWMatch.match("sherinuhhahaha");
//        System.out.println(userMatch.toString());
//
//
//
//        Pair pairWithoutMatch = new Pair (e,blankEntry);
//        Entry userHasNoMatch=pairWithoutMatch.match("sherinuhhahaha");


    }
}
