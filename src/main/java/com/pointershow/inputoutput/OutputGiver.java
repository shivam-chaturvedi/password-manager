package com.pointershow.inputoutput;


import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import com.pointershow.manager.PasswordManager;
import com.pointershow.manager.PasswordManager.PasswordEntry;



public class OutputGiver {
    private static void copyToClipboard(String text){
        try{
            StringSelection stringSelection=new StringSelection(text);
            Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection,null);
        }
        catch(HeadlessException e){
            System.err.println("You Can Copy This text From Here : "+text);
        }
    }

    public static void show(){
            System.out.println("...... All Passwords ......");
        for(PasswordEntry entry: PasswordManager.getAllEntries()){
            System.out.println("Password for "+entry.userid+" is : "+entry.password);
        }
    }

    public static void show(String userid){
        try {
            String password=PasswordManager.getPassword(userid);
            System.out.println("Password For "+userid+" is : "+ password);
            copyToClipboard(password);
            System.out.println("\nPASSWORD COPIED TO CLIPBOARD !!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
