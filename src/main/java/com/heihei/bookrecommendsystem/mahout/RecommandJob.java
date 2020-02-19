package com.heihei.bookrecommendsystem.mahout;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.sql.Connection;
import java.util.*;

public class RecommandJob {
    public static void timerJob() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,2);
        calendar.set(Calendar.MINUTE,41);
        calendar.set(Calendar.SECOND,0);
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务开启");
                System.out.println("开始计算并推荐");
                recommend();
            }
        },time,1000*60*60*24);
    }
    public static void recommend(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("gp");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        DataModel dataModel = null;
        try {
            dataModel = new MySQLJDBCDataModel(dataSource,"user_book_score","user_id","book_id","score","time");
//            //余弦相似度
//            UserSimilarity userSimilarity= new UncenteredCosineSimilarity(dataModel);
//            //欧几里得相似度
//            UserSimilarity userSimilarity= new EuclideanDistanceSimilarity(dataModel);
            //皮尔森相似度
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood userNeighborhood  = new NearestNUserNeighborhood(2,userSimilarity,dataModel);
            Recommender recommender = new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            LongPrimitiveIterator usersIterator = dataModel.getUserIDs();
            while (usersIterator.hasNext()) {
                System.out.println("========================================");
                Long userId = usersIterator.nextLong();
                System.out.println("用户Id："+userId);
                List<RecommendedItem> recommendItems = recommender.recommend(userId, 2);
                if (recommendItems == null || recommendItems.size() == 0) {
                    System.out.println("无书籍推荐");
                }else{
                    for (RecommendedItem item : recommendItems) {
                        System.out.println("给用户" + userId + "推荐商品ID为" + item.getItemID() + " 推荐值为:" + item.getValue());
                        //如果有推荐，就在推荐表中判断是否推荐的值是否存在，如果存在就更行value,不存在就插入，用JDBC的方式

                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String []args) {
        timerJob();
    }
}
