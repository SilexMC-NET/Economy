package me.imjaxs.silexmc.economy.objects;

import java.math.BigDecimal;
import java.util.UUID;

public class EPlayer {
    private final String name;
    private final UUID uniqueID;
    private BigDecimal balance;

    public EPlayer(String name, UUID uniqueID) {
        this(name, uniqueID, 0.0);
    }

    public EPlayer(String name, UUID uniqueID, double balance) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.balance = new BigDecimal(balance);
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
