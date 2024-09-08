package com.pointershow.inputoutput;

import java.util.Scanner;

import com.pointershow.manager.PasswordGenerator;
import com.pointershow.manager.PasswordManager.PasswordEntry;

public class InputTaker {
    public static PasswordEntry takeInput(){
        String userid="";
        Scanner input=new Scanner(System.in);
        while(userid.isEmpty()){
            System.out.print("Enter UserId( email or usernam ) : ");
            userid=input.nextLine();
            System.out.println("");
        }
        System.out.print("Set Password For "+userid+" ( Press Enter To Auto Generate ) : ");
        String password=input.nextLine();
        if(password.isEmpty()){
            password=PasswordGenerator.generate();
        }
        input.close();
        PasswordEntry entry=new PasswordEntry(userid, password);
        return entry;
    }
}
