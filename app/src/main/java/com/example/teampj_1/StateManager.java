package com.example.teampj_1;

class StateManager {
    private static final StateManager ourInstance = new StateManager();

    static StateManager getInstance() {
        return ourInstance;
    }

    boolean isLogin = false;

    private StateManager() {
    }

}
