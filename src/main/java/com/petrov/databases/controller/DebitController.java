package com.petrov.databases.controller;

import com.petrov.databases.service.debitaccount.DebitAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class DebitController {
    @Autowired
    UserDetailsService userDetailsService;
    private DebitAccountService debitAccountService;

    @GetMapping("/debitAccount")
    public String getDebitAccountPage() {
        return "debitAccount";
    }

//    @PostMapping("/debitAccount")
//    public String openNeDebitAccount(Model model, Principal principal) {
//        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
//    }
}
