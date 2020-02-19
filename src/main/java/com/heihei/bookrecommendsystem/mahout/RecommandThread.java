package com.heihei.bookrecommendsystem.mahout;

public class RecommandThread implements Runnable {
    @Override
    public void run() {
        System.out.println("============定时器线程启动=============");
        System.out.println("=====将于每日0点整计算数据并进行推荐=====");
        RecommandJob.timerJob();
    }

    public static void main(String[] args) {
        RecommandThread runnable = new RecommandThread();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
