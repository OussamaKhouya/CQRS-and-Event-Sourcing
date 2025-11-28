package ma.enset.cqrseventsourcing.events;

public record AccountCreditedEvent(String accountId, double amount,String currency) {
}
