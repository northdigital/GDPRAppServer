package gr.logismos.gdpr.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/demo")
public class DemoResource {
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("d1")
  public String f1() {
    return "this is a demo resource!";
  }
}
