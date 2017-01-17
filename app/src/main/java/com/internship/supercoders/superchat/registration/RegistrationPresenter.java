package com.internship.supercoders.superchat.registration;


import java.io.File;

public interface RegistrationPresenter {
    void validateData(File file, String email, String password, String fullname, String phone, String website);
    void addUserAva();
    void onDestroy();
}