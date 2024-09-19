package com.petrov.databases.controller.credit;

import com.petrov.databases.entity.credit.Credit;
import com.petrov.databases.entity.credit.CreditPayment;
import com.petrov.databases.service.credit.CreditPaymentService;
import com.petrov.databases.service.credit.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@AllArgsConstructor
public class PaymentController {
    private CreditService creditService;
    private CreditPaymentService creditPaymentService;

    @GetMapping("account/{accountId}/credit/{creditId}/payments")
    public String getPaymentsPage(Model model, Principal principal, @PathVariable("creditId") String creditId) {
        List<CreditPayment> creditPayments = creditPaymentService.getCreditPaymentsByCreditId(creditId);
        model.addAttribute("payments", creditPayments);
        model.addAttribute("creditId", creditId);
        return "payments";
    }
}
