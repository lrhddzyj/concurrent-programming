package com.lrh.threadLocal;

/**
 * ThreadLocal 最佳实践
 *
 * 用于一个线程上的参数传输  传输完成之后需要手动删除key,否则会有线程泄露的风险
 *
 * @description:
 * @author: lrh
 * @date: 2020/10/29 15:16
 */
public class ThreadUserContext implements AutoCloseable {

  private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

  public ThreadUserContext(User user) {
    userThreadLocal.set(user);
  }

  public static User get() {
    return userThreadLocal.get();
  }

  @Override
  public void close() {
    userThreadLocal.remove();
  }
}
