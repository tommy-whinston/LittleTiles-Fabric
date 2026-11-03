package team.creative.littletiles.client.compat;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.data.ModelDataManager;

public final class ModelDataHelper {

    private static final Map<Level, ModelDataManager> MANAGERS = new WeakHashMap<>();

    private ModelDataHelper() {
    }

    public static ModelDataManager get(Level level) {
        return MANAGERS.computeIfAbsent(level, x -> new ModelDataManager());
    }
}
