package gr.logismos.gdpr.resources;

import gr.logismos.gdpr.models.UserCredentials;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.util.io.pem.PemObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.Security;
import java.security.cert.X509Certificate;

@Path("/auth")
public class AuthService {
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/authenticate")
  public UserCredentials authenticate(UserCredentials userCredentials) {

    X509Certificate cert = null;
    Object o = null;

    try {
      StringReader reader = new StringReader(userCredentials.pem);
      PEMReader pr = new PEMReader(reader);
      cert = (X509Certificate) pr.readObject();
      pr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return userCredentials;
  }
}
