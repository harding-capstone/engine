package com.shepherdjerred.capstone.engine.game.network.client.state;

import com.shepherdjerred.capstone.engine.game.event.events.IdentifyPlayerEvent;
import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.network.packet.packets.PlayerDescriptionPacket;
import lombok.extern.log4j.Log4j2;

/**
 * Before the client connects to a server.
 */
@Log4j2
public class DisconnectedClientState implements NetworkClientState {

  private final NetworkClient networkClient;
  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public DisconnectedClientState(EventBus<Event> eventBus, NetworkClient networkClient) {
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    this.networkClient = networkClient;
    createEventHandlerFrame();
  }

  private void createEventHandlerFrame() {
    eventHandlerFrame.registerHandler(IdentifyPlayerEvent.class,
        (event) -> {
          networkClient.sendPacket(new PlayerDescriptionPacket(event.getPlayerInformation()));
        });
  }

  public void enable() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  public void disable() {
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
