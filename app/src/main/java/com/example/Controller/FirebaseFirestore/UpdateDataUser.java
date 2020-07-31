package com.example.Controller.FirebaseFirestore;

public class UpdateDataUser {
    private static UpdateDataUser instance;

    public static UpdateDataUser getInstance() {
        if (instance == null)
            instance = new UpdateDataUser();
        return instance;
    }

    public interface IUpdateDataUser {
        void onUpdateSuccess(String Success);

        void onUpdateFail(String Fail);
    }
    public void UpdateDataSV(IUpdateDataUser iUpdateDataUser)
    {

    }
}
