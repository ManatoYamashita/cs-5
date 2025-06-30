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
            // データセットの読み込み
            DataSource source = new DataSource("iris.arff");
            Instances data = source.getDataSet();
            
            // クラス属性の設定（ターゲット変数を設定）
            if (data.classIndex() == -1)
                data.setClassIndex(data.numAttributes() - 1);

            // ナイーブベイズモデルの作成
            NaiveBayes nb = new NaiveBayes();
            nb.buildClassifier(data);

            // モデルの評価
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(nb, data, 10, new java.util.Random(1));
            
            // 結果の表示
            //System.out.println(nb); //各クラスにおける情報が出力される．
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println(eval.toClassDetailsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
