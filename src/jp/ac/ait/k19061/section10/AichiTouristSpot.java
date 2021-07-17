package jp.ac.ait.k19061.section10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AichiTouristSpot {
    public static void main(String[] args) {

        List<Spot> data = new ArrayList<>();     // ファイルに書き込むデータ
        List<String> files = new ArrayList<>();  // 入力ファイルのリスト
        files.add("c1000326.csv");
        files.add("c1000328.csv");
        files.add("c1000329.csv");
        files.add("c1000330.csv");
        Map<String, Integer> nameIndex = new HashMap<>(); // キー:ファイル名,値:そのファイルにおいて、データ名が格納されているインデックス
        nameIndex.put(files.get(0), 2);
        nameIndex.put(files.get(1), 1);
        nameIndex.put(files.get(2), 2);
        nameIndex.put(files.get(3), 2);

        // 1.4つのファイルから読み込み
        for (int i = 0;i < 4;i++) {
            try (Scanner sc = new Scanner(Files.newBufferedReader(Path.of(files.get(i)),
                    Charset.forName("Shift-JIS")))) {
                // 1-1.1行目と2行目は読み飛ばす
                sc.nextLine();
                sc.nextLine();

                // 1-2.1行ずつファイル読み込み
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    List<String> csvData = new ArrayList<>(Arrays.asList(line.split(",", -1)));  // csvデータ1行のそれぞれの項目を持ったリスト

                    // 緯度、経度を取得
                    double lat = 0, lon = 0;
                    String pointData[] = csvData.get(0).split(" ");
                    try {
                        lon = Double.parseDouble(pointData[0].replace("\"POINT(", ""));
                        lat = Double.parseDouble(pointData[1].replace(")\"", ""));
                    } catch (NullPointerException | NumberFormatException ex) {
                        System.out.println("浮動小数点数変換時に例外が発生しました");
                        System.exit(-1);
                    }

                    // 1件分のデータを生成
                    Spot s = new Spot(lat, lon, csvData.get(nameIndex.get(files.get(i))));

                    // データを追加
                    data.add(s);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        // 2.データを並び替える
        Collections.sort(data, Comparator.comparingDouble(Spot::getDistFromAIT));
        
        // 3.データをファイルに書き込む
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("TouristSpot.csv"),
                Charset.forName("UTF-8"))) {
            for (ListIterator<Spot> itr = data.listIterator();itr.hasNext();) {
                String csvLine = itr.next().getCSVString();

                bw.write(csvLine);
                bw.newLine();
                System.out.println(csvLine);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
