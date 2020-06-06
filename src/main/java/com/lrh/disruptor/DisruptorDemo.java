package com.lrh.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * @description:
 * @author: lrh
 * @date: 2020/6/6 09:56
 */
public class DisruptorDemo {

	public static void main(String[] args) {
		//指定RingBuffer大小
		int bufferSize = 1024;

		//构建disruptor
		Disruptor<LongEvent> disruptor =
				new Disruptor(LongEvent::new,
						bufferSize,
						DaemonThreadFactory.INSTANCE);

		//注册时间处理器
		disruptor.handleEventsWith(new LongEventHandler());

		//启动disruptor
		disruptor.start();

		//获取RingBuffer
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);

		//Event生成器
		LongEventProducer longEventProducer = new LongEventProducer(ringBuffer);

		for (long i = 0; true; i++) {
			byteBuffer.putLong(0, i);
			longEventProducer.onData(byteBuffer);
		}
	}


}
