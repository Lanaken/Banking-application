package com.petrov.databases.controller.credit;

import com.petrov.databases.dto.DebtByMonthDto;
import com.petrov.databases.entity.Client;
import com.petrov.databases.service.client.ClientService;
import com.petrov.databases.service.credit.CreditPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@AllArgsConstructor
public class DebtPerMonthController {
    private CreditPaymentService creditPaymentService;
    private ClientService clientService;

    @GetMapping("/debt-per-month")
    public String returnDebtByMonthForAllAccounts(Principal principal, Model model) {
        Client client = clientService.getClientByEmail(principal.getName());
        List<DebtByMonthDto> debtByMonthDtoList = creditPaymentService.getDebtByMonths(client.getId());

        List<String> monthsWithYear = debtByMonthDtoList.stream()
                .map(item -> STR."\{item.getYear()}-\{String.format("%02d", Integer.parseInt(item.getMonth()))}")
                .collect(Collectors.toList());

        List<BigDecimal> paymentSums = debtByMonthDtoList.stream().map(DebtByMonthDto::getDebt).toList();

        model.addAttribute("monthsWithYear", monthsWithYear);
        model.addAttribute("paymentSums", paymentSums);

        return "debt-per-month"; // Возвращаем шаблон Thymeleaf
    }

    @GetMapping("/debit/{accountId}/debt-per-month")
    public String returnDebtByMonthForAllAccounts(Principal principal, Model model, @PathVariable("accountId") String accountId) {
        Client client = clientService.getClientByEmail(principal.getName());
        List<DebtByMonthDto> debtByMonthDtoList = creditPaymentService.getDebtByMonthsForAccount(Long.parseLong(accountId));

        List<String> monthsWithYear = debtByMonthDtoList.stream()
                .map(item -> STR."\{item.getYear()}-\{String.format("%02d", Integer.parseInt(item.getMonth()))}")
                .collect(Collectors.toList());

        List<BigDecimal> paymentSums = debtByMonthDtoList.stream().map(DebtByMonthDto::getDebt).toList();

        model.addAttribute("monthsWithYear", monthsWithYear);
        model.addAttribute("paymentSums", paymentSums);

        return "debt-per-month"; // Возвращаем шаблон Thymeleaf
    }
}
