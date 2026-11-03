package net.minecraftforge.common;

public final class ForgeConfig {

    private ForgeConfig() {
    }

    public static final Server SERVER = new Server();
    public static final Client CLIENT = new Client();

    public static class Server {

        public final BooleanValue fullBoundingBoxLadders = new BooleanValue(true);
    }

    public static class Client {

        public final BooleanValue alwaysSetupTerrainOffThread = new BooleanValue(false);
    }

    public static class BooleanValue {

        private boolean value;

        public BooleanValue(boolean value) {
            this.value = value;
        }

        public boolean get() {
            return value;
        }

        public void set(boolean value) {
            this.value = value;
        }
    }
}
