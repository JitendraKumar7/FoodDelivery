package com.haris.meal4u.InterfaceUtil;

public interface PlayerCallback {

    void onPlayerStarted(int audioSessionId);

    void onStreamChange(int position,long duration);

}
