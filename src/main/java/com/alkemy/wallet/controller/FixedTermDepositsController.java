package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.FixedTermDepositsDTO;
import com.alkemy.wallet.entity.FixedTermDeposits;
import com.alkemy.wallet.service.FixedTermDepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fixedDeposit")
public class FixedTermDepositsController {

    @Autowired
    private FixedTermDepositsService fixedTermDepositsService;

    @PostMapping("/{userId}/{accountId}")
    public ResponseEntity<?> createFixedTermDeposits(@PathVariable Long userId,@PathVariable Long accountId,@Validated @RequestBody FixedTermDepositsDTO fixedTermDepositsDTO) throws Exception {
        return  fixedTermDepositsService.createFixedTermDeposits(userId,accountId,fixedTermDepositsDTO);
    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulate(@RequestBody FixedTermDepositsDTO fixedTermDepositsDTO){
        return fixedTermDepositsService.simulate(fixedTermDepositsDTO);
    }
}
