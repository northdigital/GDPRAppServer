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

  /**
   * Κάθε πρόσβαση στην list πρέπει να γίνεται με κλήση μεθόδων της sessions για να είναι thread safe h list.
   * Σε κάθε άλλη περίπτωση πρέπει να γίνει χρήση της syncronize.
   * Ας πούμε αν χρησιμοποιήσουμε έναν iterator αυτός δεν συγχρονίζεται οπότε ενώ είμαστε σε κάποιο node μπορεί
   * να τον πειράξει και κάποιο άλλο thread οπότε πρέπει να συγχρονίσουμε την πρόσβαση με χρήση της syncronize.
   */
  public static List<GSession> sessions = Collections.synchronizedList(_sessions);

  public AppConfig() {
    Security.addProvider(new BouncyCastleProvider());
  }
}
