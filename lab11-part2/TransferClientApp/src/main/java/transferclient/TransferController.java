package transferclient;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public String transfer(@RequestParam Long fromChecking,
                           @RequestParam Long toSaving,
                           @RequestParam double amount,
                           @RequestParam(defaultValue = "false") boolean simulateError) {
        return transferService.transfer(fromChecking, toSaving, amount, simulateError);
    }
}
