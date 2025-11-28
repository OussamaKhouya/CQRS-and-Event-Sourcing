package ma.enset.cqrseventsourcing.events;

import ma.enset.cqrseventsourcing.enums.AccountStatus;

public record AccountActivatedEvent(String accountId,AccountStatus accountStatus) {
}
