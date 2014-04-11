package com.happytarn.coatingObsidian;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.happytarn.coatingObsidian.command.CoatingObsidianCommand;
import com.happytarn.coatingObsidian.listener.BlockListener;
import com.happytarn.coatingObsidian.listener.PlayerListener;
import com.happytarn.coatingObsidian.util.ConfigurationManager;
import com.happytarn.coatingObsidian.util.FileDirectoryStructure;

public class Main extends JavaPlugin {

	public final static Logger log = Logger.getLogger("Minecraft");
	private static ConfigurationManager config;
	private static Main instance;

	public void onEnable(){
		instance = this;

		//設定ファイル読込
		loadConfigFile();

		//イベントリスナー登録
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(log), this);
		pm.registerEvents(new PlayerListener(log), this);

		//コマンド登録
		getServer().getPluginCommand("coatingobsidian").setExecutor(new CoatingObsidianCommand(this));

		if (!CoatingObsidian.reloadData()){
			log.warning("an error occured while trying to load the coatingObsidian data.");
		}

	}

	public void onDisable(){
		if (!CoatingObsidian.saveData()){
			log.warning("an error occured while trying to save the coatingObsidian data.");
		}
	}

	/**
	 * 設定ファイルを読み込む
	 */
	public void loadConfigFile(){
		// ファイルマネージャセットアップ
		FileDirectoryStructure.setup();

		// 設定ファイルパスを取得
		String filepath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
		File file = new File(filepath);

		// 設定ファイルが見つからなければデフォルトのファイルをコピー
		if (!file.exists()){
			this.saveDefaultConfig();
			log.info("config.yml is not found! Created default config.yml!");
		}

		// 設定ファイルを読み込む
		config = new ConfigurationManager();
		try{
			config.load(this);
		}catch(Exception ex){
			log.warning("an error occured while trying to load the config file.");
			ex.printStackTrace();
		}
	}

	/**
	 * 設定マネージャを返す
	 * @return ConfigurationManager
	 */
	public static ConfigurationManager getHappyTarnConfig(){
		return config;
	}

	public static Main getInstance() {
    	return instance;
    }




}
