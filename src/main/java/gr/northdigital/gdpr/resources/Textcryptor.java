package gr.northdigital.gdpr.resources;

import gr.northdigital.utilssl.SSL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

@Path("/textcryptor")
public class Textcryptor {
  private final static String BASE_PATH = "C:\\Users\\Panagiotis\\Desktop\\ssl\\";

  @GET
  @Path("encryptKey")
  @Produces(MediaType.TEXT_PLAIN)
  public String encryptKey(@QueryParam("text") String text) throws Exception {
    KeyStore keyStore = SSL.loadKeyStoreFromFile(BASE_PATH + "keys.jks", "sporades");
    X509Certificate certificate = ((X509Certificate) keyStore.getCertificate("root"));
    PublicKey pubKey = certificate.getPublicKey();

    String encrypted = SSL.encryptTextWithKey(pubKey, text);

    return encrypted;
  }

  @GET
  @Path("encrypt")
  @Produces(MediaType.TEXT_PLAIN)
  public String encrypt(@QueryParam("text") String text) throws Exception {
    final String ALIAS = "root.pk";

    KeyStore keyStore = SSL.loadKeyStoreFromFile(BASE_PATH + "keys.jks", "sporades");
    PrivateKey privateKey = (PrivateKey) keyStore.getKey(ALIAS, "sporades".toCharArray());
    X509Certificate certificate = ((X509Certificate) keyStore.getCertificate(ALIAS));
    PublicKey pubKey = certificate.getPublicKey();

    String _key_ = "531931c789eec8a84cd232a44744e68db9eb722963ca25a501b56b15e24dc841e5904e53cdbca05f0fcdf0b773bd059458fdbc4c7b84cf9fa53a5e2995fe1407964eb3383571f579cc9984700069e0d2c13332f3f5d86f89d8c0b8f28702201d2017b1b7c8641fef2cf06072b6f4278941e8a62711b4c86fa7e2b8658cd4018660bb62935c0a641a8b54a483322a211e60bfdcd2d0ae2e070a6cf43935284d007222835db9e523ad172571aa99a75fa84e3809b51095565272cd08dcc9a22ff8efd24e34155c692da86c113b592c54dd89cfb9837ef17c341274e345d003e9637a2723ba147deac781eafd1c5e70a5b5552175293fb77f4338cd73f16400c45f835ee82f90db937d067c666782d10bedb437b7d055c018c89918a807cd41708d898b3a0424e939073390618e07f835d0203f043e266a77d2cce29fff1d60e100b5919d811d8725446f7bec0b0a2166b9f14362e22f3054586749232015913a85e2ca16b4b2b66620fb0ada9b7b1cbc716d8ebddee384f965371ba36b277224ee2f7e0012ecd9049b4528eae1ce00f86bfb7928edbcfffe8ade23e6c66d239383091f59e3b6341019cc02688de708c32dfb8a74be40e2321c84e203f4d195accaa192c598b615cfc374ad5328ce2eab568fdc2183c93bf979e560fef78d18adf5ed9a056c92ba4ff89c111100b49a8129115fd2ec88dcc61d940a6851e64c95d3";

    String encrypted = SSL.encryptTextWithKey(pubKey, text + " " + SSL.decryptTextWithKey(privateKey, _key_));

    return encrypted;
  }
}
