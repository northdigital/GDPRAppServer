package gr.logismos.gdpr.resources;

import gr.logismos.gdpr.models.UserCredentials;
import gr.logismos.x509certificatevalidator.X509CertificateValidator;
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
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

@Path("/auth")
public class AuthService {
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/authenticate")
  public UserCredentials authenticate(UserCredentials userCredentials) throws Exception {

    X509Certificate x509Certificate = null;
    Object o = null;

    try {
      StringReader reader = new StringReader(userCredentials.pem);
      PEMReader pemReader = new PEMReader(reader);
      x509Certificate = (X509Certificate) pemReader.readObject();
      pemReader.close();

      X509CertificateValidator x509CertificateValidator = new X509CertificateValidator();
      KeyStore keyStore = x509CertificateValidator.loadKeyStoreFromFile(
        "D:\\Projects\\X509CertificateValidator\\ssl\\keys2.jks", "sporades");
      boolean isValid = x509CertificateValidator.isValidCertificate(x509Certificate,keyStore);
      System.out.println(isValid);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return userCredentials;
  }
}
