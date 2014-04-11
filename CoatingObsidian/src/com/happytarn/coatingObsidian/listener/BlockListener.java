package com.happytarn.coatingObsidian.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import com.happytarn.coatingObsidian.CoatingObsidian;

public class BlockListener implements Listener {

	@SuppressWarnings("unused")
	private Logger log;

	public BlockListener(Logger log) {
		this.log = log;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(final BlockBreakEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();

		if (player != null) {
			ItemStack inHand = player.getItemInHand();
			if (inHand != null) {
				if (CoatingObsidian.getCo(block.getLocation()) != null) {
					if(!CoatingObsidian.isCoating(block.getType())){
						CoatingObsidian.removeCo(block.getLocation());
						return;
					}
					switch (inHand.getType()) {
					case DIAMOND_AXE:
					case DIAMOND_HOE:
					case DIAMOND_PICKAXE:
					case DIAMOND_SPADE:
					case DIAMOND_SWORD:
						CoatingObsidian.removeCo(block.getLocation());
						break;
					default:
						player.sendMessage("黒曜石でコーティングされているため破壊できません。");
						player.sendMessage("ダイヤツールでのみ破壊することが出来ます。");
						event.setCancelled(true);
					}
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplodeEvent(final EntityExplodeEvent event) {
		List<Block> exploded = new ArrayList<Block>();
		exploded.addAll(event.blockList());
		if (exploded != null && exploded.size() > 0) {
			for (int i = 0; i < exploded.size(); i++) {
				if (CoatingObsidian.getCo(exploded.get(i).getLocation()) != null) {
					if(!CoatingObsidian.isCoating(exploded.get(i).getType())){
						CoatingObsidian.removeCo(exploded.get(i).getLocation());
					}else{
						event.blockList().remove(exploded.get(i));
					}
				} else {
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPistonExtendEvent(final BlockPistonExtendEvent event){
		if(event.getBlocks() != null && event.getBlocks().size() > 0){
			for (Block block : event.getBlocks()){
				if(CoatingObsidian.getCo(block.getLocation()) != null){
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPistonRetractEvent(final BlockPistonRetractEvent event){
		if(CoatingObsidian.getCo(event.getRetractLocation()) != null){
			event.setCancelled(true);
		}
	}

}
