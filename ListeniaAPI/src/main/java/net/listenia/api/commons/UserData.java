package net.listenia.api.commons;

import fr.ulity.core.api.Data;

import java.io.File;

public class UserData extends Data {

    public UserData (String playerName) {
        super(new File(ListeniaAPI.path.getPath() + "/players/" + playerName + ".json"));

        setDefault("economy.global", 0);
        setDefault("timespent", 0);

    }

}
