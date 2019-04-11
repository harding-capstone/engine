package com.shepherdjerred.capstone.engine.engine.events;

import com.shepherdjerred.capstone.engine.engine.input.keyboard.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KeyPressedEvent implements InputEvent {

  private final Key key;
}
