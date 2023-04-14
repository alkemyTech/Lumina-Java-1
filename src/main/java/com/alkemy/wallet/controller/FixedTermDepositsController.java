package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.FixedTermDepositsDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.FixedTermDeposits;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.repository.UserRepository;
import com.alkemy.wallet.security.util.JwTUtil;
import com.alkemy.wallet.service.FixedTermDepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/fixedDeposit")
public class FixedTermDepositsController {
    private final String authorization="Authorization";
    private JwTUtil jwTUtil;
    private UserRepository userAux;
    @Autowired
    private FixedTermDepositsService fixedTermDepositsService;

    @PostMapping
    public ResponseEntity<?> createFixedTermDeposits(HttpServletRequest request, @Validated @RequestBody FixedTermDepositsDTO fixedTermDepositsDTO) throws Exception {
        final String authorizationHeader = request.getHeader(authorization);
        String username = null;
        String jwt = null;
        try{
            jwt = authorizationHeader.substring(7);
            username = jwTUtil.extractUsername(jwt);
            User user = userAux.findOneByEmail(username);
            Long userId = user.getId();
            List<Account> accounts=user.getAccounts();
            Long accountId=accounts.get(0).getId();
            return  fixedTermDepositsService.createFixedTermDeposits(userId,accountId,fixedTermDepositsDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulate(@RequestBody FixedTermDepositsDTO fixedTermDepositsDTO){
        return fixedTermDepositsService.simulate(fixedTermDepositsDTO);
    }
}
