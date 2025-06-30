/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ex2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Ex2 {

    public static void main(String[] args) {
        // データセットの読み込み
        String csvFile = "csv_result-iris.csv"; // Irisデータセットのパスを指定
        List<double[]> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true; // ヘッダー行を読み飛ばすためのフラグ
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // ヘッダー行を読み飛ばす
                }
                
                // 空行や無効な行をスキップ
                if (line.trim().isEmpty() || line.startsWith("%")) {
                    continue;
                }
                
                String[] values = line.split(",");
                if (values.length < 6) {
                    continue; // 不完全な行をスキップ
                }
                
                // ID列（最初の列）を除外して特徴量を取得
                double[] features = new double[4]; // sepal_length, sepal_width, petal_length, petal_width
                for (int i = 1; i <= 4; i++) { // ID列（0）を除外してi=1から開始
                    features[i-1] = Double.parseDouble(values[i]);
                }
                data.add(features);
                labels.add(values[5]); // クラスラベル
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("データ読み込み完了: " + data.size() + " 件");
        
        int numFolds = 10;
        int foldSize = data.size() / numFolds;
        double totalAccuracy = 0.0;

        for (int fold = 0; fold < numFolds; fold++) {
            List<double[]> trainData = new ArrayList<>();
            List<String> trainLabels = new ArrayList<>();
            List<double[]> testData = new ArrayList<>();
            List<String> testLabels = new ArrayList<>();

            for (int i = 0; i < data.size(); i++) {
                // 交差検定に関する処理．どのような条件でテストデータと学習データを分けているか
                // 考えてみよう
                if (i >= fold * foldSize && i < (fold + 1) * foldSize) {
                    testData.add(data.get(i));
                    testLabels.add(labels.get(i));
                } else {
                    trainData.add(data.get(i));
                    trainLabels.add(labels.get(i));
                }
            }
            
            int k = 3; // kを設定．
            int correct = 0;
            for (int i = 0; i < testData.size(); i++) {
                String predictedLabel = classify(trainData, trainLabels, testData.get(i), k);
                //predictLablelで得られた結果がテストデータと等しければcorrectを加算する処理を追加
                if (predictedLabel.equals(testLabels.get(i))) {
                    correct++;
                }
            }
            // 精度を求めるための処理を追加
            double accuracy = (double) correct / testData.size();
            totalAccuracy += accuracy;
            System.out.println("Fold " + (fold + 1) + " Accuracy: " + String.format("%.4f", accuracy) 
                             + " (" + correct + "/" + testData.size() + ")");
        }

        double averageAccuracy = totalAccuracy / numFolds;    // 交差検定を通じた全体の平均値を算出
        System.out.println("Average Accuracy: " + String.format("%.4f", averageAccuracy));
    }

    private static String classify(List<double[]> trainData, List<String> trainLabels, double[] testInstance, int k) {
        List<Neighbor> neighbors = new ArrayList<>();
        for (int i = 0; i < trainData.size(); i++) {
            double distance = euclideanDistance(trainData.get(i), testInstance);
            neighbors.add(new Neighbor(distance, trainLabels.get(i)));
        }
        Collections.sort(neighbors);

        // k最近傍のラベルを数える
        int setosaCount = 0;
        int versicolorCount = 0;
        int virginicaCount = 0;
        
        for (int i = 0; i < k && i < neighbors.size(); i++) {
            String label = neighbors.get(i).label;
            // 以下，最近傍のラベル数を計算する処理を追記
            // ただし，ラベルの一致はlabel.equals("Iris-setosa")などを利用しよう． 
            if (label.equals("Iris-setosa")) {
                setosaCount++;
            } else if (label.equals("Iris-versicolor")) {
                versicolorCount++;
            } else if (label.equals("Iris-virginica")) {
                virginicaCount++;
            }
        }
        
        // 最も多いラベルを戻り値として返す処理
        if (setosaCount >= versicolorCount && setosaCount >= virginicaCount) {
            return "Iris-setosa";
        } else if (versicolorCount >= virginicaCount) {
            return "Iris-versicolor";
        } else {
            return "Iris-virginica";
        }
    }

    private static double euclideanDistance(double[] a, double[] b) {
        double sum = 0.0;
        // ユークリッド距離の計算
        for (int i = 0; i < a.length; i++) {
            double diff = a[i] - b[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    private static class Neighbor implements Comparable<Neighbor> {
        double distance;
        String label;

        Neighbor(double distance, String label) {
            this.distance = distance;
            this.label = label;
        }

        @Override
        public int compareTo(Neighbor other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
