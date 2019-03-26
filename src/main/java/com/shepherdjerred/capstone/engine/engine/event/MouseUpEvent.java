package com.shepherdjerred.capstone.engine.engine.event;

import com.shepherdjerred.capstone.engine.engine.input.Mouse.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MouseUpEvent implements InputEvent {

  private final Button button;
}
