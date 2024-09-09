package com.petrov.databases.controller;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.dto.debit.DebitAccountDTO;
import com.petrov.databases.dto.debit.DebitCardDto;
import com.petrov.databases.entity.Client;
import com.petrov.databases.entity.debitaccount.DebitAccount;
import com.petrov.databases.entity.debitcard.PaymentSystem;
import com.petrov.databases.mapper.debitaccount.DebitAccountMapper;
import com.petrov.databases.mapper.debitaccount.DebitCardMapper;
import com.petrov.databases.service.BankingService;
import com.petrov.databases.service.client.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DebitController {
    @Autowired
    UserDetailsService userDetailsService;
    private BankingService bankingService;
    private ClientService clientService;
    private DebitAccountMapper debitAccountMapper;
    private DebitCardMapper debitCardMapper;

    @GetMapping("/debit")
    public String getDebitAccountPage(Model model, Principal principal) {
        Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
        Set<DebitAccount> debitAccounts = client.getDebitAccounts();

        // Передаем список счетов в модель для отображения в выпадающем списке
        List<DebitAccountDTO> debitAccountDTOS = debitAccounts
                .stream()
                .map(debitAccountMapper::debitAccountToDebitAccountDto)
                .collect(Collectors.toList());

        model.addAttribute("accounts", debitAccountDTOS);

        return "debit";  // шаблон для страницы выбора счёта и выпуска новой карты
    }

    private Map<Long, List<DebitCardDto>> getCardsByAccount(Set<DebitAccount> debitAccounts) {
        return debitAccounts
                .stream()
                .collect(Collectors.toMap(
                        DebitAccount::getId,
                        debitAccount -> debitAccount
                                .getDebitCards()
                                .stream()
                                .map(debitCardMapper::debitCardToDebitCardDto)
                                .collect(Collectors.toList())
                ));
    }

    // Выпуск новой карты для выбранного счета
    @PostMapping("/debit/new-card")
    public String openDebitAccount(String paymentSystem, Principal principal) throws Exception {
        ClientDTO clientDTO = clientService.getClientDtoByEmail(principal.getName());
        Long accountId = bankingService.openDebitCardWithNewCreditAccountNumber(PaymentSystem.fromString(paymentSystem), clientDTO);

        // После выпуска карты перенаправляем на просмотр счёта с картами
        return STR."redirect:/debit/\{accountId}";
    }

    @PostMapping("/debit/{accountId}")
    public String openNewDebitCard(String paymentSystem, Principal principal, Long accountId) throws Exception {
        ClientDTO clientDTO = clientService.getClientDtoByEmail(principal.getName());
        bankingService.openDebitCard(PaymentSystem.fromString(paymentSystem), accountId);

        // После выпуска карты перенаправляем на просмотр счёта с картами
        return STR."redirect:/debit/\{accountId}";
    }

    // Просмотр информации о конкретном счёте и связанных картах
    @GetMapping("/debit/{accountId}")
    public String viewAccountDetails(@PathVariable String accountId, Model model, Principal principal) {
        if (accountId != null && !accountId.equals("0")) {
            Client client = clientService.getClientByEmailWithDebitAccountsAndCards(principal.getName());
            DebitAccount debitAccount = client.getDebitAccounts().stream()
                    .filter(account -> account.getId().equals(Long.parseLong(accountId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            // Передаём информацию о счетах и картах
            List<DebitCardDto> debitCardDtos = debitAccount.getDebitCards().stream()
                    .map(debitCardMapper::debitCardToDebitCardDto)
                    .collect(Collectors.toList());

            model.addAttribute("account", debitAccountMapper.debitAccountToDebitAccountDto(debitAccount));
            model.addAttribute("cards", debitCardDtos);
        }

        return "account-details";  // шаблон для просмотра деталей счёта и карт
    }
}
