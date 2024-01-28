package org.domi;

import org.CatAndDomi.CatFish;
import org.CatAndDomi.components.CatFishBuilder;
import org.CatAndDomi.components.ComponentType;
import org.CatAndDomi.components.command.ArgsTypes;
import org.CatAndDomi.components.command.CommandComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.domi.command.Commands;
import org.domi.event.Events;

public class METEOR extends JavaPlugin {
    private static METEOR plugin;
    public CatFish cf;
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
    }

    @Override
    public void onDisable() {

    }
}