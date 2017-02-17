package com.internship.supercoders.superchat.authorization;

import com.internship.supercoders.superchat.models.user_authorization_response.VerificationData;
import com.internship.supercoders.superchat.utils.UserPreferences;

/**
 * Created by RON on 21.01.2017.
 */
public class AuthPresenterImpl implements AuthPresenter, AuthInteractor.AuthFinishedListener {
    AuthView view;
    UserPreferences userPreferences;
    AuthInteractor interactor ;

    public AuthPresenterImpl(AuthView view){
        this.view = view;
        // TODO: 1/30/17 [Code Review] You should use this instance in AuthPresenter layer
        userPreferences = new UserPreferences(view.getContext());
        interactor = new AuthInteractorImpl();
    }

    @Override
    public void onError() {
        if (view != null) {
          //  view.showAuthorizationError();
        }
    }



    @Override
    public void onDestroy() {
        // TODO: 1/30/17 [Code Review] Nullify view instance here!
        view = null;
    }

    @Override
    public void changePassword() {

    }

    @Override
    public void validateData(VerificationData verificationData) {
        view.showProgress();
    }

    @Override
    public void signIn(String token, String login, String password) {
        interactor.signIn(token, login, password, this);
    }


    @Override
    public void onSuccess(String token) {
    view.navigateToLogin(token);

    }

    @Override
    public void onError(String error) {
      view.showAuthorizationError(error);
    }
}
