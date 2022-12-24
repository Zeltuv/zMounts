package tr.zeltuv.zmounts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public enum HorseType {
    SKELETON(EntityType.SKELETON_HORSE)
    ,NORMAL(EntityType.HORSE)
    ,ZOMBIE(EntityType.ZOMBIE_HORSE);

    private EntityType entityType;

    HorseType(EntityType entityType){
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void spawn(Player player,MountPlugin plugin){
        Entity entity = plugin.getHorses().get(player);

        if(entity != null){
            player.eject();
            entity.remove();
            plugin.getHorses().remove(player);
        }

        AbstractHorse horse = (AbstractHorse) player.getWorld().spawnEntity(player.getLocation(),entityType);

        horse.setTamed(true);
        horse.setAdult();
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        horse.addPassenger(player);

        plugin.getHorses().put(player,horse);

        new BukkitRunnable(){
            @Override
            public void run() {
                if(horse.isDead()){
                    Bukkit.getScheduler().runTask(plugin,()->{
                        plugin.getHorses().remove(player);
                    });
                    cancel();
                    return;
                }

                if(horse.getPassengers().size() == 0){
                    Bukkit.getScheduler().runTask(plugin,()->{
                        horse.remove();
                        plugin.getHorses().remove(player);
                    });
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin,5,5);
    }
}
