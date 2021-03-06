/*
 * Created by Talab Omar on 10/27/17 4:26 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/27/17 4:16 PM
 */

package com.codertal.studybook.features.authentication.login;

import com.codertal.studybook.base.SubscribablePresenter;
import com.codertal.studybook.features.authentication.login.domain.LoginResponse;
import com.codertal.studybook.base.BaseRxPresenter;

public interface LoginContract {

    interface View {

        void showLoginUi();

        void showDashboard();

        void showNetworkErrorMessage();

        void showUnknownErrorMessage();

        void showCancelledMessage();
    }

    abstract class Presenter extends SubscribablePresenter {

        abstract void loadLogin(int viewId);

        abstract void loadSkipLogin(int viewId);

        abstract void processLoginResult(LoginResponse loginResponse);
    }
}
