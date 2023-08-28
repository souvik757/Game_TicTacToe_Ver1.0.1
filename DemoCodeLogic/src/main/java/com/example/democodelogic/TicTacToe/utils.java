package com.example.democodelogic.TicTacToe;

public class utils {
    public static void main(String[] args) {
        System.out.println(extractNameFromEmail("howai8074@gmail.com"));
    }
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
