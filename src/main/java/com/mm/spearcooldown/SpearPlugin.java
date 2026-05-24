package com.mm.spearcooldown;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpearPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("تم تفعيل بلوجن ثغرة السبير بنجاح بمجرد التبديل!");
    }

    @EventHandler
    public void onHotbarChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        
        // الحصول على الأداة التي كانت في يد اللاعب قبل تغيير السلوت
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
        
        // التحقق: هل كان يمسك السبير في الخانة السابقة؟
        if (previousItem != null) {
            String itemName = previousItem.getType().name();
            
            if (itemName.contains("SPEAR") || itemName.contains("TRIDENT")) {
                // تصفير الكلوداون فوراً بمجرد الانتقال لخانة أخرى (سواء فارغة أو ممتلئة)
                player.setCooldown(previousItem.getType(), 0);
            }
        }
    }
}
