package com.petrov.databases.controller.credit;

import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.Client;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
@AllArgsConstructor
public class RefinancedCreditController {
    private CreditService creditService;
    private ClientService clientService;
    private CreditMapper creditMapper;
    private DebitAccountMapper debitAccountMapper;
    private static final Logger log = LoggerFactory.getLogger(CreditController.class);


    @PostMapping("/account/{accountId}/refinanced-credit")
    public String openRefinancedCredit(
            @Valid @ModelAttribute("credit") CreditDTO creditDTO,
            BindingResult result,
            Model model,
            Long accountId,
            Principal principal,
            @RequestParam(value = "refinancingCreditIds", required = false) List<String> refinancingCreditIds

    ) {
        String message = "";
        if (refinancingCreditIds == null || refinancingCreditIds.isEmpty())
            message = "Cant open refinancing credits without picked refinanced";
        if (result.hasErrors() || !message.isEmpty()) {
            log.error("BindingResult has error for openCredit");

            return prepareModelForRefinance(accountId, principal, creditDTO, model, message);

        }
        try {
            creditService.openCredit(accountId, creditDTO, refinancingCreditIds);
        }
        catch (RuntimeException exception) {
            model.addAttribute("message", exception.getMessage());
            return prepareModelForRefinance(accountId, principal, creditDTO, model, exception.getMessage());
        }
        return STR."redirect:/account/\{accountId}/refinanced-credit";
    }

    private String prepareModelForRefinance(Long accountId, Principal principal, CreditDTO creditDTO, Model model, String message) {
        Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
        DebitAccount debitAccount = client.getDebitAccounts().stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<CreditDTO> creditDTOS = debitAccount.getCredits().stream()
                .map(creditMapper::creditToCreditDto)
                .toList();
        List<CreditDTO> refinanced = creditDTOS
                .stream()
                .filter(credit -> !(credit.getRefinancingCreditId() == null)).toList();
        List<CreditDTO> refinancing = creditDTOS
                .stream()
                .filter(credit ->
                        refinanced
                                .stream()
                                .anyMatch(refin ->
                                        Objects.equals(refin.getRefinancingCreditId(), credit.getId()))).toList();
        List<CreditDTO> canBeRefinanced = creditDTOS
                .stream()
                .filter(
                        credit ->
                                !refinanced.contains(credit)
                                        && !refinancing.contains(credit)
                                        && credit.getPledgeId()
                                        == null
                )
                .toList();

        model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
        model.addAttribute("refinanced", refinanced);
        model.addAttribute("refinancing", refinancing);
        if (canBeRefinanced.isEmpty()) {
            model.addAttribute("message", "No credits available for refinancing.");
        }
        model.addAttribute("canBeRefinanced", canBeRefinanced);

        return "refinanced-credit-details";
    }



    @GetMapping("/account/{accountId}/refinanced-credit")
    public String viewAccountDetails(@PathVariable String accountId, Model model, Principal principal, @ModelAttribute("credit") CreditDTO creditDTO) {
        if (accountId != null && !accountId.equals("0")) {

            Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
            DebitAccount debitAccount = client.getDebitAccounts().stream()
                    .filter(account -> account.getId().equals(Long.parseLong(accountId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            List<CreditDTO> creditDTOS = debitAccount.getCredits().stream()
                    .map(creditMapper::creditToCreditDto)
                    .toList();
            List<CreditDTO> refinanced = creditDTOS
                    .stream()
                    .filter(credit -> !(credit.getRefinancingCreditId() == null)).toList();
            List<CreditDTO> refinancing = creditDTOS
                    .stream()
                    .filter(credit ->
                            refinanced
                                    .stream()
                                    .anyMatch(refin ->
                                            Objects.equals(refin.getRefinancingCreditId(), credit.getId()))).toList();
            List<CreditDTO> canBeRefinanced = creditDTOS
                    .stream()
                    .filter(
                            credit ->
                                    !refinanced.contains(credit)
                                            && !refinancing.contains(credit)
                                            && credit.getPledgeId()
                                            == null
                    )
                    .toList();

            model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
            model.addAttribute("refinanced", refinanced);
            model.addAttribute("refinancing", refinancing);
            if (canBeRefinanced.isEmpty()) {
                model.addAttribute("message", "No credits available for refinancing.");
            }
            model.addAttribute("canBeRefinanced", canBeRefinanced);
        }

        return "refinanced-credit-details";
    }
}
