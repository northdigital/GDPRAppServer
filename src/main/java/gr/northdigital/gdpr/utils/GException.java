package gr.northdigital.gdpr.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class GException extends WebApplicationException {
  public GException() {
    super(Response.status(INTERNAL_SERVER_ERROR).build());
  }

  public GException(String message) {
    super(Response.status(INTERNAL_SERVER_ERROR).entity(message).type("text/plain").build());
  }
}

