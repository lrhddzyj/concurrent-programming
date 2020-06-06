package com.lrh.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @description:
 * @author: lrh
 * @date: 2020/6/6 10:08
 */
public class LongEventFactory implements EventFactory<LongEvent> {

	@Override
	public LongEvent newInstance() {
		return new LongEvent();
	}

}
