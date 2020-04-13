package com.heihei.bookrecommendsystem.mahout;

import com.heihei.bookrecommendsystem.entity.RecommendDO;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.*;
import java.util.*;

public class RecommendByUserJob {
    public static final double NaN = 0.0d / 0.0;
    final static int NEIGHBORHOOD_NUM = 4;
    public static ArrayList<RecommendDO> list = new ArrayList<>();
    public static void main(String[] args) {
       // getDate("D:/GP/BookRecommendSystem/src/main/resources/train3.txt","D:/GP/BookRecommendSystem/src/main/resources/train4.txt");
        recommendBookbyUser();
        System.out.println("长度：" + list.size());
        for (RecommendDO r : list) {
            System.out.println(r.toString());
        }
    }

    private static void getDate(String fileNameIn,String fileNameOut) {
        BufferedReader br = null;
        BufferedWriter writer = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileNameIn)),
                    "UTF-8"));
            File file=new File(fileNameOut);
            String lineTxt = null;
            if(file.isFile()&&!file.exists()){
                System.out.println("找不到指定的文件");
                file.createNewFile();// 不存在则创建  
            }else{
                writer = new BufferedWriter(new FileWriter(file));
            }
            while ((lineTxt = br.readLine()) != null) {
                System.out.println(lineTxt);
                String outTxt = lineTxt.substring(0,lineTxt.length() - 1);
                System.out.println(outTxt);
                writer.write(outTxt);
                writer.write("\n");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
                recommendBookbyUser();
            }
        },time,1000*60*60*24);
    }

    private static void recommendBookbyUser(){
        try {
            //DataModel dataModel = MahoutFactory.buildMysqlDataModel("localhost",3306,"gp","root","123456","user_book_score","user_id","book_id","score","time");
            DataModel dataModel = MahoutFactory.buildFileDataModel("D:/GP/BookRecommendSystem/src/main/resources/train4.txt");
            //基于用户的协同过滤算法
            userEuclidean(dataModel, SimilarityEnum.EUCLIDEAN, NeighborHoodEnum.NEAREST);
        } catch (TasteException e) {
            System.out.println("推荐异常");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("读取文件异常");
            e.printStackTrace();
        }
    }

    private static void showResultbyUser(DataModel dataModel, RecommenderBuilder recommenderBuilder) throws TasteException {
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
                    RecommendDO r = new RecommendDO();
                    r.setBookId(item.getItemID());
                    r.setUserId(userId);
                    r.setScore(item.getValue());
                    list.add(r);
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
        showResultbyUser(dataModel,recommenderBuilder);
    }
}
