package com.shepherdjerred.capstone.engine.game.network.client.handler;

import com.shepherdjerred.capstone.engine.game.event.events.DoTurnEvent;
import com.shepherdjerred.capstone.engine.game.event.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.event.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.event.PacketReceivedEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.DoTurnPacket;
import com.shepherdjerred.capstone.network.packet.packets.PlayerJoinPacket;
import com.shepherdjerred.capstone.network.packet.packets.StartMatchPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final EventBus<Event> eventBus;

  @Override
  public void handle(PacketReceivedEvent packetReceivedEvent) {
    var packet = packetReceivedEvent.getPacket();

    if (packet instanceof PlayerJoinPacket) {
      eventBus.dispatch(new PlayerJoinEvent(((PlayerJoinPacket) packet).getPlayer()));
    } else if (packet instanceof StartMatchPacket) {
      eventBus.dispatch(new StartGameEvent());
    } else if (packet instanceof DoTurnPacket) {
      eventBus.dispatch(new DoTurnEvent(((DoTurnPacket) packet).getTurn()));
    }
  }
}
