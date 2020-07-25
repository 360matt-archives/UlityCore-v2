package fr.ulity.customcommands;

import fr.ulity.core.api.Config;

import java.util.HashMap;

public class CreateTemplate {

    public static void make (Config config) {
        if (config.singleLayerKeySet().size() == 0) {
            PassInfoCommand template = new PassInfoCommand();
            template.name = "vote";
            template.description = "show the link of vote";
            template.text = "&6Vote &efor the server &5Example\n&chttps://vote_site.com/example.666/";
            template.type = "message";
            template.permission = "";
            template.permissionMessage = "";
            template.usage = "&e/vote";
            template.command = "";

            config.set("vote", template);
        }
    }

}
