package com.petrov.databases.entity;


import com.petrov.databases.entity.debitaccount.DebitAccount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode
// Ограничение сработает только если хибер будет создавать таблицу
@Table(schema = "test", uniqueConstraints = { @UniqueConstraint(columnNames = { "passport_series", "passport_number" }) })
public class Client {

    protected Client() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    // @JoinColumn(name = "client_id")
    private Set<DebitAccount> debitAccount;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, updatable = false)
    private LocalDate dateOfBirth;

    @Size(min = 4, max = 4)
    @Column(nullable = false)
    private String passportSeries;

    @Size(min = 6, max = 6)
    @Column(nullable = false)
    private String passportNumber;

    @Column(nullable = false)
    @Setter
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private String role;

}
