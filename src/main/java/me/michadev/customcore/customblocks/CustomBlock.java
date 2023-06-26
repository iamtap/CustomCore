package me.michadev.customcore.customblocks;

import me.michadev.customcore.CustomCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.michadev.customcore.utils.Common.colorize;

public abstract class CustomBlock {

    public final String getIdentifier() {
        return getClass().getSimpleName().toLowerCase();
    }

    public final ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(getItemMaterial(), 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CustomCore.rpgBlockKey, PersistentDataType.STRING, getIdentifier());

        itemMeta.setDisplayName(colorize(getName()));

        if (getItemLore() != null) {
            List<String> appliedLore = new ArrayList<>();
            for (String s : getItemLore()) appliedLore.add(colorize(s));
            itemMeta.setLore(appliedLore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public final void place(Player player, ItemStack itemStack, Block clickedBlock, BlockFace clickedBlockFace) {
        player.getInventory().remove(itemStack);
        Location location = clickedBlock.getLocation();


        if (clickedBlock == null || clickedBlockFace == null) return;

        switch (clickedBlockFace) {
            // x axis
            case EAST:
                location.add(1, 0, 0);
                break;
            case WEST:
                location.subtract(1, 0, 0);
                break;
            // y axis
            case UP:
                location.add(0, 1, 0);
                break;
            case DOWN:
                location.subtract(0, 1, 0);
                break;
            // z axis (north, south)
            case NORTH:
                location.add(0, 0, 1);
                break;
            case SOUTH:
                location.subtract(0, 0, 1);
                break;
        }

        location.getBlock().setType(getBlockMaterial()); // Placed Visibly
        CustomCore.blockHandler.placedBlocks.put(location, this); // Placed Programmatically
        CustomCore.blockHandler.saveMachineLocation(this, location);

        /* Ensuring Programmatic Definition is Registered */
        if (!CustomCore.blockHandler.blockRegistry.containsKey(getIdentifier())) {
            Bukkit.getLogger().warning("(" + getIdentifier() + ") is not registered. Automatically fixing related problems...");
            CustomCore.blockHandler.registerBlock(this);
        }
    }


    public final Collection<Entity> getEntitiesInRange(Block block, double range) {
        Location locationOne = block.getLocation().clone();
        locationOne.add(range, range, range);

        Location locationTwo = block.getLocation().clone();
        locationTwo.subtract(range, range, range);

        return block.getWorld().getNearbyEntities(new BoundingBox(locationOne.getX(), locationOne.getY(), locationOne.getZ(), locationTwo.getX(), locationTwo.getY(), locationTwo.getZ()));
    }


    public final Collection<Entity> getEntitiesInRelation(Block block, double x, double y, double z) {
        Location locationOne = block.getLocation().clone();
        locationOne.add(x, y, z);

        Location locationTwo = block.getLocation().clone();

        return block.getWorld().getNearbyEntities(new BoundingBox(locationOne.getX(), locationOne.getY(), locationOne.getZ(), locationTwo.getX(), locationTwo.getY(), locationTwo.getZ()));
    }

    public abstract String   getName();

    public abstract String[] getItemLore();

    public abstract Material getItemMaterial();

    public abstract Material getBlockMaterial();

    public abstract void onBlockPlaced(Player player, ItemStack itemStack, Block block);

    public abstract void onBlockBroken(Player player, Block block);

    public abstract void onBlockInteract(Player player, Block block, PlayerInteractEvent event);

    public abstract void onGameTicked(Block block);


}
