/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.nb_weka;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Evaluation;

/**
 *
 * @author kmikawa
 */
public class NB_weka {

    public static void main(String[] args) {
        try {
            // データセットの読み込み（breast-cancer.arffを使用）
            DataSource source = new DataSource("breast-cancer.arff");
            Instances data = source.getDataSet();
            
            // クラス属性の設定（ターゲット変数を設定）
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            System.out.println("=== ナイーブベイズ分類器（breast-cancer.arff） ===");

            // ナイーブベイズモデルの作成
            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(data);

            // モデルの評価（10分割交差検定）
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(nb, data, 10, new java.util.Random(1));
            
            // 結果の表示
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println(eval.toClassDetailsString());
            System.out.println(eval.toMatrixString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
