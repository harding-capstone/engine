package com.shepherdjerred.capstone.engine;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  public static void main(String[] args) {
    try {
      Integer.valueOf("Not an Integer");
    } catch (NumberFormatException e) {

      log.error("Can't convert to Integer!!", e);
    }
  }
}
