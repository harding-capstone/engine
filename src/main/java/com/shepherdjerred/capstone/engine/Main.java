package com.shepherdjerred.capstone.engine;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

public class Main {

  private static SentryClient sentry;

  public static void main(String[] args) {
    Sentry.init();

    sentry = SentryClientFactory.sentryClient();

    var myClass = new Main();
    myClass.logWithStaticApi();
    myClass.logWithInstanceApi();
  }

  /**
   * An example method that throws an exception.
   */
  void unsafeMethod() {
    throw new UnsupportedOperationException("You shouldn't call this!");
  }

  /**
   * Examples using the (recommended) static API.
   */
  void logWithStaticApi() {
    Sentry.getContext().recordBreadcrumb(
        new BreadcrumbBuilder().setMessage("User made an action").build()
    );

    Sentry.getContext().setUser(
        new UserBuilder().setEmail("hello@sentry.io").build()
    );

    Sentry.getContext().addExtra("extra", "thing");

    Sentry.getContext().addTag("tagName", "tagValue");

    Sentry.capture("This is a test");

    try {
      unsafeMethod();
    } catch (Exception e) {
      Sentry.capture(e);
    }
  }

  /**
   * Examples that use the SentryClient instance directly.
   */
  void logWithInstanceApi() {
    Context context = sentry.getContext();

    context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());

    context.setUser(new UserBuilder().setEmail("hello@sentry.io").build());

    sentry.sendMessage("This is a test");

    try {
      unsafeMethod();
    } catch (Exception e) {
      sentry.sendException(e);
    }
  }
}
