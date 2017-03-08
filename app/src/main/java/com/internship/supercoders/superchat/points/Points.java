package com.internship.supercoders.superchat.points;

import android.app.Dialog;

import com.internship.supercoders.superchat.chat.chat_model.Message;
import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.dialog.DialogModel;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_info.UserDataPage;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUserData;
import okhttp3.ResponseBody;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

// TODO: 1/30/17 [Code Review] Move 'QuickBlox-REST-API-Version' header to basic client initialization,
// seems like it is the same for all API calls. Also use Retrofit's Authenticator/Interceptor interfaces
// to provide token for requests. https://futurestud.io/tutorials/retrofit-token-authentication-on-android
public interface Points {

    interface AuthorizationPoint {

        @Headers({
                "Content-Type: application/json",
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @POST("/session.json")
        Call<Session> getSession(@Body Map<String, String> map);

        @POST("/session.json")
        Observable<Session> getRxSession(@Body Map<String, String> map);

    }

    interface CreateFilePoint {
        @POST("/blobs.json")
        Call<Blob> createFile(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body Blob blob);
    }

    interface DeclaringFileUploadedPoint {

        @PUT("/blobs/{id}/complete.json")
        Call<Void> declaringUpload(@Path("id") int id, @Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body Blob blob);

    }

    interface RegistrationPoint {

        @POST("/users.json")
        Call<UpdateUser> registration(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body ReqUser user);
    }

    interface ResetPasswordPoint {

        @GET("users/password/reset.json?")
        Call<Void> resetPassword(@Header("Content-Type") String cont, @Header("QB-Token") String token, @Query("email") String email);

    }

    interface SignInPoint {
        @POST("/login.json")
        Call<UpdateUserData> signIn(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body VerificationData verificationData);

    }

    interface SignInPoint2 {
        @POST("/login.json")
        Call<UpdateUser> signIn(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body VerificationData verificationData);

        Observable<UpdateUser>rxSignIn(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body VerificationData verificationData);

    }


    interface SignOut {
        @Headers({
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @DELETE("/login.json")
        Observable<Response<Void>> signOut(@Header("QB-Token") String token);

        @DELETE("/session.json")
        Observable<Response<Void>> destroySession(@Header("QB-Token") String token);


    }

    interface UserAuthorizatoinPoint {
        @Headers({
                "Content-Type: application/json",
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @POST("/session.json")
        Call<Session> userAuthorizatoin(@Body ALog user);
    }

    interface RxUserAuthorizationPoint {
        @Headers({
                "Content-Type: application/json",
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @POST("/session.json")
        Observable<Session> rxUserAuthorizatoin(@Body ALog user);
    }

    interface UpdateUserPoint {

        @PUT("/users/{id}.json")
        Call<UpdateUser> update(@Path("id") String id, @Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body UpdateUser user);
    }

    interface RetrieveDialogs {
        @GET("chat/Dialog.json")
        Call<DialogModel> retrieve(@Header("QB-Token") String token);

    }

    interface DownloadFilePoint {
        @GET("blobs/{id}/download.json")
        Call<ResponseBody> downloadFile(@Path("id") String blobId, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token);

    }

    interface CreateDialog {
        @POST("chat/Dialog.json")
        Call<Void> createDialog(@Header("Content-Type") String cont, @Header("QB-Token") String token, @Body Dialog dialog);

    }


    interface RetrieveMessages {
        @GET("chat/Message.json?")
        Observable<Response<Message>> retrieveMessages(@Header("QB-Token") String token, @Query("chat_dialog_id") String dialogId);
    }

    interface DeleteMessage {
        @DELETE("chat/Message/{message_id}.json")
        Observable<Response<Void>> deleteMessage(@Header("Content-Type") String cont, @Header("QB-Token") String token, @Path("message_id") String messageId);


    }


    interface RxRetriveAllUsers {
        @Headers({
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @GET("/users.json?")
        Observable<UserDataPage> getUserInfoPage(@Header("QB-Token") String token, @Query("page") String page, @Query("per_page") String perPage);
    }

    interface RxDownloadFile {
        @Headers({
                "QuickBlox-REST-API-Version: 0.1.0"
        })
        @GET("/blobs/{blob_id}/download.json")
        Observable<ResponseBody> getFile(@Header("QB-Token") String token, @Path("blob_id") String blobId);
    }

}
