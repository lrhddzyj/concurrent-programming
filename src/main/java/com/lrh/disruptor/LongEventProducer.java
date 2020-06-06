package com.lrh.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @description:
 * @author: lrh
 * @date: 2020/6/6 10:12
 */
public class LongEventProducer {


	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(ByteBuffer byteBuffer) {
		long sequence = ringBuffer.next();
		try {
			//从Disruptor中的RingBuffer获取Entity
			LongEvent event = ringBuffer.get(sequence);
			//填充数据
			event.set(byteBuffer.getLong(0));
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
