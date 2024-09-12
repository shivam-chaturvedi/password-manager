package com.pointershow;

import com.pointershow.database.PasswordBackupDao;
import com.pointershow.inputoutput.InputTaker;
import com.pointershow.inputoutput.OutputGiver;
import com.pointershow.manager.PasswordManager;
import com.pointershow.manager.PasswordManager.PasswordEntry;

public class App 
{
    private static final String []USAGE_MESSAGE=new String[]{
       "Usage : pm <-add> (To Add a new userid and password )",
       "Usage : pm <-show> (To Show  all userid's and password's )",
       "Usage : pm <-show userid> (To Show password for <userid> )",
       "Usage : pm <-add -dpl <NUMBER> (To set default password length <NUMBER> for autogeneration of password )>"
    };
    
    public static void main( String[] args )
    {

        if(args.length>0 && args.length<4){
            if(args[0].equalsIgnoreCase("-add")){
                InputTaker taker=new InputTaker();
                if(args.length>2){
                    if(args[1].equalsIgnoreCase("-dpl")){
                        try{
                            taker.setDefaultPasswordLength(Integer.parseInt(args[2]));
                        }
                        catch(NumberFormatException e){
                            System.err.println("Using default Password Length : "+taker.getDefaultPasswordlength());
                        }
                    }
                }
                PasswordEntry entry=taker.takeInput();

                // Null is returned when a entry with same userid is already present 
                // takeInput() method will automatically handel the password updation
                if(entry!=null){
                    PasswordManager.addNewEntry(entry);
                }
            }       
            else if(args[0].equalsIgnoreCase("-show")){
                if(args.length>1){
                    OutputGiver.show(args[1]);
                }
                else{
                    OutputGiver.show();
                }
            }
            else if(args[0].equalsIgnoreCase("-backup")){
                PasswordBackupDao passwordBackupDao=new PasswordBackupDao();
                passwordBackupDao.syncWithDb();
            }
            else{
                for(String msg:USAGE_MESSAGE){
                    System.err.println(msg);
                }
            }
        }
        else{
            for(String msg:USAGE_MESSAGE){
                System.err.println(msg);
            }
        }
    }
}