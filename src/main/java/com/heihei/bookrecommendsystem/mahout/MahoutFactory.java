package com.heihei.bookrecommendsystem.mahout;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.*;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;

public class MahoutFactory {
    public static DataModel buildMysqlDataModel(String serverName,int port,String databaseName,String user,String password,String tableName,String userIDColumn,String itemIDColumn,String preferenceColumn,String timeStampColumn) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(serverName);
        dataSource.setPort(port);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(databaseName);
        DataModel dataModel = new MySQLJDBCDataModel(dataSource,tableName,userIDColumn,itemIDColumn,preferenceColumn,timeStampColumn);
        return dataModel;
    }

    public static DataModel buildFileDataModel(String file) throws IOException{
        return new FileDataModel(new File(file));
    }

    public static DataModel buildDataModelNoPref(String file) throws IOException, TasteException {
        return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File(file))));
    }

    public static DataModelBuilder buildDataModelNoPrefBuilder() {
        return new DataModelBuilder() {
            public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
                return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
            }
        };
    }

    public static UserSimilarity userSimilarity(SimilarirtEnum type,DataModel dataModel) throws TasteException {
        switch (type) {
            case PEARSON:
                //皮尔森相似度
                return new PearsonCorrelationSimilarity(dataModel);
            case COSINE:
                //余弦相似度
                return new UncenteredCosineSimilarity(dataModel);
            case TANIMOTO:
                //Tanimoto系数相似度
                return new TanimotoCoefficientSimilarity(dataModel);
            case LOGLIKELIHOOD:
                //对数似然相似度
                return new LogLikelihoodSimilarity(dataModel);
            case SPEARMAN:
                //Spearman秩相关系数相似度
                return new SpearmanCorrelationSimilarity(dataModel);
            case CITYBLOCK:
                //曼哈顿距离相似度
                return new CityBlockSimilarity(dataModel);
            case EUCLIDEAN:
            default:
                //欧几里得相似度
                return new EuclideanDistanceSimilarity(dataModel);
        }
    }

    public static ItemSimilarity itemSimilarity(SimilarirtEnum type, DataModel dataModel) throws TasteException {
        switch (type) {
            case PEARSON:
                return new PearsonCorrelationSimilarity(dataModel);
            case COSINE:
                return new UncenteredCosineSimilarity(dataModel);
            case TANIMOTO:
                return new TanimotoCoefficientSimilarity(dataModel);
            case LOGLIKELIHOOD:
                return new LogLikelihoodSimilarity(dataModel);
            case CITYBLOCK:
                return new CityBlockSimilarity(dataModel);
            case EUCLIDEAN:
            default:
                return new EuclideanDistanceSimilarity(dataModel);
        }
    }

    public static UserNeighborhood userNeighborhood(NeighborHoodEnum type,UserSimilarity userSimilarity,DataModel dataModel,double num) throws TasteException {
        switch (type) {
            case NEAREST:
                return new NearestNUserNeighborhood((int) num,userSimilarity,dataModel);
            case THRESHOLD:
            default:
                return new ThresholdUserNeighborhood(num,userSimilarity,dataModel);
        }
    }

    //pref是否有比分
    public static RecommenderBuilder userRecommender(final UserSimilarity userSimilarity,final UserNeighborhood userNeighborhood,boolean pref) {
        if (pref) {
            return new RecommenderBuilder(){
                public Recommender buildRecommender(DataModel model) {
                    return new GenericUserBasedRecommender(model,userNeighborhood,userSimilarity);
                }
            };
        }else{
            return new RecommenderBuilder(){
                public Recommender buildRecommender(DataModel model) {
                    return new GenericBooleanPrefUserBasedRecommender(model,userNeighborhood,userSimilarity);
                }
            };
        }
    }

    public static RecommenderBuilder itemRecommender(final ItemSimilarity itemSimilarity,boolean pref) {
        if (pref) {
            return new RecommenderBuilder(){
                public Recommender buildRecommender(DataModel model) {
                    return new GenericItemBasedRecommender(model,itemSimilarity);
                }
            };
        }else{
            return new RecommenderBuilder(){
                public Recommender buildRecommender(DataModel model) {
                    return new GenericBooleanPrefItemBasedRecommender(model,itemSimilarity);
                }
            };
        }
    }

    public static RecommenderBuilder svdRecommender(final Factorizer factorizer) throws TasteException {
        return new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                return new SVDRecommender(dataModel, factorizer);
            }
        };
    }

//    public static RecommenderBuilder slopeOneRecommender() throws TasteException {
//        return new RecommenderBuilder() {
//            @Override
//            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
//                return new SlopeOneRecommender(dataModel);
//            }
//        };
//    }
//public static RecommenderBuilder itemKNNRecommender(final ItemSimilarity is, final Optimizer op, final int n) throws TasteException {
//    return new RecommenderBuilder() {
//        @Override
//        public Recommender buildRecommender(DataModel dataModel) throws TasteException {
//            return new KnnItemBasedRecommender(dataModel, is, op, n);
//        }
//    };
//}

//    public static RecommenderBuilder treeClusterRecommender(final ClusterSimilarity cs, final int n) throws TasteException {
//        return new RecommenderBuilder() {
//            @Override
//            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
//                return new TreeClusteringRecommender(dataModel, cs, n);
//            }
//        };
//    }

    //评论推荐器
    public static RecommenderEvaluator buildEvaluator(EvaluatorEnum type) {
        switch (type) {
            case RMS:
                return new RMSRecommenderEvaluator();
            case AVERAGE_ABSOLUTE_DIFFERENCE:
            default:
                return new AverageAbsoluteDifferenceRecommenderEvaluator();
        }
    }

    public static void evaluate(EvaluatorEnum type,RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("%s Evaluator Score:%s\n",type.toString(),buildEvaluator(type).evaluate(rb,mb,dm,trainPt,1.0));
    }

    public static void evaluate(RecommenderEvaluator re, RecommenderBuilder rb, DataModelBuilder mb, DataModel dm, double trainPt) throws TasteException {
        System.out.printf("Evaluater Score:%s\n", re.evaluate(rb, mb, dm, trainPt, 1.0));
    }

    public static void statsEvaluator(RecommenderBuilder rb,DataModelBuilder mb, DataModel m, int topn) throws TasteException {
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = evaluator.evaluate(rb, mb, m, null, topn, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.printf("Recommender IR Evaluator: [Precision:%s,Recall:%s]\n", stats.getPrecision(), stats.getRecall());
    }
}
