# Computer Simulations 6 - TCU Informatics
このプロジェクトは東京都市大学 メディア情報学部 情報システム学科のコンピュータシミュレーションの第六回の課題です。

## 課題内容

### [cite_start]1. k-NNプログラムの修正と識別 [cite: 65]

1. [cite_start]k-NNのプログラムを用いて、`optdigits.arff`を識別してください [cite: 65]。

2. [cite_start]以下の通りプログラムを修正してください [cite: 65]。
    * [cite_start]合計のデータ数、各クラスのデータ数をプログラムで取得してください [cite: 65]。
    * [cite_start]説明変数の数をプログラムで取得してください（`weka.core.Instance`のAPIページを読んでメソッドを調べてみましょう） [cite: 65]。
    * [cite_start]`k`の値を変更して、最も識別精度が高い値を見つけてください [cite: 65][cite_start]。`k`を変化させた際の識別精度をグラフで表現してください [cite: 65]。

### [cite_start]2. 分類プログラムの比較 [cite: 65]

1. [cite_start]配布した3種類の分類プログラム（k-NN、ナイーブベイズ、ロジスティック回帰）について、`breast-cancer.arff`を対象に3手法を実施し、それぞれの結果（精度、混同行列）を表示してください [cite: 65]。
2.  [cite_start]3種類の中で最も識別性能が高いプログラムを回答してください [cite: 65][cite_start]。また、どのような基準で識別性能が高いと判断したかも明記してください [cite: 65]。

### [cite_start]3. k-NN法の自作実装 [cite: 66]

1. [cite_start]配布資料の中にある`Ex2.java`は、k-NN法をライブラリを用いずに作成しようとしているものです [cite: 66][cite_start]。このプログラムは作成途中であり、完全なものではありません [cite: 66][cite_start]。記述が足りない部分を補完し、正しく動作するようにしてください [cite: 66]。

2. [cite_start]ただし、以下の条件を満たすこと [cite: 66]。
    * [cite_start]10分割交差検定を用いて、精度の平均を求めてください [cite: 66]。
    * [cite_start]使用するデータは配布した「`csv_result-iris.csv`」としてください [cite: 66]。
    * [cite_start]距離尺度としてユークリッド距離を用いてください [cite: 66]。

## ディレクトリ構成

``` bash
. ├── cs-6  # ルートディレクトリ
├── Ex2  # k-NN法の自作実装プロジェクト（課題3）
│   ├── csv_result-breast-cancer.csv  # 課題用データセット
│   ├── csv_result-ionosphere.csv     # 課題用データセット
│   ├── csv_result-iris.csv           # 課題3で使用するアヤメデータセット
│   ├── pom.xml                       # Maven設定ファイル
│   ├── src                           # ソースコードディレクトリ
│   │   └── main
│   │       └── java                  # Javaソースファイル
│   └── target                        # ビルド出力ディレクトリ
│       ├── classes                   # コンパイル済みクラスファイル
│       │   └── com
│       └── test-classes
├── LogReg  # ロジスティック回帰分類プロジェクト（課題2）
│   ├── breast-cancer.arff            # 乳がんデータセット（課題2メイン）
│   ├── ionosphere.arff               # 追加データセット
│   ├── iris.arff                     # アヤメデータセット
│   ├── pom.xml                       # Maven設定ファイル
│   ├── src                           # ソースコードディレクトリ
│   │   ├── main
│   │   │   └── java                  # Javaソースファイル
│   │   └── test
│   │       └── java                  # テストコード
│   └── target                        # ビルド出力ディレクトリ
│       ├── classes                   # コンパイル済みクラスファイル
│       │   └── com
│       └── test-classes
├── NB_weka  # ナイーブベイズ分類プロジェクト（課題2）
│   ├── iris.arff                     # アヤメデータセット
│   ├── pom.xml                       # Maven設定ファイル
│   ├── src                           # ソースコードディレクトリ
│   │   ├── main
│   │   │   └── java                  # Javaソースファイル
│   │   └── test
│   │       └── java                  # テストコード
│   └── target                        # ビルド出力ディレクトリ
│       ├── classes                   # コンパイル済みクラスファイル
│       │   └── com
│       └── test-classes
└── kNN  # k-NN分類プロジェクト（課題1,2）
    ├── breast-cancer.arff            # 乳がんデータセット
    ├── diabetes.arff                 # 糖尿病データセット
    ├── ionosphere.arff               # 電離層データセット
    ├── iris.arff                     # アヤメデータセット
    ├── optdigits.arff                # 手書き数字データセット（課題1メイン）
    ├── pom.xml                       # Maven設定ファイル
    ├── src                           # ソースコードディレクトリ
    │   ├── main
    │   │   └── java                  # Javaソースファイル
    │   └── test
    │       └── java                  # テストコード
    └── target                        # ビルド出力ディレクトリ
        ├── classes                   # コンパイル済みクラスファイル
        │   └── com
        └── test-classes
```