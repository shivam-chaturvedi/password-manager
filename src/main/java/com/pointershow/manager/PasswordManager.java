package com.pointershow.manager;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PasswordManager {
    private final static String STORAGEFILENAME = "password.manager";

    public static class PasswordEntry implements Serializable {
        private static final long serialVersionUID = 1L;

        public String userid;
        public String password;

        public PasswordEntry(String userid, String password) {
            this.userid = userid;
            this.password = password;
        }

        @Override
        public String toString() {
            return "{" + this.userid + ": " + this.password + "}";
        }
    }

    private static File getFile() {
        File storage = new File(STORAGEFILENAME);
        try {
            if (!storage.exists()) {
                storage.createNewFile();
                storage.setExecutable(false);
                storage.setReadable(false);
                storage.setWritable(false);
            }
            
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
        return storage;
    }

    public static void addNewEntry(PasswordEntry entry) {
        List<PasswordEntry> existingEntries = getAllEntries();
        existingEntries.add(entry);
        File file=getFile();
        file.setWritable(true);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            for (PasswordEntry e : existingEntries) {
                objectOutputStream.writeObject(e);
            }
        } catch (IOException e) {
            System.err.println("Error writing entry: " + e.getMessage());
        }
        finally{
            file.setWritable(false);
        }
    }

    public static List<PasswordEntry> getAllEntries() {
        List<PasswordEntry> entries = new LinkedList<>();
        File file = getFile();

        if(!file.exists() || file.length()==0){
            return entries;
        }

        file.setReadable(true);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    PasswordEntry entry = (PasswordEntry) objectInputStream.readObject();
                    entries.add(entry);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        finally{
            file.setReadable(false);
        }

        return entries;
    }

    public static String getPassword(String userid) throws Exception {
        for(PasswordEntry e:getAllEntries()){
            if(e.userid.equalsIgnoreCase(userid)){
                return e.password;
            }
        }
        throw new Exception("Entry Not Found");
    }

    public static boolean isRepeated(PasswordEntry e){
        for(PasswordEntry entry:getAllEntries()){
            if(e.userid.equalsIgnoreCase(entry.userid)){
                return true;
            }
        }
        return false;
    }

    public static void updatePassword(PasswordEntry newEntry){
        List<PasswordEntry> allEntries=getAllEntries();
        for(PasswordEntry e:allEntries){
            if(e.userid.equalsIgnoreCase(newEntry.userid)){
                e.password=newEntry.password;
            }
        }
        File file=getFile();
        file.setWritable(true);
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file))) {
            for(PasswordEntry entry:allEntries){
                oos.writeObject(entry);
            }
        } catch (IOException e) {
            System.err.println("Error While Updatig Password: "+e.getMessage());
        }
        finally{
            file.setWritable(false);
        }
    }
}
