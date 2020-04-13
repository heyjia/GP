package com.heihei.bookrecommendsystem.mahout;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import java.io.*;
import java.util.*;

public class RecommendByItemJob {
    public static void main(String[] args) {
        recommendBookbyItem();
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
                recommendBookbyItem();
            }
        },time,1000*60*60*24);
    }

    private static void recommendBookbyItem(){
        try {
            //DataModel dataModel = MahoutFactory.buildMysqlDataModel("localhost",3306,"gp","root","123456","user_book_score","user_id","book_id","score","time");
            DataModel dataModel = MahoutFactory.buildFileDataModel("D:/GP/BookRecommendSystem/src/main/resources/douban-rating-300.txt");
            //基于物品的协同过滤算法
            itemEuclidean(dataModel,SimilarityEnum.EUCLIDEAN);
        } catch (TasteException e) {
            System.out.println("推荐异常");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("读取文件异常");
            e.printStackTrace();
        }
    }

    private static void itemEuclidean(DataModel dataModel,SimilarityEnum similarityType) throws TasteException {
        System.out.println("userEuclidean：基于物品的协同过滤算法，相似度算法为：" + similarityType.toString());
        ItemSimilarity itemSimilarity = MahoutFactory.itemSimilarity(similarityType,dataModel);
        RecommenderBuilder recommenderBuilder = MahoutFactory.itemRecommender(itemSimilarity,true);
        // 用70%的数据用作训练，剩下的30%用来测试
        MahoutFactory.evaluate(EvaluatorEnum.AVERAGE_ABSOLUTE_DIFFERENCE,recommenderBuilder,null,dataModel,0.7);
        MahoutFactory.statsEvaluator(recommenderBuilder,null,dataModel,2);
//        showResultbyItem(dataModel,recommenderBuilder);
//        Recommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
        LongPrimitiveIterator itemIDIterator = dataModel.getItemIDs();
        //遍历所有物品
        while (itemIDIterator.hasNext()) {
            System.out.println("==================================================");
            Long itermID = itemIDIterator.next();
            LongPrimitiveIterator otherItemIDIterator = dataModel.getItemIDs();
            //打印物品相似度
            while (otherItemIDIterator.hasNext()){
                Long otherItermID = otherItemIDIterator.next();
                if (!Double.isNaN(itemSimilarity.itemSimilarity(itermID,otherItermID))) {
                    System.out.println("物品 "+itermID+" 与物品 "+otherItermID+" 的相似度为： "+itemSimilarity.itemSimilarity(itermID,otherItermID));
                }
            }
        }
    }
}
