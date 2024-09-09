package com.petrov.databases.entity.debitcard;

public enum PaymentSystem {
    MIR,
    MASTERCARD,
    VISA;

    public static PaymentSystem fromString(String dayString) {
        if (dayString == null || dayString.isEmpty()) {
            throw new IllegalArgumentException("Значение не может быть null или пустым");
        }

        for (PaymentSystem day : PaymentSystem.values()) {
            if (day.name().equalsIgnoreCase(dayString)) {
                return day;
            }
        }

        throw new IllegalArgumentException("Неверное значение для enum: " + dayString);
    }

}
