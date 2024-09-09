package com.petrov.databases.controller.credit;


import com.petrov.databases.dto.credit.CreditDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.service.client.ClientService;
import com.petrov.databases.service.credit.CreditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
@AllArgsConstructor
public class CreditController {
    private ClientService clientService;
    private CreditService creditService;

    @GetMapping("/credit")
    public String getCreditPage(Principal principal, Model model) {
        Client client = clientService.getClientByEmail(principal.getName());
        model.addAttribute("client", client);
        return "credit";
    }

    @PostMapping("/credit/{account}")
    public String openCredit(BindingResult result, Model model, @Valid CreditDTO creditDTO, @PathVariable String accountNumber) {
        if (result.hasErrors()) {
            model.addAttribute("creditDTO", creditDTO);
            return "credit"; // Возвращаем ту же форму, если есть ошибки
        }
        Credit credit = creditService.openCredit(accountNumber, creditDTO);
        model.addAttribute("creditDTO", credit);
        return "credit";
    }
}
