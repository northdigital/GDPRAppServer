package gr.logismos.gdpr.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/decryptor")
public class Decryptor {
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("decrypt")
  public String decrypt(@QueryParam("enc") String encryptedString) {
    return encryptedString.toUpperCase();
  }
}
