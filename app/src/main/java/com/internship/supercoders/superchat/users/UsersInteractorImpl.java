package com.internship.supercoders.superchat.users;

import com.internship.supercoders.superchat.api.ApiClient;
import com.internship.supercoders.superchat.data.AppConsts;
import com.internship.supercoders.superchat.db.DBMethods;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.points.Points;
import com.internship.supercoders.superchat.users.interfaces.UsersInteractor;
import com.internship.supercoders.superchat.utils.FileManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Max on 17.02.2017.
 */

public class UsersInteractorImpl implements UsersInteractor {
    private DBMethods mDbManager;
    private FileManager mFileManager;

    UsersInteractorImpl(DBMethods dbManager, FileManager fileManager) {
        this.mDbManager = dbManager;
        this.mFileManager = fileManager;

    }

    @Override
    public Observable<UserDataPage> getUsers() {
        final Points.RxRetriveAllUsers apiService = ApiClient.getRxRetrofit().create(Points.RxRetriveAllUsers.class);
        return apiService.getUserInfoPage(mDbManager.getToken(), "1", "100");
    }

    @Override
    public Observable<ResponseBody> getFile(String blobId) {
        final Points.RxDownloadFile apiService = ApiClient.getRxRetrofit().create(Points.RxDownloadFile.class);
        return apiService.getFile(mDbManager.getToken(), blobId);
    }

    private boolean writeAvatarToDisk(ResponseBody body, int id) throws IOException {
        mFileManager.saveToInternalStorage(AppConsts.AVATAR_DIR, Integer.toString(id), body.bytes());
        return true;
    }
}
