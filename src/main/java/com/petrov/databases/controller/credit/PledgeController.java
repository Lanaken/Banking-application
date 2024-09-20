package com.petrov.databases.controller.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.dto.credit.PledgeDto;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.mapper.credit.CreditMapper;
import com.petrov.databases.mapper.credit.PledgeMapper;
import com.petrov.databases.mapper.debitaccount.DebitAccountMapper;
import com.petrov.databases.service.client.ClientService;
import com.petrov.databases.service.credit.CreditService;
import com.petrov.databases.service.pledge.PledgeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class PledgeController {
    private static final Logger log = LoggerFactory.getLogger(CreditController.class);
    private PledgeService pledgeService;
    private CreditService creditService;
    private ClientService clientService;
    private CreditMapper creditMapper;
    private DebitAccountMapper debitAccountMapper;
    private PledgeMapper pledgeMapper;

    @GetMapping("/account/{accountId}/pledge-credit")
    public String viewAccountDetails(@PathVariable String accountId, Model model, Principal principal, @ModelAttribute("credit") CreditDTO creditDTO) {
        if (accountId != null && !accountId.equals("0")) {
            Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
            DebitAccount debitAccount = client.getDebitAccounts().stream()
                    .filter(account -> account.getId().equals(Long.parseLong(accountId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            List<CreditDTO> creditDTOS = debitAccount.getCredits().stream()
                    .filter(credit -> credit.getPledge() != null)
                    .map(creditMapper::creditToCreditDto)
                    .toList();
            List<PledgeDto> pledges = creditDTOS
                    .stream()
                    .map(credit ->
                            pledgeMapper.pledgeToPledgeDto(pledgeService.getPledge(credit.getPledgeId()))).toList();

            model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
            model.addAttribute("credits", creditDTOS);
            model.addAttribute("pledges", pledges);
            model.addAttribute("pledge", new PledgeDto());
        }

        return "pledge-credit-details";
    }

    @PostMapping("/account/{accountId}/pledge-credit")
    public String openCredit(
            @Valid @ModelAttribute("pledge") PledgeDto pledgeDto,
            @Valid @ModelAttribute("credit") CreditDTO creditDTO,
            BindingResult result,
            Model model,
            Long accountId,
            Principal principal
    ) {
        if (result.hasErrors()) {
            log.error("BindingResult has error for openCredit");

            Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
            DebitAccount debitAccount = client.getDebitAccounts().stream()
                    .filter(account -> account.getId().equals(accountId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            List<CreditDTO> creditDTOS = debitAccount.getCredits().stream()
                    .map(creditMapper::creditToCreditDto)
                    .toList();

            model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
            model.addAttribute("credits", creditDTOS);
            model.addAttribute("creditDTO", creditDTO);

            // Возвращаем ту же страницу с ошибками
            return "pledge-credit-details";
        }
        creditService.openPledgeCredit(accountId, creditDTO, pledgeDto);
        return STR."redirect:/account/\{accountId}/pledge-credit";
    }
}
