package org.domi;

import org.CatAndDomi.CatFish;
import org.CatAndDomi.components.CatFishBuilder;
import org.CatAndDomi.components.ComponentType;
import org.CatAndDomi.components.command.ArgsTypes;
import org.CatAndDomi.components.command.CommandComponent;
import org.CatAndDomi.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.domi.command.CMD.CMDS;
import org.domi.command.Commands;
import org.domi.event.Events;

public class METEOR extends JavaPlugin {
    private static METEOR plugin;
    public CatFish cf;
    private Location meteor;
    private Location meteor2;
    public YamlConfiguration config;
    private Commands command = new Commands();
    public CommandComponent component;
    public static METEOR getInstance() {
        return plugin;
    }

    public void setUpCatFish() {
        if(Bukkit.getPluginManager().isPluginEnabled("CatFish")) {
            cf = (CatFish) Bukkit.getPluginManager().getPlugin("CatFish");
        }
    }

    private void loadConfig() {
        int minx = getConfig().getInt("meteor.x1", 0);
        int miny = getConfig().getInt("meteor.y1", 0);
        int minz = getConfig().getInt("meteor.z1", 0);
        int maxx = getConfig().getInt("meteor.x2", 0);
        int maxy = getConfig().getInt("meteor.y2", 0);
        int maxz = getConfig().getInt("meteor.z2", 0);
        meteor = new Location(getServer().getWorlds().get(0), minx, miny, minz);
        meteor2 = new Location(getServer().getWorlds().get(0), maxx, maxy, maxz);
    }

    @Override
    public void onEnable() {
        plugin = this;
        setUpCatFish();
        new Events();
        new CatFishBuilder(this).addComponents(ComponentType.COMMAND).build();
        component = (CommandComponent) cf.getComponent(this, ComponentType.COMMAND);
        try {
            component.addCommand(command.getClass().getMethod("getWands", CommandSender.class, String[].class), new ArgsTypes[]{}, "/메테오영역설정", "메테오영역설정");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        component.load();

        new CatFishBuilder(this).addComponents(ComponentType.COMMAND).build();
        component = (CommandComponent) cf.getComponent(this, ComponentType.COMMAND);
        try {
            component.addCommand(command.getClass().getMethod("meteorCount", CommandSender.class, Integer.class, String[].class), new ArgsTypes[]{ArgsTypes.INTEGER}, "/메테오 <INT>", "메테오");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        component.load();

        config = ConfigUtils.loadPluginConfig(this);

        CMDS.locations = getMin();
        CMDS.locations = getMax();

    }

    public Location[] getMin(){
        int minx = config.getInt("meteor.x1");
        int miny = config.getInt("meteor.y1");
        int minz = config.getInt("meteor.z1");
        meteor = new Location(getServer().getWorlds().get(0), minx, miny, minz);
        return new Location[0];
    }

    public Location[] getMax(){
        int maxx = config.getInt("meteor.x2");
        int maxy = config.getInt("meteor.y2");
        int maxz = config.getInt("meteor.z2");
        meteor2 = new Location(getServer().getWorlds().get(0), maxx, maxy, maxz);
        return new Location[1];
    }

    public Location setMin() {


        return meteor;
    }


    @Override
    public void onDisable() {

    }
}