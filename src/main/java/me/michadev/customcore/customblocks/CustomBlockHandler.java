package me.michadev.customcore.customblocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static me.michadev.customcore.CustomCore.blockHandler;
import static me.michadev.customcore.CustomCore.rpgBlockKey;
import static me.michadev.customcore.utils.Common.*;
import static me.michadev.customcore.utils.YamlSettings.getConfiguration;

public class CustomBlockHandler implements Listener {

    public Map<String, CustomBlock> blockRegistry;
    public Map<Location, CustomBlock> placedBlocks;

    public CustomBlockHandler() {
        blockRegistry = new HashMap<>();
        placedBlocks  = new HashMap<>();

        Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), ()-> placedBlocks.keySet().forEach(key-> {
            CustomBlock CustomBlock = placedBlocks.get(key);
            CustomBlock.onGameTicked(key.getBlock());
        }), 0, 20);
    }

    public void registerBlock(CustomBlock block) {
        if (!this.blockRegistry.containsKey(block.getIdentifier())) {
            this.blockRegistry.put(block.getIdentifier(), block);
        }
        else {
            Bukkit.getLogger().warning("(" + block.getIdentifier() + ")  Unable to register multiple of the same block type. Skipping...");
        }
    }


    public void loadPlacedBlocks() {
        placedBlocks.clear();
        Set<String> blockSections = getConfiguration(null, "machines.yml").getYaml().getKeys(false);

        for (String blockType : blockSections) {
            List<String> stringifiedLocations = getConfiguration(null, "machines.yml").getYaml().getStringList(blockType);

            for (String stringifiedLocation : stringifiedLocations) {
                if (!placedBlocks.containsKey(stringifiedLocation))
                    placedBlocks.put(locationifyString(stringifiedLocation), blockRegistry.get(blockType));
            }

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (placedBlocks.containsKey(location)) {
            CustomBlock CustomBlock = placedBlocks.get(location);

            event.setCancelled(true);
            block.setType(Material.AIR);
            location.getWorld().dropItemNaturally(location, CustomBlock.getItemStack());
            placedBlocks.remove(location);

            CustomBlock.onBlockBroken(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();

        /* Custom Placement Handler */
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getHand() == EquipmentSlot.HAND) {
            Block clickedBlock = event.getClickedBlock();

            if (placedBlocks.containsKey(clickedBlock.getLocation())) {
                placedBlocks.get(clickedBlock.getLocation()).onBlockInteract(player, clickedBlock, event);

                return;
            }

            if (isMachineItem(heldItem)) {
                CustomBlock CustomBlock = blockFromItem(heldItem);

                CustomBlock.place(player, heldItem, clickedBlock, event.getBlockFace());
                CustomBlock.onBlockPlaced(player, heldItem, event.getClickedBlock());
            }

        }

    }

    public final void saveMachineLocation(CustomBlock CustomBlock, Location location) {
        String stringifiedLocation = stringifyLocation(location);
        List<String> listOfLocations = getConfiguration(null, "machines.yml").getYaml().getStringList(CustomBlock.getIdentifier());
        if (!listOfLocations.contains(stringifiedLocation)) listOfLocations.add(stringifiedLocation);

        getConfiguration(null, "machines.yml").getYaml().set(CustomBlock.getIdentifier(), listOfLocations);
        getConfiguration(null, "machines.yml").save();
        getConfiguration(null, "machines.yml").reload();
    }


    public static boolean isMachineItem(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(rpgBlockKey, PersistentDataType.STRING);
    }


    public static CustomBlock blockFromItem(ItemStack itemStack) {
        return blockHandler.blockRegistry.get(itemStack.getItemMeta().getPersistentDataContainer().get(rpgBlockKey, PersistentDataType.STRING));
    }

}
