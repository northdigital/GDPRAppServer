package gr.logismos.gdpr.resources;

import gr.logismos.gdpr.models.GAccessTicket;
import gr.logismos.gdpr.models.GUserCredentials;
import gr.logismos.gdpr.utils.GException;
import gr.logismos.gdpr.utils.GTicketGenerator;
import gr.logismos.gdpr.utils.GUnauthorizedException;
import gr.logismos.x509certificatevalidator.X509CertificateValidator;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.openssl.PEMReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

@Path("/auth")
public class GAuthService {
  private final String BASE_PATH = "C:\\Users\\Panagiotis\\Desktop\\ssl\\";

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/authenticate")
  public GAccessTicket authenticate(GUserCredentials userCredentials) throws Exception {
    try {
      StringReader reader = new StringReader(userCredentials.pem);
      PEMReader pemReader = new PEMReader(reader);
      X509Certificate x509Certificate = (X509Certificate) pemReader.readObject();
      pemReader.close();

      X509CertificateValidator x509CertificateValidator = new X509CertificateValidator();
      KeyStore keyStore = x509CertificateValidator.loadKeyStoreFromFile(BASE_PATH + "keys.jks", "sporades");
      boolean isValid = x509CertificateValidator.isValidCertificate(x509Certificate, keyStore);

      GAccessTicket gAccessTicket = null;

      if (isValid) {
        byte[] extensionValue = x509Certificate.getExtensionValue("2.16.840.1.113730.1.13");

        ASN1InputStream asn1InputStream = new ASN1InputStream(new ByteArrayInputStream(extensionValue));
        ASN1OctetString asn1OctetString = (ASN1OctetString) asn1InputStream.readObject();
        asn1InputStream = new ASN1InputStream(new ByteArrayInputStream(asn1OctetString.getOctets()));
        String secret = asn1InputStream.readObject().toString();

        //TODO validate secret

        gAccessTicket = GTicketGenerator.generate("demo", 30);
        return gAccessTicket;
      }
    } catch (Exception e) {
      throw new GException(e.getMessage());
    }

    throw new GUnauthorizedException("Provided credentials are not valid!");
  }
}
