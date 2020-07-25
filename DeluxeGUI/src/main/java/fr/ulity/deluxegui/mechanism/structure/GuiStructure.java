package fr.ulity.deluxegui.mechanism.structure;

import de.leonhard.storage.shaded.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuiStructure {
    public @NotNull String name;
    public @NotNull String title = "";
    public @NotNull String permission;
    public @NotNull Open open = new Open();
    public @NotNull List<Content> content = new ArrayList<>();
}
