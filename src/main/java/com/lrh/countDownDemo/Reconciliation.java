package com.lrh.countDownDemo;

/**
 * countDown 并行处理对账
 *
 * @description:
 * @author: lrh
 * @date: 2020/5/20 19:56
 */


import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 串行逻辑
 *    ->  收集商品订单 ---> 收集派送订单 ---> 对比 ---> 存入差异库
 *
 * CountDownLatch并行处理
 *  ->  收集商品订单 -- |
 *                    |  ---> 对比 ---> 存入差异库
 *  ->  收集派送订单 --|
 *
 *  CyclicBarrier 并行处理
 *
 *   *  ->  收集商品订单 -- |	 收集商品订单 -- |	      收集商品订单 -- |
 *  *                     | ---> 				| --->  				 |
 *  *  ->  收集派送订单 -- |		  收集派送订单 -- |		   收集派送订单 -- |
 *
 *						  | ---> 对比 ---> 存入差异库	 |	---> 对比 ---> 存入差异库
 */
public class Reconciliation {


	/**
	 * 串行处理
	 */
	public void serial() {
		List<GoodOrder> goodOrder = getGoodOrder();
		List<ExpressOrder> expressOrder = getExpressOrder();
		List<DiffOrder> diffOrderList = compare(goodOrder, expressOrder);
		saveDiffOrder(diffOrderList);
	}

	/**
	 * 简单的线程处理并行
	 */
	public void parallelWithThread() throws InterruptedException {

		OrderThread getGoodOrderThread = new OrderThread();
		OrderThread getExpressOrderThread = new OrderThread();
		/**
		 ExecutorService executorService = Executors.newFixedThreadPool(2);
		 executorService.execute(() -> {
		 });
		 使用线程池无法处理等待
		 */

		getGoodOrderThread.start();
		getExpressOrderThread.start();

		getGoodOrderThread.join();
		getExpressOrderThread.join();

		List<Order> goodOrder = getGoodOrderThread.getOrders();
		List<Order> expressOrder = getExpressOrderThread.getOrders();
		List<DiffOrder> compare = compare(goodOrder, expressOrder);
		saveDiffOrder(compare);
	}

	/**
	 * 线程池 + countDown 处理
	 */
	BlockingQueue blockingQueue = new LinkedBlockingDeque(10);
	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 3_000, TimeUnit.SECONDS, blockingQueue);

	public void parallelWithCountDownLatch() throws InterruptedException {
		AtomicReference<List<GoodOrder>> goodOrders = null;
		AtomicReference<List<ExpressOrder>> expressOrders = null;
		CountDownLatch countDownLatch = new CountDownLatch(2);
		threadPoolExecutor.execute(() -> {
			goodOrders.set(getGoodOrder());
			countDownLatch.countDown();
		});
		threadPoolExecutor.execute(() -> {
			expressOrders.set(getExpressOrder());
			countDownLatch.countDown();
		});
		countDownLatch.await();
		List<DiffOrder> diffOrderList = compare(goodOrders.get(), expressOrders.get());
		saveDiffOrder(diffOrderList);
	}

	/**
	 * 线程池 + countDown 处理
	 */
	ThreadPoolExecutor callbackExecutor =
			new ThreadPoolExecutor(1, 1, 3_000, TimeUnit.SECONDS, blockingQueue);

	//CyclicBarrier 的计数器减到0后回调执行 Runnable ,执行完毕后重置计数值为原始值，
	// 此处采用了线程去执行，所以如果能从线程池中获取到线程的情况下是可以直接进行下一步的
	final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
		//此处如果不用线程去执行的情况相当于同步了！！！！
		callbackExecutor.execute(() -> compareWithQueue());
	});

	final Vector goodOrder = new Vector();
	final Vector expressOrder = new Vector();

	public void parallelWithCycleBarrier() throws InterruptedException {
		threadPoolExecutor.execute(() -> {
			//这里需要注意的是产出的数据需要同步 这里没做处理，暂时假设是同步的
			goodOrder.addAll(getGoodOrder());
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		threadPoolExecutor.execute(() -> {
			expressOrder.addAll(getExpressOrder());
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
	}

	private List<DiffOrder> compareWithQueue() {
//		Object goodOrder = goodOrder.remove(0);
//		Object expressOrder = expressOrder.remove(0);
		//省略 。。。 比较 返回
		return Collections.EMPTY_LIST;
	}





	public class OrderThread extends Thread {

		private List<Order> orders;

		@Override
		public void run() {
			super.run();
		}

		public List<Order> getOrders() {
			return orders;
		}
	}

	/**
	 * 获取商品订单
	 * @return
	 */
	private List<GoodOrder> getGoodOrder() {
		return Collections.EMPTY_LIST;
	}


	/**
	 * 获取派送订单
	 * @return
	 */
	private List<ExpressOrder> getExpressOrder() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * 存入差异订单
	 * @param diffOrders
	 */
	private void saveDiffOrder(List<DiffOrder> diffOrders) {

	}

	/**
	 * 比较差异
	 * @param goodOrders
	 * @param expressOrders
	 * @return
	 */
	private List<DiffOrder> compare(List goodOrders, List expressOrders) {
		return Collections.EMPTY_LIST;
	}

	interface Order {

	}


	/**
	 * 商品订单
	 */
	static class GoodOrder implements Order {


	}

	/**
	 * 派送单
	 */
	static class ExpressOrder implements Order {
	}


	/**
	 * 差异订单
	 */
	static class DiffOrder {
	}


}
