package com.happytarn.coatingObsidian.command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.happytarn.coatingObsidian.CoatingObsidian;
import com.happytarn.coatingObsidian.Main;

public class CoatingObsidianCommand implements CommandExecutor {

	public final Logger log = Main.log;
	private Main plugin;

	/**
	 * デフォルトコンストラクタ
	 * @param plugin
	 */
	public CoatingObsidianCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

		if(commandLabel.equalsIgnoreCase("coatingobsidian") || commandLabel.equalsIgnoreCase("co")){
			//引数なし
			if(args.length == 0){
				if(sender instanceof Player){
					Player player = (Player) sender;
					//権限チェック
					if(player.hasPermission("hapitanCmd.coatingobsidian.admin")){
						player.sendMessage("引数無しでは実行できません。/co help で確認してください。");
						return true;
					}else{
						player.sendMessage("権限がありません。hapitanCmd.coatingobsidian.admin");
						return true;
					}
				}else{
					sender.sendMessage("引数無しでは実行できません。/co help で確認してください。");
					return true;
				}
			}

			if(args.length >= 1 && (args[0].equalsIgnoreCase("on"))){
				if(sender instanceof Player){
					CoatingObsidian.putOnMap(sender.getName());
					sender.sendMessage("黒曜石コーティングが有効になりました。");
					return true;
				}
			}

			if(args.length >= 1 && (args[0].equalsIgnoreCase("off"))){
				if(sender instanceof Player){
					CoatingObsidian.removeOnMap(sender.getName());
					sender.sendMessage("黒曜石コーティングが無効になりました。");
					return true;
				}
			}


			// coatingObsidian reload
			if(args.length >= 1 && (args[0].equalsIgnoreCase("reload"))){
				if(sender instanceof Player){
					Player player = (Player) sender;
					//権限チェック
					if(!player.hasPermission("hapitanCmd.coatingobsidian.admin")){
						player.sendMessage("権限がありません。hapitanCmd.coatingobsidian.admin");
						return true;
					}
					plugin.loadConfigFile();
					sender.sendMessage(new StringBuffer().append(ChatColor.GOLD).append("コーティング可能アイテム：" + Main.getHappyTarnConfig().getCoatingItem()).toString());
					sender.sendMessage(new StringBuffer().append(ChatColor.GOLD).append("リロード完了！").toString());
					return true;
				}else{
					sender.sendMessage("引数無しでは実行できません。/co help で確認してください。");
					return true;
				}
			}


			if(args.length >= 1 && (args[0].equalsIgnoreCase("save"))){
				if(sender instanceof Player){
					Player player = (Player) sender;
					//権限チェック
					if(!player.hasPermission("hapitanCmd.coatingobsidian.admin")){
						player.sendMessage("権限がありません。hapitanCmd.coatingobsidian.admin");
						return true;
					}
					CoatingObsidian.saveData();
					sender.sendMessage(new StringBuffer().append(ChatColor.GOLD).append("セーブ完了！").toString());
					return true;
				}else{
					sender.sendMessage("引数無しでは実行できません。/co help で確認してください。");
					return true;
				}
			}

			// coatingObsidian help
			if (args.length >= 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h"))){
				sender.sendMessage("作者に聞いてください");
				return true;
			}
		}
		return false;
	}



}
