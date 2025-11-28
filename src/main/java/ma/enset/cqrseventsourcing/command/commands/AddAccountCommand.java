package ma.enset.cqrseventsourcing.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class AddAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private String currency;
    private double initialBalance;
}
