package com.dyman.im.disr;

import com.lmax.disruptor.EventFactory;

/**
 * @author dyman
 * @describe
 * @date 2020/3/26
 */
public class LongEventFactory implements EventFactory {

    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
