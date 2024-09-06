package com.petrov.databases.controller;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.service.client.ClientDetail;
import com.petrov.databases.service.client.ClientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
@AllArgsConstructor
public class ClientController {
    @Autowired
    UserDetailsService userDetailsService;


    private ClientService clientService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") ClientDTO clientDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") ClientDTO clientDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", clientDTO);
            return "register"; // Возвращаем имя представления с формой регистрации
        }
        clientDTO.setRole("USER");
        clientService.saveClient(clientDTO);// Назначение роли

        model.addAttribute("message", "Registration successful!");
        return "register";
    }


    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable long id, HttpSession session) {
        return clientService.getClient(id);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
