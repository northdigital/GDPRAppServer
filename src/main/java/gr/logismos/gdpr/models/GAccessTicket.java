package gr.logismos.gdpr.models;

import java.util.Date;

public class GAccessTicket {
  public String ticket;
  public String issuer;
  public Date issuedAt;
  public Date expiresAt;
  public Date lastAccessedAt;
  public Integer autoRefreshInMin;
}
