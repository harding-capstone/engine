package com.shepherdjerred.capstone.engine.game.network.client.state;

import com.shepherdjerred.capstone.engine.game.event.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.network.packet.packets.StartMatchPacket;

public class LobbyClientState extends AbstractNetworkClientState {

  public LobbyClientState(EventBus<Event> eventBus,
      NetworkClient networkClient) {
    super(eventBus, networkClient);
  }

  @Override
  protected EventHandlerFrame<Event> createEventHandlerFrame() {
    var frame = new EventHandlerFrame<>();

    frame.registerHandler(StartGameEvent.class, (event) -> {
      networkClient.sendPacket(new StartMatchPacket());
    });

    return frame;
  }
}
