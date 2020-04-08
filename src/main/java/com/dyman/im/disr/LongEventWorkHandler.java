package com.dyman.im.disr;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author dyman
 * @describe
 * @date 2020/3/26
 */
public class LongEventWorkHandler implements WorkHandler<LongEvent> {

    private String name = "";

    public LongEventWorkHandler(String name) {
        this.name = name;
    }

//    @Override
//    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
//
//    }

    @Override
    public void onEvent(LongEvent longEvent) throws Exception {
        System.out.println(longEvent.getValue() + "---->" + name);
    }
}
