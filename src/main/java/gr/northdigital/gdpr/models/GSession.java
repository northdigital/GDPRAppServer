package gr.northdigital.gdpr.models;

import java.util.Date;

public class GSession {
  public GTicket ticket;
  public byte[] symmetricKey;
  public String subjectDN;
  public Date createdAt;
  public Date lastAccessedAt;
}
