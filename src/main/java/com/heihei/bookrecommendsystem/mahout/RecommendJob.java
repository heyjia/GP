package com.heihei.bookrecommendsystem.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.IOException;
import java.util.*;

public class RecommendJob {
    final static int NEIGHBORHOOD_NUM = 4;

    public static void main(String[] args) {
        recommendBook();
    }

    public static void timerJob() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,21);
        calendar.set(Calendar.MINUTE,40);
        calendar.set(Calendar.SECOND,50);
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务开启");
                System.out.println("开始计算并推荐");
                recommendBook();
            }
        },time,1000*60*60*24);
    }

    private static void recommendBook() {
        try {
            DataModel dataModel = MahoutFactory.buildFileDataModel("D:/GP/BookRecommendSystem/src/main/resources/douban-rating-300.txt");
            //基于用户的协同过滤算法
            userEuclidean(dataModel, SimilarityEnum.EUCLIDEAN, NeighborHoodEnum.NEAREST);
            //基于物品的协同过滤算法
//            itemEuclidean(dataModel,SimilarityEnum.EUCLIDEAN);
        } catch (IOException e) {
            System.out.println("找不到指定文件");
            e.printStackTrace();
        }catch (TasteException e) {
            System.out.println("推荐异常");
            e.printStackTrace();
        }
    }

    private static void showResult(DataModel dataModel, RecommenderBuilder recommenderBuilder) throws TasteException {
        Recommender recommender = recommenderBuilder.buildRecommender(dataModel);
        LongPrimitiveIterator usersIterator = dataModel.getUserIDs();
        while (usersIterator.hasNext()) {
            System.out.println("========================================");
            Long userId = usersIterator.nextLong();
            System.out.println("用户Id："+userId);
            List<RecommendedItem> recommendItems = recommender.recommend(userId, 5);
            if (recommendItems == null || recommendItems.size() == 0) {
                System.out.println("无书籍推荐");
            }else{
                for (RecommendedItem item : recommendItems) {
                    System.out.println("给用户ID为" + userId + " 推荐商品ID为" + item.getItemID() + " 推荐值为:" + item.getValue());
                    //如果有推荐，就在推荐表中判断是否推荐的值是否存在，如果存在就更行value,不存在就插入，用JDBC的方式
                }
            }
        }
    }

    private static void userEuclidean(DataModel dataModel,SimilarityEnum similarityType,NeighborHoodEnum neighborHoodType) throws TasteException {
        System.out.println("userEuclidean：基于用户的协同过滤算法，相似度算法为：" + similarityType.toString() + " 近邻算法为：" + neighborHoodType.toString());
        UserSimilarity userSimilarity = MahoutFactory.userSimilarity(similarityType,dataModel);
        UserNeighborhood userNeighborhood = MahoutFactory.userNeighborhood(neighborHoodType,userSimilarity,dataModel,NEIGHBORHOOD_NUM);
        RecommenderBuilder recommenderBuilder = MahoutFactory.userRecommender(userSimilarity,userNeighborhood,true);
        // 用70%的数据用作训练，剩下的30%用来测试
        MahoutFactory.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE,recommenderBuilder,null,dataModel,0.7);
        MahoutFactory.statsEvaluator(recommenderBuilder,null,dataModel,2);
        showResult(dataModel,recommenderBuilder);
    }

    private static void itemEuclidean(DataModel dataModel,SimilarityEnum similarityType) throws TasteException {
        System.out.println("userEuclidean：基于物品的协同过滤算法，相似度算法为：" + similarityType.toString());
        ItemSimilarity itemSimilarity = MahoutFactory.itemSimilarity(similarityType,dataModel);
        RecommenderBuilder recommenderBuilder = MahoutFactory.itemRecommender(itemSimilarity,true);
        // 用70%的数据用作训练，剩下的30%用来测试
        MahoutFactory.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE,recommenderBuilder,null,dataModel,0.7);
        MahoutFactory.statsEvaluator(recommenderBuilder,null,dataModel,2);
        showResult(dataModel,recommenderBuilder);
    }
}
