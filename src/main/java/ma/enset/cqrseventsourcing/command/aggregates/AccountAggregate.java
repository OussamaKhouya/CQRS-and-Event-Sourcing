package ma.enset.cqrseventsourcing.command.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.cqrseventsourcing.command.commands.AddAccountCommand;
import ma.enset.cqrseventsourcing.command.commands.CreditAccountCommand;
import ma.enset.cqrseventsourcing.enums.AccountStatus;
import ma.enset.cqrseventsourcing.events.AccountActivatedEvent;
import ma.enset.cqrseventsourcing.events.AccountCreatedEvent;
import ma.enset.cqrseventsourcing.events.AccountCreditedEvent;
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
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));


    }
    @CommandHandler
    public void handleCommand(CreditAccountCommand command){
        log.info("CreditAccountCommand Command Received");
        if (!status.equals(AccountStatus.ACTIVATED)) throw  new RuntimeException("This account can not be debited because of the account is not activated. The current status is "+status);
        if(command.getAmount()<0) throw  new IllegalArgumentException("Amount negative exception");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent occurred");
        this.accountId =event.getAccountId();
        this.currentBalance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getAccountStatus();
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("AccountActivatedEvent occurred");
        this.accountId =event.accountId();
        this.status = event.accountStatus();
    }
    @EventSourcingHandler
    //@EventHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent occurred");
        this.accountId =event.accountId();
        this.currentBalance = this.currentBalance + event.amount();
    }
}
