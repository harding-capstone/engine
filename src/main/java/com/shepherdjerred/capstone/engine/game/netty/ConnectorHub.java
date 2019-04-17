package com.shepherdjerred.capstone.engine.game.netty;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import java.util.HashSet;
import java.util.Set;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * Composes multiple connectors.
 */
@Log4j2
public class ConnectorHub {

  @Setter
  private Connection serverConnection;
  private final Set<Connector> connectors;
  private final EventBus<Event> eventBus;

  public ConnectorHub(EventBus<Event> serverEventBus) {
    this.serverConnection = null;
    this.connectors = new HashSet<>();
    this.eventBus = serverEventBus;
  }

  public void sendPacket(Packet packet) {
    serverConnection.sendPacket(packet);
  }

  public void registerConnector(Connector connector) {
    connectors.add(connector);
    connector.acceptConnections();
  }

  public void handleLatestEvents() {
    connectors.forEach(connector -> {
      if (connector.hasEvent()) {
        var event = connector.getNextEvent();
        eventBus.dispatch(event);
      }
    });
  }
}
