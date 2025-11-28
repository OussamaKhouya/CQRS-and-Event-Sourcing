package ma.enset.cqrseventsourcing.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.enset.cqrseventsourcing.enums.AccountStatus;

@Getter @AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private String currency;
    private AccountStatus accountStatus;
}
