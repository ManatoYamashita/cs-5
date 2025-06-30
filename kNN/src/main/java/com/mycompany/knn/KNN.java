/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.knn;


import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;
import weka.core.EuclideanDistance;



/**
 *
 * @author kmikawa
 */
public class KNN {

    public static void main(String[] args) {
            try {
            // データセットの読み込み（breast-cancer.arffを使用）
            DataSource source = new DataSource("breast-cancer.arff");
            Instances data = source.getDataSet();
            
            // クラス属性の設定（ターゲット変数を設定）
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            
            System.out.println("=== k-NN 分類器（breast-cancer.arff） ===");
            
            // k=3（最適値）を使用
            IBk knn = new IBk();
            knn.setKNN(3);
            
            // 距離関数の設定（Euclidean Distance）
            EuclideanDistance distanceFunction = new EuclideanDistance();
            distanceFunction.setInstances(data);
            knn.getNearestNeighbourSearchAlgorithm().setDistanceFunction(distanceFunction);

            // モデルの評価（10分割交差検定）
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(knn, data, 10, new java.util.Random(1));
            
            // 結果の表示
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println(eval.toClassDetailsString());
            System.out.println(eval.toMatrixString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
