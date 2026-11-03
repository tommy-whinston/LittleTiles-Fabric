package net.minecraftforge.fml;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.api.distmarker.Dist;

import java.util.function.Supplier;

public final class DistExecutor {

    private DistExecutor() {
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT;
    }

    public static void unsafeRunWhenOn(Dist dist, Supplier<Runnable> runnable) {
        if ((dist == Dist.CLIENT) == isClient())
            runnable.get().run();
    }

    public static void runWhenOn(Dist dist, Supplier<Runnable> runnable) {
        unsafeRunWhenOn(dist, runnable);
    }

    public static <T> T unsafeCallWhenOn(Dist dist, Supplier<Supplier<T>> supplier) {
        if ((dist == Dist.CLIENT) == isClient())
            return supplier.get().get();
        return null;
    }
}
