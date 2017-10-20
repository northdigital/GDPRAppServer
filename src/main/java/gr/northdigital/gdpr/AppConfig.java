package gr.northdigital.gdpr;

import javax.ws.rs.ApplicationPath;

import gr.northdigital.gdpr.models.GAccessTicket;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationPath("/")
public class AppConfig extends ResourceConfig {
  private static ArrayList<GAccessTicket> _gAccessTickets = new ArrayList<>();
  public List<GAccessTicket> gAccessTickets = Collections.synchronizedList(_gAccessTickets);

  public AppConfig() {
    Security.addProvider(new BouncyCastleProvider());
  }
}
