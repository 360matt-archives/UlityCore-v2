package fr.ulity.deluxekit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Kit implements Serializable {
    private Config confFile;

    public String name, title, description;
    public List<Content> contents = new ArrayList<>();

    public Kit (String name) {
        this.name = name;
        confFile = new Config(name, Api.corePath + "/addons/DeluxeKit/kits/");
        load();
    }

    public static class Content {
        public int id = 1;
        public org.bukkit.Material material;
        public ItemMeta meta;
        public int count;

        @Override
        public String toString() {
            return "Content{" +
                    "id=" + id +
                    ", material=" + material +
                    ", meta=" + meta +
                    ", count=" + count +
                    '}';
        }
    };

    public void save () {
        confFile.set("title", title);
        confFile.set("description", description);
        confFile.set("contents", contents);
    }

    public void load () {
        title = confFile.getString("title");
        description = confFile.getString("description");


        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            contents = (List<Content>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
