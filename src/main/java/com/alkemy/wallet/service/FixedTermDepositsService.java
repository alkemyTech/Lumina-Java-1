package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.FixedTermDepositsDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.FixedTermDeposits;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.mapping.FixedTermDepositsMapping;
import com.alkemy.wallet.repository.FixedTermDepositsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class FixedTermDepositsService {
    private Float interestPercent=0.5F;
    @Autowired
    private FixedTermDepositsRepository fixedTermDepositsRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;


    public ResponseEntity<?> createFixedTermDeposits(Long accountId, Long userId,FixedTermDepositsDTO fixedTermDepositsDTO) throws Exception {
        Account account = accountService.findById(accountId);
        User user = userService.findById(userId);

        UserExist(user);
        AccountExist(account);
        validateBalance(account.getBalance(),fixedTermDepositsDTO.getAmount());
        validateClosingDate(fixedTermDepositsDTO);
        accountService.discount(account,fixedTermDepositsDTO.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(generateFixedTermDeposit(account,fixedTermDepositsDTO));
    }

    private void validateClosingDate(FixedTermDepositsDTO fixedTermDepositsDTO) {
        LocalDateTime creationDate = fixedTermDepositsDTO.getCreationDate();
        LocalDateTime closingDate = fixedTermDepositsDTO.getClosingDate();
        Duration duration = Duration.between(creationDate, closingDate);
        long daysDifference = duration.toDays();
        if (daysDifference < 30) {
            throw new IllegalArgumentException("No se permite realizar plazos fijos con una cantidad de dias menor a 30 dias");
        }
    }

    private FixedTermDepositsDTO generateFixedTermDeposit(Account account, FixedTermDepositsDTO fixedTermDepositsDTO) {
        Duration duration = Duration.between(fixedTermDepositsDTO.getCreationDate(), fixedTermDepositsDTO.getClosingDate());
        long daysDifference = duration.toDays();
        Double tasaFija = calculoTasaFija(fixedTermDepositsDTO.getAmount(),this.interestPercent,daysDifference);

        FixedTermDeposits fixedTermDeposits = FixedTermDeposits.builder()
                .amount(fixedTermDepositsDTO.getAmount())
                .interest(tasaFija)
                .creationDate(fixedTermDepositsDTO.getCreationDate())
                .closingDate(fixedTermDepositsDTO.getClosingDate())
                .account(account)
                .build();
        fixedTermDepositsRepository.save(fixedTermDeposits);
        return FixedTermDepositsMapping.convertEntityToDto(fixedTermDeposits);
    }
    //Interés = Monto del plazo fijo * Tasa de interés * (Período de tiempo en días / Días en el año)
    private Double calculoTasaFija(Double amount, Float interestPercent,Long days) {
        Double interes = amount * interestPercent * (days / 365.0);
        return interes;
    }

    private void validateBalance(Double balance, Double amount) throws Exception {
        if(balance < amount){
            throw new Exception("Dinero insuficiente");
        }
    }

    public void UserExist(User user) throws Exception {
        if(user == null){
            throw new Exception("Usuario inexistente");
        }
    }

    public void AccountExist(Account account) throws Exception {
        if(account == null){
            throw new Exception("Cuenta inexistente");
        }
    }
}
