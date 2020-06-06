package com.lrh.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @description:
 * @author: lrh
 * @date: 2020/6/6 10:09
 */
public class LongEventHandler implements EventHandler<LongEvent> {
	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("E:" + event);
	}
}
