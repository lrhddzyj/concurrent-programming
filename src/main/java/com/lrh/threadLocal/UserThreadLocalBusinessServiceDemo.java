package com.lrh.threadLocal;

/**
 * ThreadLocal 最佳实践demo
 * @description:
 * @author: lrh
 * @date: 2020/10/29 15:22
 */
public class UserThreadLocalBusinessServiceDemo {

  public static void main(String[] args) {
    UserThreadLocalBusinessService userThreadLocalBusinessService = new UserThreadLocalBusinessService();
    User user = new User();
    user.setId("1");
    user.setName("ABC");
    userThreadLocalBusinessService.doBusiness(user);
  }

}
