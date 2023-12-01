package com.ze;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
   @Test
    public void test(){
       //提供一个threadlocal对象
       ThreadLocal t1 = new ThreadLocal();
       new Thread(()->{
           t1.set("周天泽");
           System.out.println(Thread.currentThread().getName()+t1.get());
           System.out.println(Thread.currentThread().getName()+t1.get());
           System.out.println(Thread.currentThread().getName()+t1.get());
       },"蓝色").start();

       new Thread(()->{
           t1.set("晓燕");
           System.out.println(Thread.currentThread().getName()+t1.get());
           System.out.println(Thread.currentThread().getName()+t1.get());
           System.out.println(Thread.currentThread().getName()+t1.get());
       },"红色").start();
   }

}
