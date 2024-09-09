package com.petrov.databases.dto.client;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ClientDTO {
    private Long id;
    private Set<Long> debitAccountIds;
    private String firstName;
    private String middleName;
    private String lastName;
    // Сделать валидацию
    private LocalDate dateOfBirth;
    @Size(min = 4, max = 4)
    @Pattern(regexp = "\\d{4}", message = "Номер паспорта должен содержать только цифры")
    private String passportSeries;
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должен состоять из 6 цифр")
    private String passportNumber;
    private String password;
    private String email;
    private String role;
}
