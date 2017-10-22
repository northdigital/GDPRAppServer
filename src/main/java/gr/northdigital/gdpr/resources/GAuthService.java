package gr.northdigital.gdpr.resources;

import gr.northdigital.gdpr.AppConfig;
import gr.northdigital.gdpr.models.GTicket;
import gr.northdigital.gdpr.models.GSession;
import gr.northdigital.gdpr.models.GUserCredentials;
import gr.northdigital.gdpr.utils.GException;
import gr.northdigital.gdpr.utils.GUnauthorizedException;
import gr.northdigital.utilssl.SSL;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;

@Path("/auth")
public class GAuthService {
  private final String BASE_PATH = "C:\\Users\\Panagiotis\\Desktop\\ssl\\";

  @POST
  @Path("/authenticate")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public GTicket authenticate(GUserCredentials userCredentials) throws Exception {
    try {
      X509Certificate x509Certificate = SSL.loadCertificateFromPemString(userCredentials.pem);
      KeyStore keyStore = SSL.loadKeyStoreFromFile(BASE_PATH + "keys.jks", "sporades");
      PrivateKey privateKey = (PrivateKey) keyStore.getKey("root.pk", "sporades".toCharArray());

      boolean isValid = SSL.isValidCertificate(x509Certificate, keyStore);

      if (isValid) {
        String encodedOid = SSL.getExtensionValue(x509Certificate, "2.16.840.1.113730.1.13");
        String[] secretParts = StringUtils.splitByWholeSeparatorPreserveAllTokens(encodedOid, " ");
        String userName = SSL.decryptTextWithKey(privateKey, secretParts[0]);
        String password = SSL.decryptTextWithKey(privateKey, secretParts[1]);
        byte[] symmetricKey = Hex.decodeHex(SSL.decryptTextWithKey(privateKey, secretParts[2]).toCharArray());

        if(userCredentials.userName.equals(userName) && userCredentials.password.equals(password)) {

          GSession _session = null;

          synchronized (AppConfig.sessions) {
            for (GSession session : AppConfig.sessions) {
              if(session.subjectDN.equals(x509Certificate.getSubjectDN().getName())) {
                _session = session;
                break;
              }
            }
          }

          if(_session == null) {
            Date now = new Date();
            _session = new GSession();
            _session.gTicket = new GTicket();
            _session.symmetricKey = symmetricKey;
            _session.subjectDN = x509Certificate.getSubjectDN().getName();
            _session.createdAt = now;
            _session.lastAccessedAt = now;

            AppConfig.sessions.add(_session);
          } else {
            _session.lastAccessedAt = new Date();
          }

          return _session.gTicket;
        }
      }
    } catch (Exception e) {
      throw new GException(e.getMessage());
    }

    throw new GUnauthorizedException("Provided credentials are not valid!");
  }
}
