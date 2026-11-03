package net.minecraftforge.client.model.data;

import java.util.Collections;
import java.util.Map;

public class ModelData {

    public static final ModelData EMPTY = new ModelData(Collections.emptyMap());

    private final Map<?, ?> data;

    public ModelData(Map<?, ?> data) {
        this.data = data;
    }

    public boolean has(Object property) {
        return data.containsKey(property);
    }

    public <T> T get(Object property) {
        return (T) data.get(property);
    }
}
