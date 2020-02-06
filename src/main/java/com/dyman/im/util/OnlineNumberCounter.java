package com.dyman.im.util;

/**
 * @author dyman
 * @describe
 * @date 2020/2/5
 */
public class OnlineNumberCounter {

    private volatile long onlineNumber;

    public OnlineNumberCounter() {
        onlineNumber = 0L;
    }

    public static OnlineNumberCounter getInstance(){
        return OnlineNumberCounterHolder.instance;
    }

    private static class OnlineNumberCounterHolder {
        private static final OnlineNumberCounter instance = new OnlineNumberCounter();
    }

    public long getOnlineNumber() {
        return onlineNumber;
    }

    public long increment() {
        onlineNumber += 1;
        return onlineNumber;
    }

    public long decrease() {
        onlineNumber -= 1;
        return onlineNumber;
    }

}
