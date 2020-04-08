package com.dyman.im.disr;

import com.lmax.disruptor.EventHandler;

/**
 * @author dyman
 * @describe
 * @date 2020/3/26
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    private String name = "";

    public LongEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue() + "---->" + name);
    }
}
