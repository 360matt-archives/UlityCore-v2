package fr.ulity.moderation.bukkit;


import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.loaders.BukkitLoader;
import fr.ulity.moderation.api.sanctions.freeze.BukkitFreezeSystem;
import fr.ulity.moderation.bukkit.cmd.*;

import fr.ulity.moderation.bukkit.events.BanEvent;
import fr.ulity.moderation.bukkit.events.FreezeEvent;
import fr.ulity.moderation.bukkit.events.MuteEvent;
import org.bukkit.Server;


public final class MainModBukkit extends BukkitLoader {
    public static MainModBukkit plugin;
    public static Server server;


    @Override
    public void onEnable() {
        Core.initialize(this);

        plugin = this;
        server = getServer();


        // register config field


        BukkitFreezeSystem.regListener();

        // register commands :
        new BanCmd();
        new BanipCmd();
        new ChatCmd();
        new ClearchatCmd();
        new FreezeCmd();
        new InvseeCmd();
        new KickCmd();
        new MuteCmd();
        new StaffchatCmd();
        new TempbanCmd();
        new TempmuteCmd();
        new UnbanCmd();
        new UnfreezeCmd();
        new UnbanIpCmd();
        new UnmuteCmd();
        new VanishCmd();

        // register/start modules :
        StartModule.start();


        // register events
        registerEvent(new MuteEvent());
        registerEvent(new BanEvent());
        registerEvent(new FreezeEvent());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
