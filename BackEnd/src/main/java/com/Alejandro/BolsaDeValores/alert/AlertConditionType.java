package com.Alejandro.BolsaDeValores.alert;

public enum AlertConditionType {
    ABOVE("Preço acima de"),
    BELOW("Preço abaixo de"),
    POSITIVE_VARIATION("Variação positiva acima de"),
    NEGATIVE_VARIATION("Variação negativa abaixo de");

    private final String description;

    AlertConditionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

