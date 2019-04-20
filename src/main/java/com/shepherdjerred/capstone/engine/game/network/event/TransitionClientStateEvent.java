package com.shepherdjerred.capstone.engine.game.network.event;

import com.shepherdjerred.capstone.engine.game.network.client.state.NetworkClientState;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TransitionClientStateEvent implements Event {

  private final NetworkClientState newState;
}
