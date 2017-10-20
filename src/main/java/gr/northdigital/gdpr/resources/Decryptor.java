package gr.northdigital.gdpr.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/decryptor")
public class Decryptor {
  @GET
  @Path("decrypt")
  @Produces(MediaType.TEXT_PLAIN)
  public String decrypt(@QueryParam("enc") String encryptedString) {
    return encryptedString.toUpperCase();
  }
}
