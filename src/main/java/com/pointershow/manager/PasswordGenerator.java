package com.pointershow.manager;

import java.util.Random;

public class PasswordGenerator {

    private static final String UPPERCASECHARACTERS="QWERTYUIOPASDFGHJKLZXCVBNM"; 
    private static final String LOWERCASECHARACTERS="qwertyuiopzxcvbnmasdfghjkl"; 
    private static final String NUMBERS="1234567890"; 
    private static final String SYMBOLS="`~!@#$%^&*(_-+={}[];:|<>?)"; 

    private static final String []passwordMaker=new String[]{UPPERCASECHARACTERS,LOWERCASECHARACTERS,NUMBERS,SYMBOLS};
    
    private static final int DEFAULTPASSWORDLENGTH=15;

    public static String generate(int length){
        try{
            Random random=new Random();
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<length;i++){
                String selectedString=passwordMaker[random.nextInt(0,passwordMaker.length)];
                sb.append( selectedString.charAt(random.nextInt(0,selectedString.length() )));
            }
            return sb.toString();
        }
        catch(Exception e){
            System.err.println("Password Generation Failed :"+e.getMessage());
            return null;
        }
        
    }

    public static String generate(){
        return generate(DEFAULTPASSWORDLENGTH);
    }

    
}
