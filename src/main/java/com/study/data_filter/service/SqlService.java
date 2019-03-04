package com.study.data_filter.service;

import com.study.data_filter.dao.SqlDao;
import com.study.data_filter.model.User;
import com.study.data_filter.model.UserMovieScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SqlService {

    @Autowired
    private SqlDao sqlDao;

    public List<User> getUserList() {
        return sqlDao.getUserList();
    }

    public List<UserMovieScore> getBaseList() {
        return sqlDao.getBaseList();
    }

    public List<UserMovieScore> getTestList() {
        return sqlDao.getTestList();
    }

    // 计算用户之间之间的相似度
    public double[][] calculateSimMatrix() {

        // 查询base数据集的信息
        List<UserMovieScore> userBaseList = getBaseList();

        // 进行分组操作
        Map<String, List<UserMovieScore>> userBaseGroup =
                        userBaseList.stream().collect(Collectors.groupingBy(UserMovieScore::getUserId));

        //定义相似度计算结果存放位置
        double[][] similarityMatrix = new double[943][943];

        // 934个用户内外循环，计算相互之间的相似度
        for (int i = 0; i < 943; i++) {
            for (int j = 0; j < 943; j++) {
                // 同一个用户，相似度为1
                if (i == j) {
                    similarityMatrix[i][j] = 1;
                }

                // 不同用户进行相似度的计算
                else {
                    List<UserMovieScore> user_i_base = userBaseGroup.get(i + 1 + "");
                    List<UserMovieScore> user_j_base = userBaseGroup.get(j + 1 + "");

                    Map<String, List<UserMovieScore>> i_moviesGroup =
                                    user_i_base.stream().collect(Collectors.groupingBy(UserMovieScore::getMovieId));
                    Map<String, List<UserMovieScore>> j_moviesGroup =
                                    user_j_base.stream().collect(Collectors.groupingBy(UserMovieScore::getMovieId));

                    // 循环项目个数，查询啊i和j之间所有的公共项目评分结果并存储
                    List<Integer> list_i = new ArrayList<>();
                    List<Integer> list_j = new ArrayList<>();
                    for (int k = 0; k < 1682; k++) {
                        UserMovieScore userMovieScore_i = i_moviesGroup.get(k + 1 + "") == null ? null : i_moviesGroup.get(k + 1 + "").get(0);
                        UserMovieScore userMovieScore_j = j_moviesGroup.get(k + 1 + "") == null ? null : j_moviesGroup.get(k + 1 + "").get(0);
                        if (i_moviesGroup.get(k + 1 + "") != null && j_moviesGroup.get(k + 1 + "") != null) {
                            assert userMovieScore_i != null;
                            list_i.add(userMovieScore_i.getScore());
                            assert userMovieScore_j != null;
                            list_j.add(userMovieScore_j.getScore());
                        }
                    }

                    // 参照用户i的长度进行计算
                    int num = list_i.size();
                    int sum_prefOne = 0;
                    int sum_prefTwo = 0;
                    int sum_squareOne = 0;
                    int sum_squareTwo = 0;
                    int sum_product = 0;
                    for (int m = 0; m < num; m++) {
                        sum_prefOne += list_i.get(m);//∑xi
                        sum_prefTwo += list_j.get(m);//∑yi
                        sum_squareOne += Math.pow(list_i.get(m), 2);//∑xi2
                        sum_squareTwo += Math.pow(list_j.get(m), 2);//∑yi2
                        sum_product += list_i.get(m) * list_j.get(m);//∑xiyi
                    }
                    double sum = num * sum_product - sum_prefOne * sum_prefTwo;//n∑xiyi-∑xi∑yi
                    double den = Math.sqrt((num * sum_squareOne - Math.pow(sum_squareOne, 2)) * (num * sum_squareTwo
                                    - Math.pow(sum_squareTwo, 2)));
                    //√((n∑xi-(∑xi2)2)*(n∑yi-(∑yi2)2))------这就是皮尔逊相关系数
                    double result;
                    if (den == 0)
                        result = 0;
                    else
                        result = sum / den;
                    //n∑xiyi-∑xi∑yi/√((n∑xi-(∑xi2)2)*(n∑yi-(∑yi2)2))
                    similarityMatrix[i][j] = result;
                }
            }
        }
        return similarityMatrix;
    }
}
