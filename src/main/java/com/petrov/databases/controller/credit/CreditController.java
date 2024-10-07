package com.petrov.databases.controller.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.mapper.credit.CreditMapper;
import com.petrov.databases.mapper.debitaccount.DebitAccountMapper;
import com.petrov.databases.service.client.ClientService;
import com.petrov.databases.service.credit.CreditService;
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
public class CreditController {
    private static final Logger log = LoggerFactory.getLogger(CreditController.class);
    private ClientService clientService;
    private CreditService creditService;
    private CreditMapper creditMapper;
    private DebitAccountMapper debitAccountMapper;

    @GetMapping("/account")
    public String getCreditPage(Principal principal, Model model) {
        Client client = clientService.getClientByEmail(principal.getName());
        model.addAttribute("client", client);
        List<DebitAccount> debitAccounts = client.getDebitAccounts().stream().toList();
        model.addAttribute("accounts", debitAccounts);
        return "credit";
    }

    @GetMapping("/account/{accountId}/credit")
    public String viewAccountDetails(@PathVariable String accountId, Model model, Principal principal, @ModelAttribute("credit") CreditDTO creditDTO) {
        if (accountId != null && !accountId.equals("0")) {
            Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
            DebitAccount debitAccount = client.getDebitAccounts().stream()
                    .filter(account -> account.getId().equals(Long.parseLong(accountId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            // Передаём информацию о счетах и картах
            List<CreditDTO> creditDTOS = debitAccount.getCredits().stream()
                    .map(creditMapper::creditToCreditDto)
                    .toList();

            model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
            model.addAttribute("credits", creditDTOS);
        }

        return "credit-details";  // шаблон для просмотра деталей счёта и карт
    }

    @PostMapping("/account/{accountId}/credit")
    public String openCredit(@Valid @ModelAttribute("credit") CreditDTO creditDTO, BindingResult result, Model model, Long accountId, Principal principal) {
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

            return "credit-details";
        }
        creditService.openCredit(accountId, creditDTO);
        return STR."redirect:/account/\{accountId}/credit";
    }
}
