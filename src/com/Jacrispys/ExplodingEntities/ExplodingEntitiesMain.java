package com.Jacrispys.ExplodingEntities;

import com.Jacrispys.ExplodingEntities.Commands.ExplodeEverything;
import org.bukkit.plugin.java.JavaPlugin;

public class ExplodingEntitiesMain extends JavaPlugin {

    @Override
    public void onEnable() {
        new ExplodeEverything(this);
    }
}
