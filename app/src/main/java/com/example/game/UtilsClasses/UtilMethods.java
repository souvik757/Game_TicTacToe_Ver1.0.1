package com.example.game.UtilsClasses;

public class UtilMethods {
    public static String extractNameFromEmail(String email){
        int index = 0 ;
        for (char ch : email.toCharArray()){
            index++ ;
            if(ch == '@') {
                break;
            }
        }
        return email.substring(0,index-1) ;
    }
}
