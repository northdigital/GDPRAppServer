package gr.logismos.gdpr;

import javax.ws.rs.ApplicationPath;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.security.Security;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {
  public AppConfig() {
    Security.addProvider(new BouncyCastleProvider());
  }
}
