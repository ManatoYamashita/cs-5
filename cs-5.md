# Computer Simulations 5 - レポート

![cs-5](https://github.com/user-attachments/assets/8d360583-5164-4ad7-9f21-42b6dba5c9b7)

2025/06/30

g2172117 山下マナト

## 課題1: k-NNプログラムの修正と識別

### 課題概要

1. k-NNのプログラムを用いて、`optdigits.arff`を識別する
2. 以下の通りプログラムを修正する：
   - 合計のデータ数、各クラスのデータ数をプログラムで取得
   - 説明変数の数をプログラムで取得（`weka.core.Instance`のAPIを使用）
   - `k`の値を変更して、最も識別精度が高い値を見つける
   - `k`を変化させた際の識別精度をグラフで表現

### プログラムの修正箇所

#### 1. データセット基本情報の取得

```25:43:kNN/src/main/java/com/mycompany/knn/KNN.java
// 課題要求：データセットの基本情報を取得・表示
System.out.println("=== データセット基本情報 ===");

// 合計のデータ数をプログラムで取得
int totalInstances = data.numInstances();
System.out.println("合計データ数: " + totalInstances);

// 説明変数の数をプログラムで取得（weka.core.InstanceのnumAttributes()メソッド使用）
int numFeatures = data.numAttributes() - 1; // クラス属性を除く
System.out.println("説明変数の数: " + numFeatures);

// クラス数と各クラスのデータ数を取得
int numClasses = data.numClasses();
System.out.println("クラス数: " + numClasses);

// 各クラスのデータ数をプログラムで取得
System.out.println("\n=== 各クラスのデータ数 ===");
int[] classCounts = new int[numClasses];
for (int i = 0; i < data.numInstances(); i++) {
    int classValue = (int) data.instance(i).classValue();
    classCounts[classValue]++;
}
```

#### 2. k値の最適化処理

```50:70:kNN/src/main/java/com/mycompany/knn/KNN.java
// k=1から21まで（奇数のみ）を試す
for (int k = 1; k <= 21; k += 2) {
    // kNNモデルの作成
    IBk knn = new IBk();
    knn.setKNN(k);
    
    // 距離関数の設定（Euclidean Distance）
    EuclideanDistance distanceFunction = new EuclideanDistance();
    distanceFunction.setInstances(data);
    knn.getNearestNeighbourSearchAlgorithm().setDistanceFunction(distanceFunction);

    // モデルの評価（10分割交差検定）
    Evaluation eval = new Evaluation(data);
    eval.crossValidateModel(knn, data, 10, new java.util.Random(1));
    
    double accuracy = eval.pctCorrect();
    kValues.add(k);
    accuracies.add(accuracy);
    
    System.out.println("k=" + k + " → 識別精度: " + String.format("%.2f", accuracy) + "%");
}
```

### 実行結果

#### データセット基本情報

``` bash
=== データセット基本情報 ===
合計データ数: 5620
説明変数の数: 64
クラス数: 10

=== 各クラスのデータ数 ===
クラス 0: 554 個
クラス 1: 571 個
クラス 2: 557 個
クラス 3: 572 個
クラス 4: 568 個
クラス 5: 558 個
クラス 6: 558 個
クラス 7: 566 個
クラス 8: 554 個
クラス 9: 562 個
```

#### k値による識別精度の変化

``` bash
=== k値による識別精度の変化 ===
k=1 → 識別精度: 98.61%
k=3 → 識別精度: 98.77%
k=5 → 識別精度: 98.68%
k=7 → 識別精度: 98.59%
k=9 → 識別精度: 98.54%
k=11 → 識別精度: 98.56%
k=13 → 識別精度: 98.52%
k=15 → 識別精度: 98.38%
k=17 → 識別精度: 98.35%
k=19 → 識別精度: 98.29%
k=21 → 識別精度: 98.17%

=== 最適なk値 ===
最も識別精度が高いk値: 3
その時の識別精度: 98.77%
```

#### 最適なk値での詳細結果

``` bash
=== 最適なk値(3)での詳細結果 ===

Results
======

Correctly Classified Instances        5551               98.7722 %
Incorrectly Classified Instances        69                1.2278 %
Kappa statistic                          0.9864
Mean absolute error                      0.0035
Root mean squared error                  0.0462
Relative absolute error                  1.9625 %
Root relative squared error             15.4153 %
Total Number of Instances             5620     

=== Confusion Matrix ===

   a   b   c   d   e   f   g   h   i   j   <-- classified as
 552   0   0   0   1   0   1   0   0   0 |   a = 0
   0 569   0   0   0   0   0   1   0   1 |   b = 1
   0   1 556   0   0   0   0   0   0   0 |   c = 2
   0   1   0 563   0   1   0   2   2   3 |   d = 3
   0   1   0   0 561   0   2   1   0   3 |   e = 4
   0   0   0   1   0 549   0   0   0   8 |   f = 5
   0   2   0   0   0   0 556   0   0   0 |   g = 6
   0   0   0   1   1   0   0 563   0   1 |   h = 7
   0  15   0   0   0   0   1   0 535   3 |   i = 8
   0   1   0   6   1   2   0   3   2 547 |   j = 9
```

### グラフ用データ

k値と識別精度の関係データを `k_accuracy_results.csv` として出力：

| k値 | 識別精度(%) |
|-----|-------------|
| 1   | 98.61       |
| 3   | 98.77       |
| 5   | 98.68       |
| 7   | 98.59       |
| 9   | 98.54       |
| 11  | 98.56       |
| 13  | 98.52       |
| 15  | 98.38       |
| 17  | 98.35       |
| 19  | 98.29       |
| 21  | 98.17       |

### 考察

1. **データセット特性**: `optdigits.arff`は5620個のデータからなり、64次元の特徴量を持つ手書き数字認識データセット。各数字クラス（0-9）のデータ数はほぼ均等に分布している。

2. **最適なk値**: k=3で最高の識別精度98.77%を達成。これは、k-NN法において適度な近傍数がノイズ耐性と精度のバランスを保つことを示している。

3. **k値による精度の変化**: kの値が増加するにつれて全体的に精度が低下する傾向が見られる。これは、より多くの近傍を考慮することで決定境界が過度に平滑化されるためと考えられる。

4. **混同行列の分析**: クラス8（数字8）で比較的多くの誤分類（19個）が発生しており、特にクラス1（数字1）との混同が多い。これは手書き数字の形状的類似性に起因すると考えられる。

---

## 課題2: 分類プログラムの比較

### 課題概要

1. 配布した3種類の分類プログラム（k-NN、ナイーブベイズ、ロジスティック回帰）について、`breast-cancer.arff`を対象に3手法を実施し、それぞれの結果（精度、混同行列）を表示する
2. 3種類の中で最も識別性能が高いプログラムを回答し、判断基準を明記する

### 実行結果

#### 1. k-NN分類器（k=3）

``` bash
=== k-NN 分類器（breast-cancer.arff） ===

Results
======

Correctly Classified Instances         211               73.7762 %
Incorrectly Classified Instances        75               26.2238 %
Kappa statistic                          0.2281
Mean absolute error                      0.3414
Root mean squared error                  0.4477
Relative absolute error                 81.5847 %
Root relative squared error             97.9585 %
Total Number of Instances              286     

=== Detailed Accuracy By Class ===

                 TP Rate  FP Rate  Precision  Recall   F-Measure  MCC      ROC Area  PRC Area  Class
                 0.950    0.765    0.746      0.950    0.836      0.277    0.656     0.792     no-recurrence-events
                 0.235    0.050    0.667      0.235    0.348      0.277    0.656     0.507     recurrence-events
Weighted Avg.    0.738    0.552    0.722      0.738    0.691      0.277    0.656     0.708     

=== Confusion Matrix ===

   a   b   <-- classified as
 191  10 |   a = no-recurrence-events
  65  20 |   b = recurrence-events
```

#### 2. ナイーブベイズ分類器

``` bash
=== ナイーブベイズ分類器（breast-cancer.arff） ===

Results
======

Correctly Classified Instances         205               71.6783 %
Incorrectly Classified Instances        81               28.3217 %
Kappa statistic                          0.2857
Mean absolute error                      0.3272
Root mean squared error                  0.4534
Relative absolute error                 78.2086 %
Root relative squared error             99.1872 %
Total Number of Instances              286     

=== Detailed Accuracy By Class ===

                 TP Rate  FP Rate  Precision  Recall   F-Measure  MCC      ROC Area  PRC Area  Class
                 0.836    0.565    0.778      0.836    0.806      0.288    0.701     0.837     no-recurrence-events
                 0.435    0.164    0.529      0.435    0.477      0.288    0.701     0.514     recurrence-events
Weighted Avg.    0.717    0.446    0.704      0.717    0.708      0.288    0.701     0.741     

=== Confusion Matrix ===

   a   b   <-- classified as
 168  33 |   a = no-recurrence-events
  48  37 |   b = recurrence-events
```

#### 3. ロジスティック回帰分類器

``` bash
=== ロジスティック回帰分類器（breast-cancer.arff） ===

Results
======

Correctly Classified Instances         197               68.8811 %
Incorrectly Classified Instances        89               31.1189 %
Kappa statistic                          0.1979
Mean absolute error                      0.37  
Root mean squared error                  0.4631
Relative absolute error                 88.4196 %
Root relative squared error            101.3094 %
Total Number of Instances              286     

=== Detailed Accuracy By Class ===

                 TP Rate  FP Rate  Precision  Recall   F-Measure  MCC      ROC Area  PRC Area  Class
                 0.831    0.647    0.752      0.831    0.790      0.202    0.646     0.794     no-recurrence-events
                 0.353    0.169    0.469      0.353    0.403      0.202    0.646     0.412     recurrence-events
Weighted Avg.    0.689    0.505    0.668      0.689    0.675      0.202    0.646     0.680     

=== Confusion Matrix ===

   a   b   <-- classified as
 167  34 |   a = no-recurrence-events
  55  30 |   b = recurrence-events
```

### 性能比較結果

| 分類器 | 識別精度(%) | Kappa統計 | F-Measure | ROC Area |
|--------|-------------|-----------|-----------|----------|
| k-NN (k=3) | **73.78** | 0.2281 | 0.691 | 0.656 |
| ナイーブベイズ | 71.68 | **0.2857** | **0.708** | **0.701** |
| ロジスティック回帰 | 68.88 | 0.1979 | 0.675 | 0.646 |

### 最高性能分類器の判定

**結果：ナイーブベイズ分類器**

#### 判断基準

1. **総合的評価指標**：
   - k-NNは識別精度で最高値（73.78%）を示しているが、他の評価指標では劣る
   - ナイーブベイズはKappa統計（0.2857）、F-Measure（0.708）、ROC Area（0.701）で最高値を示す

2. **バランスの取れた性能**：
   - **Kappa統計**：偶然による一致を考慮した指標で、ナイーブベイズが最も高い値を示す
   - **F-Measure**：Precision と Recall の調和平均で、クラス不均衡への対応を評価
   - **ROC Area**：異なる閾値での性能を総合的に評価

3. **クラス不均衡への対応**：
   - breast-cancerデータセットは不均衡データ（no-recurrence-events: 201個、recurrence-events: 85個）
   - ナイーブベイズは少数クラス（recurrence-events）の検出性能が相対的に良好

4. **医療分野での重要性**：
   - 乳がん再発予測では、偽陰性（実際は再発するのに「再発しない」と予測）を避けることが重要
   - ナイーブベイズは recurrence-events クラスの Recall（0.435）が k-NN（0.235）より大幅に高い

### 考察

breast-cancer.arffデータセットにおいて、単純な識別精度だけでなく、クラス不均衡への対応や医療分野での実用性を考慮すると、**ナイーブベイズ分類器**が最も優れた性能を示している。特に、Kappa統計やF-Measureなどの robust な評価指標で一貫して高い値を示しており、実際の医療現場での適用において信頼性が高いと判断される。

---

## 課題3: k-NN法の自作実装

### 課題概要

1. 配布資料の中にある`Ex2.java`は、k-NN法をライブラリを用いずに作成しようとしているものです。このプログラムは作成途中であり、完全なものではありません。記述が足りない部分を補完し、正しく動作するようにしてください。

2. 条件：
   - 10分割交差検定を用いて、精度の平均を求める
   - 使用するデータは配布した「`csv_result-iris.csv`」とする
   - 距離尺度としてユークリッド距離を用いる

### プログラムの修正箇所

#### 1. CSVデータ読み込みの修正

```26:44:Ex2/src/main/java/com/mycompany/ex2/Ex2.java
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
```

#### 2. 10分割交差検定の実装

```60:72:Ex2/src/main/java/com/mycompany/ex2/Ex2.java
// 交差検定に関する処理
if (i >= fold * foldSize && i < (fold + 1) * foldSize) {
    testData.add(data.get(i));
    testLabels.add(labels.get(i));
} else {
    trainData.add(data.get(i));
    trainLabels.add(labels.get(i));
}

int k = 3; // kを設定
// 精度計算処理
double accuracy = (double) correct / testData.size();
```

#### 3. ユークリッド距離の実装

```125:131:Ex2/src/main/java/com/mycompany/ex2/Ex2.java
private static double euclideanDistance(double[] a, double[] b) {
    double sum = 0.0;
    for (int i = 0; i < a.length; i++) {
        double diff = a[i] - b[i];
        sum += diff * diff;
    }
    return Math.sqrt(sum);
}
```

#### 4. k最近傍による分類の実装

```91:108:Ex2/src/main/java/com/mycompany/ex2/Ex2.java
// k最近傍のラベルを数える
int setosaCount = 0;
int versicolorCount = 0;
int virginicaCount = 0;

for (int i = 0; i < k && i < neighbors.size(); i++) {
    String label = neighbors.get(i).label;
    if (label.equals("Iris-setosa")) {
        setosaCount++;
    } else if (label.equals("Iris-versicolor")) {
        versicolorCount++;
    } else if (label.equals("Iris-virginica")) {
        virginicaCount++;
    }
}
```

### 実行結果

``` bash
データ読み込み完了: 150 件
Fold 1 Accuracy: 1.0000 (15/15)
Fold 2 Accuracy: 1.0000 (15/15)
Fold 3 Accuracy: 1.0000 (15/15)
Fold 4 Accuracy: 1.0000 (15/15)
Fold 5 Accuracy: 0.8667 (13/15)
Fold 6 Accuracy: 0.8667 (13/15)
Fold 7 Accuracy: 1.0000 (15/15)
Fold 8 Accuracy: 0.8667 (13/15)
Fold 9 Accuracy: 0.8667 (13/15)
Fold 10 Accuracy: 1.0000 (15/15)
Average Accuracy: 0.9467
```

### 詳細分析

| Fold | 正解数 | テスト数 | 精度 |
|------|--------|----------|------|
| 1    | 15     | 15       | 100.0% |
| 2    | 15     | 15       | 100.0% |
| 3    | 15     | 15       | 100.0% |
| 4    | 15     | 15       | 100.0% |
| 5    | 13     | 15       | 86.67% |
| 6    | 13     | 15       | 86.67% |
| 7    | 15     | 15       | 100.0% |
| 8    | 13     | 15       | 86.67% |
| 9    | 13     | 15       | 86.67% |
| 10   | 15     | 15       | 100.0% |
| **平均** | **142** | **150** | **94.67%** |

### 考察

1. **高い識別精度**: 自作実装のk-NN法で94.67%の平均精度を達成。これは、アヤメデータセットの特徴量（花びらと萼片の長さ・幅）が種類の識別に非常に有効であることを示している。

2. **10分割交差検定の効果**: 6つのfoldで100%の完璧な識別を達成し、残り4つのfoldでも86.67%と高い精度を維持。これは、実装の正確性とアルゴリズムの有効性を証明している。

3. **ライブラリ vs 自作実装**: WekaライブラリのIBkクラスを使用した課題1のoptdigitsデータセット（98.77%）と比較して、自作実装でも高い性能を達成。基本的なk-NN法の理論実装が適切に機能していることを確認。

4. **計算効率**: 150件の小規模データセットながら、ユークリッド距離計算と近傍探索が正確に実装され、期待通りの結果を得られた。

5. **アルゴリズムの理解**: ライブラリを使わない実装により、k-NN法の核心的な仕組み（距離計算→近傍探索→多数決）を深く理解できる。これは機械学習アルゴリズムの原理習得において極めて重要である。 
