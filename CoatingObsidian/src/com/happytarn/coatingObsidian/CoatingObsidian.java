package com.happytarn.coatingObsidian;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.happytarn.coatingObsidian.util.ConfigurationManager;
import com.happytarn.coatingObsidian.util.TextFileHandler;

public class CoatingObsidian {


	// コーティング黒曜石の保存用ファイルパス
	private static final String coDataPath = "plugins/CoatingObsidian/list.ncsv";

	// コーティング黒曜石の座標とアイテム
	private static Map<Location, String> coMap = new HashMap<Location, String>();

	private static Map<String, Boolean> onMap = new HashMap<String, Boolean>();

	public CoatingObsidian() {
	}

	/**
	 * データリロード
	 * @return
	 */
	public static boolean reloadData(){
		TextFileHandler datafile = new TextFileHandler(coDataPath);
		coMap.clear(); // ハッシュマップをクリア
		try{
			List<String> list = datafile.readLines();
			String[] data;
			String[] coord;

			@SuppressWarnings("unused")
			int line = 0;
			// 行を格納したリストが空になるまで繰り返す
			while (!list.isEmpty()){
				line++; // ログ用のデータファイル行数
				String thisLine = list.remove(0); // リストの先頭にある要素を格納して削除

				// デリミタで配列に分ける
				data = thisLine.split("#");
				coord = data[0].split(",");

				// 行の形式がおかしい場合はその行をスキップ
				if (data.length != 2 || coord.length != 4){
					continue;
				}

				World world = Main.getInstance().getServer().getWorld(coord[0]);
				// ワールドが存在しなければその行をスキップ
				if (world == null){
					continue;
				}

				// ハッシュマップにコーティング黒曜石データを設置
				coMap.put(new Location(world, new Double(coord[1]), new Double(coord[2]), new Double(coord[3])), data[1]);
			}
		}catch (FileNotFoundException ex){
			return false;
		}catch (IOException ex){
			return false;
		}
		return true;
	}

	/**
	 * コーティング黒曜石のハッシュマップデータをファイルに保存する
	 * @return
	 */
	public static boolean saveData(){
		TextFileHandler datafile = new TextFileHandler(coDataPath);
		// 実際に書き込むデータリスト
		List<String> wdata = new ArrayList<String>();

		// ハニーチェストのハッシュマップをループで回す
		for (Entry<Location, String> hc : coMap.entrySet()){
			Location loc = hc.getKey();
			String items = hc.getValue();
			// 書き込むリストに追加
			wdata.add(loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "#" + items);
		}

		try{
			datafile.writeLines(wdata);
		}catch (IOException ex){
			return false;
		}
		return true;
	}

	public static boolean isCoating(Material material){
		ConfigurationManager config = Main.getHappyTarnConfig();
		String items[] = config.getCoatingItem().split(",");
		if(items != null && items.length > 0){
			for(String itemStr : items){
				if(material.getId() == Integer.parseInt(itemStr)){
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * 指定した座標のブロックがコーティング黒曜石か判定
	 * @param loc チェックする座標
	 * @return コーティング黒曜石なら対象アイテムID文字列、null なら通常のチェスト
	 */
	public static String getCo(Location loc){
		return coMap.get(loc);
	}

	/**
	 * コーティング黒曜石を作成
	 * @param loc 作成するチェストの座標
	 * @param items アイテムID文字列
	 */
	public static void setCo(Location loc, String items){
		if (getCo(loc) == null){
			coMap.put(loc, items);
		}
	}

	/**
	 * コーティング黒曜石を削除
	 * @param loc 削除するコーティング黒曜石の座標
	 */
	public static void removeCo(Location loc){
		if (getCo(loc) != null){
			coMap.remove(loc);
		}
	}

	public static void putOnMap(String name){
		if(!onMap.containsKey(name)){
			onMap.put(name, true);
		}
	}

	public static void removeOnMap(String name){
		if(onMap.containsKey(name)){
			onMap.remove(name);
		}
	}

	public static boolean isOn(String name){
		if (onMap.containsKey(name)){
			return true;
		}
		return false;
	}

}
