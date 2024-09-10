package com.pointershow.inputoutput;

import java.util.Scanner;

import com.pointershow.manager.PasswordGenerator;
import com.pointershow.manager.PasswordManager;
import com.pointershow.manager.PasswordManager.PasswordEntry;

public class InputTaker {
    private int DEFAULTPASSWORDLENGTH=15;

    public void setDefaultPasswordLength(int length){
        DEFAULTPASSWORDLENGTH=length;
    }

    public int getDefaultPasswordlength(){
        return DEFAULTPASSWORDLENGTH;
    }
    public PasswordEntry takeInput(){
        String userid="";
        try(Scanner input=new Scanner(System.in);){

            while(userid.isEmpty()){
                System.out.print("Enter UserId( email or username ) : ");
                userid=input.nextLine();
                System.out.println("");
            }
            System.out.print("Set Password For "+userid+" ( Press Enter To Auto Generate ) : ");
            String password=input.nextLine();
            if(password.isEmpty()){
                password=PasswordGenerator.generate(DEFAULTPASSWORDLENGTH);
                if(password==null){
                    System.exit(1); //Some error occured
                }
            }
            PasswordEntry entry=new PasswordEntry(userid, password);
            if(PasswordManager.isRepeated(entry)){
                System.err.println("Entry for "+userid+" is Already Present!");
                System.out.print("Do You Want To Update Password For "+userid+" (y/n): ");
                if(input.nextLine().equalsIgnoreCase("y")){
                    entry.password=password;
                    PasswordManager.updatePassword(entry);
                    System.out.println("Password Updated !");
                    return null;
                }
                else{
                    return null;
                }
            }
            return entry;
        }
        catch(Exception e){
            System.err.println("Error Taking Input: "+e.getMessage());
            return null;
        }
    }
}
