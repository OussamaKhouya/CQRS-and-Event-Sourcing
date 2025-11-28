package ma.enset.cqrseventsourcing.command.dtos;

public record AddNewAccountRequestDTO(
        String currency,
        double initialBalance) {
}
