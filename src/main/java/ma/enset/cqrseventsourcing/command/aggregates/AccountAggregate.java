package ma.enset.cqrseventsourcing.command.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.cqrseventsourcing.command.commands.AddAccountCommand;
import ma.enset.cqrseventsourcing.enums.AccountStatus;
import ma.enset.cqrseventsourcing.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    //@Id
    private String accountId ;
    private double currentBalance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }
    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("CreateAccount Command Received");
        if (command.getInitialBalance()<0) throw  new IllegalArgumentException("Balance negative exception");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));

    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent occured");
        this.accountId =event.getAccountId();
        this.currentBalance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getAccountStatus();
    }
}
