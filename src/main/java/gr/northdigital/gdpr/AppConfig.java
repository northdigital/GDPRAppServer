package gr.northdigital.gdpr;

import javax.ws.rs.ApplicationPath;

import gr.northdigital.gdpr.models.GSession;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {
  private static ArrayList<GSession> _sessions = new ArrayList<>();
  public static List<GSession> sessions = Collections.synchronizedList(_sessions);

  public AppConfig() {
    Security.addProvider(new BouncyCastleProvider());
  }
}
