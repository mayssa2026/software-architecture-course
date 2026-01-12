package transferclient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import transferclient.TransferService;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public String transfer(@RequestParam Long checkingId,
                           @RequestParam Long savingId,
                           @RequestPara