package com.example.game.UtilsClasses;

public class UtilMethods {
    public static String extractNameFromEmail(String email){
        int index = 0 ;
        char[] name = new char[email.length()] ;
        for (int i = 0 ; i < email.length() ; i ++){
            if(email.charAt(i) == '@')
                break ;
            name[index++] = email.charAt(i) ;
        }

        return new String(name).trim() ;
    }
}
