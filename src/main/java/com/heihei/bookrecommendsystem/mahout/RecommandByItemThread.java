package com.heihei.bookrecommendsystem.mahout;

public class RecommandByItemThread implements Runnable {
    @Override
    public void run() {
        System.out.println("========基于物品定时器线程启动=========");
        System.out.println("=====将于每日0点整计算数据并进行推荐=====");
        RecommendByItemJob.timerJob();
    }

    public static void main(String[] args) {
        RecommandByItemThread runnable = new RecommandByItemThread();
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
