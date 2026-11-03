package net.minecraftforge.common.capabilities;

public class Capability<T> {

    private final Class<T> type;

    public Capability(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }
}
