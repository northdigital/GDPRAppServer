package gr.logismos.gdpr.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

public class GUnauthorizedException extends WebApplicationException {
  public GUnauthorizedException() {
    super(Response.status(UNAUTHORIZED).build());
  }

  public GUnauthorizedException(String message) {
    super(Response.status(UNAUTHORIZED).entity(message).type("text/plain").build());
  }
}
