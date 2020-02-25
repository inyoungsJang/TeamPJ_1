package com.example.teampj_1;

import android.util.Log;

class StateManager {
    private static final StateManager ourInstance = new StateManager();

    static StateManager getInstance() {
        return ourInstance;
    }

    private boolean isLogin = false;
    private boolean isSignup = false;

    private StateManager() {
    }

    void setIsSignup(boolean isSignup) {
        this.isSignup = isSignup;
    }

    boolean getIsSignup() {
        return isSignup;
    }

    void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    boolean getIsLogin() {
        return isLogin;
    }

}
