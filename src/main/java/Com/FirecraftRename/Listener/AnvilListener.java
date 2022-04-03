package Com.FirecraftRename.Listener;

import Com.FirecraftRename.FirecraftRename;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AnvilListener implements Listener {
    private FirecraftRename instance;

    public AnvilListener (FirecraftRename instance) {
        this.instance = instance;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void playerRenameItem(InventoryClickEvent event){
        if (event.getView().getType() != InventoryType.ANVIL)
            return;

        if (event.isCancelled())
            return;

        if (event.getRawSlot() != 2)
            return;

        ItemStack initialItem = event.getView().getItem(0);
        ItemStack finalItem = event.getView().getItem(2);
        if (initialItem.getType() == Material.AIR && finalItem.getType() == Material.AIR)
            return;

        // Disables all Anvils if the list is incomplete to prevent players from bugging items
        if (
            instance.getBlacklistRenameItems().getBlockedMaterial() == null ||
            instance.getBlacklistRenameItems().getBlockedLores() == null ||
            instance.getBlacklistRenameItems().getBlockedNames() == null
        )
        {
            event.getWhoClicked().sendMessage(setColor("&8[&c&lFIRE&f&lCRAFT&8] &fDer skete en &cfejl&f. Opret en Ticket. &8(KUNNE IKKE FINDE BLACKLISTET ITEMS)"));
            event.setCancelled(true);
            return;
        }

        // Check if the material the player tries to rename is banned
         if (checkMaterialBlacklist(initialItem)) {
             event.setCancelled(true);
             event.getWhoClicked().sendMessage(setColor("&8[&c&lFIRE&f&lCRAFT&8] &fDu kan ikke rename dette item!"));
             return;
         }

         // Check if the Item name is Banned
        if (checkNameBlacklist(initialItem)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(setColor("&8[&c&lFIRE&f&lCRAFT&8] &fDu kan ikke rename dette item!"));
            return;
        }

        if (initialItem.getItemMeta().getLore() == null)
            return;

        // Check if the Item Lore is Banned
        if (checkLoreBlacklist(initialItem)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(setColor("&8[&c&lFIRE&f&lCRAFT&8] &fDu kan ikke rename dette item!"));
            return;
        }


    }

    private boolean checkMaterialBlacklist(ItemStack initialItem) {
        for (Material mat: instance.getBlacklistRenameItems().getBlockedMaterial()) {
            if (initialItem.getType().equals(mat)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNameBlacklist(ItemStack initialItem) {
        if (initialItem.getItemMeta().getDisplayName() == null)
            return false;

        for (String s: instance.getBlacklistRenameItems().getBlockedNames()) {
            if (initialItem.getItemMeta().getDisplayName().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return  false;
    }

    private boolean checkLoreBlacklist(ItemStack initialItem) {
        for (String s: instance.getBlacklistRenameItems().getBlockedLores()) {
            if (initialItem.getItemMeta().getLore().contains(s)) {
                return true;
            }
        }

        return  false;
    }

    private String setColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
