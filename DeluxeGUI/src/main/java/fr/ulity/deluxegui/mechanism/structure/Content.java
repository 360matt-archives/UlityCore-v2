package fr.ulity.deluxegui.mechanism.structure;

import fr.ulity.deluxegui.mechanism.structure.content.Action;
import fr.ulity.deluxegui.mechanism.structure.content.Extra;
import fr.ulity.deluxegui.mechanism.structure.content.Item;

public class Content {
    public int line;
    public int column;
    public Item item = new Item();
    public Extra extra = new Extra();
    public Action action = new Action();
}
