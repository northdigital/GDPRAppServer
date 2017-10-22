package gr.northdigital.gdpr.models;

import java.util.UUID;

public class GTicket {
  public String ticket;

  public GTicket() {
    this.ticket = UUID.randomUUID().toString();
  }
}
