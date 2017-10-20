package gr.northdigital.gdpr.utils;

import gr.northdigital.gdpr.models.GAccessTicket;

import java.util.Date;
import java.util.UUID;

public class GTicketGenerator {
  public static GAccessTicket generate(String issuer, Integer autoRefreshInMin) {
    GAccessTicket retVal = new GAccessTicket();

    Date now = new Date();

    retVal.issuer = issuer;
    retVal.issuedAt = now;
    retVal.lastAccessedAt = now;
    retVal.autoRefreshInMin = autoRefreshInMin;
    retVal.expiresAt = autoRefreshInMin != null ? new Date(now.getTime() + (autoRefreshInMin * 60) * 1000) : null;
    retVal.ticket = UUID.randomUUID().toString();

    return retVal;
  }
}
