/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.knn;


import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;
import weka.core.EuclideanDistance;
import weka.core.ManhattanDistance;



/**
 *
 * @author kmikawa
 */
public class KNN {

    public static void main(String[] args) {
            try {
            // データセットの読み込み
            DataSource source = new DataSource("breast-cancer.arff");
            Instances data = source.getDataSet();
            
            // クラス属性の設定（ターゲット変数を設定）
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);
            
                        int numClasses = data.numClasses();
            System.out.println("Number of classes: " + numClasses);

            // クラスの名前を取得して表示
            for (int i = 0; i < numClasses; i++) {
                String className = data.classAttribute().value(i);
                System.out.println("Class " + (i + 1) + ": " + className);
            }

            // kNNモデルの作成
            IBk knn = new IBk();
            knn.setKNN(5);  // kの値を設定（ここでは3）
            System.out.println("設定したkの値：" + knn.getKNN());
            
            // 距離関数の設定（例: Euclidean Distance）
            EuclideanDistance distanceFunction = new EuclideanDistance();
            // ManhattanDistance distanceFunction = new ManhattanDistance();
            distanceFunction.setInstances(data);
            knn.getNearestNeighbourSearchAlgorithm().setDistanceFunction(distanceFunction);

            // モデルの評価
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(knn, data, 10, new java.util.Random(1));
            
            // 結果の表示
            System.out.println(eval.toSummaryString("\nResults\n======\n", false)); // その他の結果を出力．
            System.out.println(eval.toClassDetailsString());
            System.out.println(eval.toMatrixString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
