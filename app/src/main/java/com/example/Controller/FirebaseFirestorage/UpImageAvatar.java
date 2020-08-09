package com.example.Controller.FirebaseFirestorage;

public class UpImageAvatar {
    public static UpImageAvatar instance;
    public static UpImageAvatar getInstance()
    {
        if(instance == null)
            instance = new UpImageAvatar();
        return instance;
    }
    public  interface IupImageAvatar
    {
        void onSuccess(String Success);
        void onFail(String Fail);
    }
    public void UpImageAvatarUser()
    {

    }
}
