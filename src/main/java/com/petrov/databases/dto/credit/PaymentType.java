package com.petrov.databases.dto.credit;

import com.petrov.databases.entity.debitcard.PaymentSystem;

public enum PaymentType {
    ANNUITY,
    DIFFERENTIAL;

    public static PaymentType fromString(String dayString) {
        if (dayString == null || dayString.isEmpty()) {
            throw new IllegalArgumentException("Значение не может быть null или пустым");
        }

        for (PaymentType type : PaymentType.values()) {
            if (type.name().equalsIgnoreCase(dayString)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Неверное значение для enum: " + dayString);
    }
}
