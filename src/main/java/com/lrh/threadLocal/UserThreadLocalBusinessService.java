package com.lrh.threadLocal;

/**
 * @description:
 * @author: lrh
 * @date: 2020/10/29 15:22
 */
public class UserThreadLocalBusinessService {


  public void doBusiness(User user) {
    try (ThreadUserContext threadUserContext = new ThreadUserContext(user)) {
      step1();
      step2();
      step3();
      step4();
    }
  }


  /**
   * 模拟其它地方调用
   */
  public void step1(){
    User user = ThreadUserContext.get();
    System.out.println("step1："+ user);

  }
  public void step2(){
    User user = ThreadUserContext.get();
    System.out.println("step2："+ user);

  }
  public void step3(){
    User user = ThreadUserContext.get();
    System.out.println("step3："+ user);

  }
  public void step4(){
    User user = ThreadUserContext.get();
    System.out.println("step4："+ user);

  }



}
