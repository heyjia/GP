package com.heihei.bookrecommendsystem.mahout;

import com.heihei.bookrecommendsystem.entity.RecommendItemDO;
import com.heihei.bookrecommendsystem.util.DBConnectionUtil;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class RecommendByItemJob {
    private static final int RECOMMEND_NUM = 5;
    private static Queue<RecommendItemDO> heap = new PriorityQueue<>(new Comparator<RecommendItemDO>() {
        @Override
        public int compare(RecommendItemDO o1, RecommendItemDO o2) {
            return o1.getVal().compareTo(o2.getVal());
        }
    });
    public static void main(String[] args) {
        recommendBookbyItem();
    }

    public static void timerJob() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
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
//            DataModel dataModel = MahoutFactory.buildFileDataModel("D:/GP/BookRecommendSystem/src/main/resources/douban-rating-300.txt");
            DataModel dataModel = MahoutFactory.buildFileDataModel("D:/GP/BookRecommendSystem/src/main/resources/train4.txt");
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
        showResultByItem(dataModel, itemSimilarity);
    }

    private static void showResultByItem(DataModel dataModel, ItemSimilarity itemSimilarity) throws TasteException {
        int count = 0;
        LongPrimitiveIterator itemIDIterator = dataModel.getItemIDs();
        Connection conn = null;
        try {
            conn = DBConnectionUtil.getConn();
            //遍历所有物品
            while (itemIDIterator.hasNext()) {
                heap.clear();
                System.out.println("==================================================");
                Long itermID = itemIDIterator.next();
                LongPrimitiveIterator otherItemIDIterator = dataModel.getItemIDs();
                //打印物品相似度
                while (otherItemIDIterator.hasNext()){
                    Long otherItermID = otherItemIDIterator.next();
                    float val = (float) itemSimilarity.itemSimilarity(itermID,otherItermID);
                    if (!Double.isNaN(val)) {
                        System.out.println("物品 "+itermID+" 与物品 "+otherItermID+" 的相似度为： "+val);
                        RecommendItemDO r = new RecommendItemDO();
                        r.setBookIdFrom(itermID);
                        r.setBookIdTo(otherItermID);
                        r.setVal(val);
                        if (heap.size() < RECOMMEND_NUM) {
                            heap.add(r);
                        }else{
                            if(heap.peek().getVal() < val){
                                heap.remove();
                                heap.add(r);
                            }
                        }
                    }
                }
                while (heap != null && heap.size() > 0) {
                    RecommendItemDO r = heap.remove();
                    String sql = "INSERT INTO recommend_item(book_id_from,book_id_to,val) VALUES(?,?,?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setLong(1,r.getBookIdFrom());
                    preparedStatement.setLong(2,r.getBookIdTo());
                    preparedStatement.setFloat(3,r.getVal());
                    preparedStatement.executeUpdate();
                    count++;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            System.out.println("基于物品的推荐线程结束，总推荐数量:" + count);
            if (conn != null) {
                try {
                    conn.close();
                }catch (Exception e) {
                    System.out.println("关闭数据库连接异常");
                    e.printStackTrace();
                }
            }
        }
    }
}
