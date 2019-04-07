package com.shepherdjerred.capstone.engine.engine.event;

import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class TestEvent implements Event {

  private String value;
}
