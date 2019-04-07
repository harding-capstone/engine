package com.shepherdjerred.capstone.engine.engine.event;

import com.shepherdjerred.capstone.engine.engine.input.keyboard.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KeyReleasedEvent implements InputEvent {

  private final Key key;
}
