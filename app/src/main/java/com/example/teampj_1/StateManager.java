package com.example.teampj_1;

import android.util.Log;

class StateManager {
    private static final StateManager ourInstance = new StateManager();

    static StateManager getInstance() {
        return ourInstance;
    }

    private boolean isLogin = false;

    private StateManager() {
    }

    void setIsLogin(boolean isLogin){
        Log.i("test","로그인 플래그 변경됨" + isLogin);
        this.isLogin = isLogin;
        if(!isLogin)
            DataManager.getInstance().Logout();
    }

    boolean getIsLogin()
    {
        return isLogin;
    }

}
