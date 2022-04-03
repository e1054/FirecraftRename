package Com.FirecraftRename;

import Com.FirecraftRename.Listener.AnvilListener;
import Com.FirecraftRename.Utils.BlacklistRenameItems;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FirecraftRename extends JavaPlugin {
    private BlacklistRenameItems blacklistRenameItems;

    @Override
    public void onEnable() {
        blacklistRenameItems = new BlacklistRenameItems(this);
        blacklistRenameItems.makeBlacklist();

        Bukkit.getServer().getPluginManager().registerEvents(new AnvilListener(this), this);

        saveDefaultConfig();
    }

    public BlacklistRenameItems getBlacklistRenameItems() {
        return blacklistRenameItems;
    }
}
