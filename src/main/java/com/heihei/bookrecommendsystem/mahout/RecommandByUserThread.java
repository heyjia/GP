package com.heihei.bookrecommendsystem.mahout;

public class RecommandByUserThread implements Runnable {
    @Override
    public void run() {
        System.out.println("========基于用户定时器线程启动=========");
        System.out.println("=====将于每日0点整计算数据并进行推荐=====");
        RecommendByUserJob.timerJob();
    }

    public static void main(String[] args) {
        RecommandByUserThread runnable = new RecommandByUserThread();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
