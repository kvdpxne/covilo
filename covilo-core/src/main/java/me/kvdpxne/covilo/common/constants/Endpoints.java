package me.kvdpxne.covilo.common.constants;

import me.kvdpxne.covilo.shared.ObjectInitializationError;

public final class Endpoints {

  /**
   *
   */
  public static final String COMMENTS = "api/v1/comments";

  /**
   *
   */
  public static final String CRIME = "api/v1/crime";

  /**
   *
   */
  public static final String GEOLOCATION = "api/v1/geolocation";

  /**
   *
   */
  public static final String SEARCH = "api/v1/search";

  /**
   *
   */
  public static final String USER = "api/v1/user";

  /**
   *
   */
  public static final String USER_AUTHENTICATION = "api/v1/authentication";

  /**
   *
   */
  public static final String USER_ME = "api/v1/me";

  private Endpoints() {
    throw new ObjectInitializationError();
  }
}
