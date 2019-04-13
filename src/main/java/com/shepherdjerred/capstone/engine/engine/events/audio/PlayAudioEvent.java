package com.shepherdjerred.capstone.engine.engine.events.audio;

import com.shepherdjerred.capstone.engine.engine.audio.AudioName;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PlayAudioEvent implements Event {

  private final AudioName audioName;
}
