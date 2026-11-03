package net.minecraftforge.client.event;

import net.minecraft.network.Connection;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class ClientPlayerNetworkEvent extends Event {

    private final Player player;
    private final Connection connection;

    public ClientPlayerNetworkEvent() {
        this.player = null;
        this.connection = null;
    }

    public ClientPlayerNetworkEvent(Player player, Connection connection) {
        this.player = player;
        this.connection = connection;
    }

    public Player getPlayer() {
        return player;
    }

    public Connection getConnection() {
        return connection;
    }

    public static class LoggingOut extends ClientPlayerNetworkEvent {

        public LoggingOut() {
        }

        public LoggingOut(Player player, Connection connection) {
            super(player, connection);
        }
    }

    public static class LoggingIn extends ClientPlayerNetworkEvent {

        public LoggingIn() {
        }

        public LoggingIn(Player player, Connection connection) {
            super(player, connection);
        }
    }
}
