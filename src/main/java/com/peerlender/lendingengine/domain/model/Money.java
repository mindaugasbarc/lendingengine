package com.peerlender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public final class Money {

    @Id
    @GeneratedValue
    private long id;

    private Currency currency;
    private double amount;
    public static final Money ZERO = new Money(0, Currency.USD);

    public Money() {
    }

    public Money(double amount, Currency currency) {
        this.currency = currency;
        this.amount = amount;
    }

    public Money add(final Money money) {
        if (currency != money.getCurrency()) {
            throw new IllegalArgumentException();
        }
        return new Money(amount + money.getAmount(), currency);
    }

    public Money minus(final Money money) {
        if (currency != money.getCurrency() || amount < money.getAmount()) {
            throw new IllegalArgumentException();
        }
        return new Money(amount - money.getAmount(), currency);
    }

    public Money times(final double multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
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
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
