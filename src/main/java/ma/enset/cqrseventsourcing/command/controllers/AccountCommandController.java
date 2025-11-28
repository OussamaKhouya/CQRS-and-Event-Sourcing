package ma.enset.cqrseventsourcing.command.controllers;

import lombok.RequiredArgsConstructor;
import ma.enset.cqrseventsourcing.command.commands.AddAccountCommand;
import ma.enset.cqrseventsourcing.command.dtos.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {

    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    private CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO requestDTO) {
        CompletableFuture<String> response= commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                requestDTO.currency(),
                requestDTO.initialBalance()
        ));
        return response;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception  exception){
        return exception.getMessage();
    }
}
