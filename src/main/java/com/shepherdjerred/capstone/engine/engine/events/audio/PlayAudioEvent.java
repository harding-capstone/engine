package com.shepherdjerred.capstone.engine.engine.events.audio;

import com.shepherdjerred.capstone.engine.engine.audio.SourcedAudio;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PlayAudioEvent implements Event {

  private final SourcedAudio audio;
}
