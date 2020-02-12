package com.peerlender.lendingengine.domain.model;

import java.util.Objects;

public class Money {

    private final double amount;
    private final Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money money) {
        if (currency != money.getCurrency()) {
            throw new IllegalArgumentException();
        }
        return new Money(amount + money.getAmount(), currency);
    }

    public Money minus(Money money) {
        if (currency != money.getCurrency() || amount < money.getAmount()) {
            throw new IllegalArgumentException();
        }
        return new Money(amount - money.getAmount(), currency);
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0 &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
