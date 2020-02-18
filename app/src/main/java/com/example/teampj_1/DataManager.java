package com.example.teampj_1;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    public static DataManager getInstance() {
        return ourInstance;
    }

    private  UserData userData;

    private DataManager() {
    }

    public UserData getUserData(){
        if(userData==null){
            userData=new UserData();
        }
        return userData;
    }

}

class UserData{
    public String id;
    public String password;
    public String user_name;
    public String rfid;
}