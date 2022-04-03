package Com.FirecraftRename.Utils;

import Com.FirecraftRename.FirecraftRename;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BlacklistRenameItems {
    private FirecraftRename instance;
    private List<String> blockedMaterialString;
    private List<Material> blockedMaterial;
    private List<String> blockedNames;
    private List<String> blockedLores;


    public BlacklistRenameItems (FirecraftRename instance) {
        this.instance = instance;
        makeMaterialBlacklist();
    }
    public void makeBlacklist() {
        makeLoresBlacklist();
        makeMaterialBlacklist();
        makeNamesBlacklist();
    }

    private void makeMaterialBlacklist() {
        blockedMaterialString = instance.getConfig().getStringList("Rename.Blocked-Item-Materials");
        blockedMaterial = new ArrayList<Material>();
        for (String current : blockedMaterialString) {
            try {
                blockedMaterial.add(Material.getMaterial(current.trim()));
            } catch (Exception e) {
                Bukkit.getLogger().warning("[ANVIL BLACKLIST] Kunne ikke blacklist et Item fra configen");
            }
        }
    }

    private void makeNamesBlacklist() {
        blockedNames = instance.getConfig().getStringList("Rename.Blocked-Item-Names");
    }

    private void makeLoresBlacklist() {
        blockedLores = instance.getConfig().getStringList("Rename.Blocked-Item-Lores");
    }

    public boolean reloadBlacklist() {
        // Clear existing data
        blockedMaterial.clear();
        blockedMaterialString.clear();
        blockedNames.clear();
        blockedLores.clear();

        // Names
        blockedNames = instance.getConfig().getStringList("Rename.Blocked-Item-Names");

        // Lore
        blockedNames = instance.getConfig().getStringList("Rename.Blocked-Item-Lores");

        // Material
        blockedMaterialString = instance.getConfig().getStringList("Rename.Blocked-Item-Materials");
        for (String current : blockedMaterialString) {
            try {
                blockedMaterial.add(Material.getMaterial(current.trim()));
            } catch (Exception e) {
                Bukkit.getLogger().warning("[ANVIL BLACKLIST] Kunne ikke blacklist et Item fra configen");
                return  false;
            }
        }
        return true;
    }

    public List<String> getBlockedMaterialString() {
        return blockedMaterialString;
    }

    public List<Material> getBlockedMaterial() {
        return blockedMaterial;
    }

    public List<String> getBlockedNames() {
        return blockedNames;
    }

    public List<String> getBlockedLores() {
        return blockedLores;
    }
}
