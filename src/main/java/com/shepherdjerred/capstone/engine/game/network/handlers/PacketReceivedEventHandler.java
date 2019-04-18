package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.network.events.GameOverEvent;
import com.shepherdjerred.capstone.engine.game.network.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerCreatedEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.events.TurnEvent;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.PacketReceivedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.CreatedPlayerPacket;
import com.shepherdjerred.capstone.network.packet.packets.EditLobbyPacket;
import com.shepherdjerred.capstone.network.packet.packets.GameOverPacket;
import com.shepherdjerred.capstone.network.packet.packets.LobbyAction;
import com.shepherdjerred.capstone.network.packet.packets.PlayerLobbyActionPacket;
import com.shepherdjerred.capstone.network.packet.packets.SendChatMessagePacket;
import com.shepherdjerred.capstone.network.packet.packets.StartGamePacket;
import com.shepherdjerred.capstone.network.packet.packets.TurnPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketReceivedEventHandler implements EventHandler<PacketReceivedEvent> {

  private final GameClient game;

  @Override
  public void handle(PacketReceivedEvent event) {
    var packet = event.getPacket();
    if (packet instanceof SendChatMessagePacket) {
      handleSendChatMessagePacket((SendChatMessagePacket) packet);
    } else if (packet instanceof PlayerLobbyActionPacket) {
      handleLobbyActionPacket((PlayerLobbyActionPacket) packet);
    } else if (packet instanceof EditLobbyPacket) {
      handleEditLobbyPacket((EditLobbyPacket) packet);
    } else if (packet instanceof StartGamePacket) {
      handleReadyToStartGamePacket((StartGamePacket) packet);
    } else if (packet instanceof TurnPacket) {
      handleMakeTurnPacket((TurnPacket) packet);
    } else if (packet instanceof GameOverPacket) {
      handleGameOverPacket((GameOverPacket) packet);
    } else if (packet instanceof CreatedPlayerPacket) {
      handleCreatedPlayerPacket((CreatedPlayerPacket) packet);
    }
  }

  private void handleSendChatMessagePacket(SendChatMessagePacket sendChatMessagePacket) {
    game.dispatch(new PlayerChatEvent(sendChatMessagePacket.getChatMessage()));
  }

  private void handleLobbyActionPacket(PlayerLobbyActionPacket lobbyActionPacket) {
    var player = lobbyActionPacket.getPlayer();
    if (lobbyActionPacket.getLobbyAction() == LobbyAction.JOIN) {
      game.dispatch(new PlayerJoinEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.LEAVE) {
      game.dispatch(new PlayerLeaveEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.EVICT) {
      game.dispatch(new PlayerEvictEvent(player));
    } else if (lobbyActionPacket.getLobbyAction() == LobbyAction.HOSTLEAVE) {
      game.dispatch(new HostLeaveEvent(player));
    }
  }

  private void handleEditLobbyPacket(EditLobbyPacket editLobbyPacket) {
    game.dispatch(new EditLobbyEvent(editLobbyPacket.getLobbySettings(), editLobbyPacket.getPlayer()));
  }

  private void handleReadyToStartGamePacket(StartGamePacket startGamePacket) {
    game.dispatch(new StartGameEvent(startGamePacket.getMatch()));
  }

  private void handleMakeTurnPacket(TurnPacket turnPacket) {
    game.dispatch(new TurnEvent(turnPacket.getTurn()));
  }

  private void handleGameOverPacket(GameOverPacket packet) {
    game.dispatch(new GameOverEvent(packet.getStatus()));
  }

  private void handleCreatedPlayerPacket(CreatedPlayerPacket packet) {
    game.dispatch(new PlayerCreatedEvent(packet.getPlayer(), packet.getLobby()));
  }


}
