package com.happytarn.coatingObsidian.listener;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.happytarn.coatingObsidian.CoatingObsidian;
import com.happytarn.coatingObsidian.Main;

public class PlayerListener implements Listener{

	@SuppressWarnings("unused")
	private Logger log;

	public PlayerListener(Logger log) {
		this.log = log;
	}

	@EventHandler(priority = EventPriority.HIGH) // ignoreCancelled = true
    public void onPlayerLeftClickWithItem(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		final Block block = event.getClickedBlock();
		final int count = Main.getHappyTarnConfig().getNumberNecessaryForCoating();
		if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			if(player != null){

				if(CoatingObsidian.getCo(block.getLocation()) != null){
					if(!CoatingObsidian.isCoating(block.getType())){
						CoatingObsidian.removeCo(block.getLocation());
						return;
					}
					player.sendMessage("黒曜石でコーティングされています。");
				}else{
					if(CoatingObsidian.isOn(player.getName())){
						ItemStack inHand = player.getItemInHand();
						if(Material.OBSIDIAN.equals(inHand.getType())){
							if(inHand.getAmount() > count){
								if(CoatingObsidian.isCoating(block.getType())){
									inHand.setAmount(inHand.getAmount() - count);
									player.sendMessage("黒曜石でコーティングしました。[" + block.getX() + "," + block.getY() + "," + block.getZ() + "]");
									CoatingObsidian.setCo(block.getLocation(), block.getType().name());
								}else{
									player.sendMessage("このブロックはコーティングすることができません。");
								}
							}else{
								player.sendMessage("コーティングするための黒曜石が足りません。");
							}
						}
					}
				}
			}
		}
	}
}
