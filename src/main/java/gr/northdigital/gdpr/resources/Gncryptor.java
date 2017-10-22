package gr.northdigital.gdpr.resources;

import gr.northdigital.gdpr.AppConfig;
import gr.northdigital.gdpr.models.GSession;
import gr.northdigital.gdpr.utils.GUnauthorizedException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Path("/gncryptor")
public class Gncryptor {

  @GET
  @Path("encrypt")
  @Produces(MediaType.TEXT_PLAIN)
  public String encrypt(@QueryParam("ticket") String ticket, @QueryParam("text") String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    GSession _session = null;

    synchronized (AppConfig.sessions) {
      for (GSession session : AppConfig.sessions) {
        if (session.gTicket.ticket.equals(ticket)) {
          _session = session;
          break;
        }
      }
    }

    if (_session != null) {
      Cipher cipher = Cipher.getInstance("AES");
      SecretKeySpec secretKeySpec = new SecretKeySpec(_session.symmetricKey, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

      return Hex.encodeHexString(cipher.doFinal(text.getBytes()));
    }

    throw new GUnauthorizedException("Provided credentials are not valid!");
  }

  @GET
  @Path("decrypt")
  @Produces(MediaType.TEXT_PLAIN)
  public String decrypt(@QueryParam("ticket") String ticket, @QueryParam("text") String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, DecoderException {
    GSession _session = null;

    synchronized (AppConfig.sessions) {
      for (GSession session : AppConfig.sessions) {
        if (session.gTicket.ticket.equals(ticket)) {
          _session = session;
          break;
        }
      }
    }

    if (_session != null) {
      Cipher cipher = Cipher.getInstance("AES");
      SecretKeySpec secretKeySpec = new SecretKeySpec(_session.symmetricKey, "AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

      return new String(cipher.doFinal(Hex.decodeHex(text.toCharArray())));
    }

    throw new GUnauthorizedException("Provided credentials are not valid!");
  }
}
