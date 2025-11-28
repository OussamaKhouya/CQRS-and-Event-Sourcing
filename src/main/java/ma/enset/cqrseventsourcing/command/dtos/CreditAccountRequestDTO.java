package ma.enset.cqrseventsourcing.command.dtos;

public record CreditAccountRequestDTO(String accountId, double amount,String currency) {
}
