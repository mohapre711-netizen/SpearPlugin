package com.mm.spearcooldown;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SpearPlugin extends JavaPlugin implements Listener {

    private Enchantment lungeEnchant;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        
        // جلب تطويرة Lunge الرسمية من ريجستري اللعبة
        lungeEnchant = Registry.ENCHANTMENT.get(NamespacedKey.minecraft("lunge"));
        
        getLogger().info("SpearCooldownBypass for Official Lunge Enabled!");
    }

    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
        
        if (previousItem != null && previousItem.getType() != Material.AIR) {
            ItemMeta meta = previousItem.getItemMeta();
            if (meta != null) {
                // التحقق إذا كان السلاح يملك تطويرة Lunge الرسمية (بأي مستوى 1 أو 2 أو 3)
                // أو إذا كان السلاح نفسه هو الـ Spear الرسمي في اللعبة
                boolean hasLunge = (lungeEnchant != null && meta.hasEnchant(lungeEnchant)) 
                                   || previousItem.getType().name().contains("SPEAR");

                if (hasLunge) {
                    Material spearMaterial = previousItem.getType();
                    // تصفير الكولداون فوراً بعد التبديل بـ 1 تيك لضمان الاستجابة
                    Bukkit.getScheduler().runTaskLater(this, () -> {
                        if (player.hasCooldown(spearMaterial)) {
                            player.setCooldown(spearMaterial, 0);
                        }
                    }, 1L);
                }
            }
        }
    }
}
