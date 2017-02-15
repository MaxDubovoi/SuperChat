package com.internship.supercoders.superchat.points;

import com.internship.supercoders.superchat.models.authorization_response.Session;
import com.internship.supercoders.superchat.models.blob.Blob;
import com.internship.supercoders.superchat.models.registration_request.ReqUser;
import com.internship.supercoders.superchat.models.user_authorization_response.ALog;
import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.models.user_update_request.UpdateUser;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
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
        Call<Objects> signIn(@Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body VerificationData verificationData);

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
        Call<UpdateUser> update(@Path("id")String id, @Header("Content-Type") String cont, @Header("QuickBlox-REST-API-Version") String version, @Header("QB-Token") String token, @Body UpdateUser user);
    }


}
