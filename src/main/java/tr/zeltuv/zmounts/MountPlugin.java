package tr.zeltuv.zmounts;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tr.zeltuv.zmounts.commands.MountCommand;

import java.util.HashMap;
import java.util.Map;

public class MountPlugin extends JavaPlugin {

    private Map<Player,Entity> horses = new HashMap<>();
    private String prefix;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        prefix = getConfig().getString("plugin-prefix").replace("&","§");

        getCommand("mount").setExecutor(new MountCommand(this));
    }

    @Override
    public void onDisable() {
        getHorses().forEach((player, entity) -> {
            player.eject();
            entity.remove();
        });
        getHorses().clear();
    }

    public Map<Player, Entity> getHorses() {
        return horses;
    }

    public void spawnHorseForPlayer(Player player,HorseType horseType){

        FileConfiguration configuration = getConfig();

        switch (horseType){
            case NORMAL:
                if(player.hasPermission(configuration.getString("command-permission")
                        .replace("{mount}","normal"))){
                    player.sendMessage(prefix+
                            configuration
                                    .getString("spawned-horse")
                                    .replace("&","§")
                                    .replace("{type}","Normal")
                    );

                    HorseType.NORMAL.spawn(player,this);
                }else{
                    player.sendMessage(prefix+
                            configuration
                                    .getString("no-permission")
                                    .replace("&","§")
                    );

                }
                return;
            case ZOMBIE:
                if(player.hasPermission(configuration.getString("command-permission")
                        .replace("{mount}","zombie"))){
                    player.sendMessage(prefix+
                            configuration
                                    .getString("spawned-horse")
                                    .replace("&","§")
                                    .replace("{type}","Zombie")
                    );

                    HorseType.ZOMBIE.spawn(player,this);
                }else{
                    player.sendMessage(prefix+
                            configuration
                                    .getString("no-permission")
                                    .replace("&","§")
                    );

                }
                return;
            case SKELETON:
                if(player.hasPermission(configuration.getString("command-permission")
                        .replace("{mount}","skeleton"))){
                    player.sendMessage(prefix+
                            configuration
                                    .getString("spawned-horse")
                                    .replace("&","§")
                                    .replace("{type}","Skeleton")
                    );

                    HorseType.SKELETON.spawn(player,this);
                }else{
                    player.sendMessage(prefix+
                            configuration
                                    .getString("no-permission")
                                    .replace("&","§")
                    );

                }
                return;
        }
    }
}
