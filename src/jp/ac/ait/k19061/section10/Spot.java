package jp.ac.ait.k19061.section10;

public class Spot {
    /*---フィールド---*/
    /**
     * 緯度
     */
    private double lat;

    /**
     * 経度
     */
    private double lon;

    /**
     * 愛工大からのユークリッド距離
     */
    private double distFromAIT;

    /**
     * その施設を表す文字列
     */
    private String name;

    /*---コンストラクタ---*/
    private Spot() {}   // 引数なしコンストラクタは外部から呼び出せないようにする

    /**
     *
     * @param lat 緯度
     * @param lon 経度
     * @param name 施設を表す文字列
     */
    public Spot(double lat, double lon, String name) {
        this.lat = lat;
        this.lon = lon;
        this.distFromAIT = calcDistFromAIT(this.lat, this.lon);
        this.name = name;
    }

    /*---メソッド---*/
    // アクセサ
    public double getDistFromAIT() {
        return distFromAIT;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    /**
     * SpotのインスタンスをCSV文字列で返す
     */
    public String getCSVString() {
        return String.format("%.10f,%.10f,%.10f,%s", lat, lon, distFromAIT, name);
    }

    /**
     * 愛工大からのユークリッド距離を求める
     * @param lat 緯度
     * @param lon 経度
     */
    private double calcDistFromAIT(double lat, double lon) {
        return Math.sqrt(Math.pow(35.1834122 - lat, 2.0) + Math.pow(137.1130419 - lon, 2.0));
    }
}
