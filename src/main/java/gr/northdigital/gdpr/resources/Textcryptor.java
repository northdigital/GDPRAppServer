package gr.northdigital.gdpr.resources;

import gr.northdigital.utilssl.SSL;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Path("/textcryptor")
public class Textcryptor {
  private final static String BASE_PATH = "C:\\Users\\Panagiotis\\Desktop\\ssl\\";

  @GET
  @Path("encrypt")
  @Produces(MediaType.TEXT_PLAIN)
  public String encrypt(@QueryParam("text") String text) throws Exception {
    KeyStore keyStore = SSL.loadKeyStoreFromFile(BASE_PATH + "keys.jks", "sporades");
    X509Certificate cer = ((X509Certificate) keyStore.getCertificate("root"));
    PublicKey pubKey = cer.getPublicKey();

    String encrypted = SSL.encryptTextWithKey(pubKey, text);

    return encrypted;
  }
}
