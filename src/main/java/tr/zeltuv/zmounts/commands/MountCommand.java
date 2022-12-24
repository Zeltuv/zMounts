package tr.zeltuv.zmounts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tr.zeltuv.zmounts.HorseType;
import tr.zeltuv.zmounts.MountPlugin;

import java.util.ArrayList;
import java.util.List;

public class MountCommand implements CommandExecutor {

    private MountPlugin plugin;
    private FileConfiguration configuration;

    public MountCommand(MountPlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;


            switch (label.toLowerCase()) {
                case "mount":
                    if (args.length == 1) {
                        switch (args[0].toLowerCase()) {
                            case "normal":
                                plugin.spawnHorseForPlayer(player, HorseType.NORMAL);
                                return false;
                            case "skeleton":
                                plugin.spawnHorseForPlayer(player, HorseType.SKELETON);
                                return false;
                            case "zombie":
                                plugin.spawnHorseForPlayer(player, HorseType.ZOMBIE);
                                return false;
                        }
                    }
                    sendHelpMsg(player);
                    return false;
                case "mn":
                    plugin.spawnHorseForPlayer(player, HorseType.NORMAL);
                    return false;
                case "mz":
                    plugin.spawnHorseForPlayer(player, HorseType.ZOMBIE);
                    return false;
                case "ms":
                    plugin.spawnHorseForPlayer(player, HorseType.SKELETON);
                    return false;
            }
            sendHelpMsg(player);
        }
        return false;
    }

    public void sendHelpMsg(Player player) {
        List<String> messages = new ArrayList<>();

        configuration.getStringList("help")
                .forEach(s -> messages.add(s.replace("&", "§")));

        player.sendMessage(
                messages.toArray(new String[0]));

    }
}
