package me.imjaxs.silexmc.economy.objects;

import java.util.UUID;

public class EPlayer {
    private final String name;
    private final UUID uniqueID;
    private double balance;

    public EPlayer(String name, UUID uniqueID) {
        this(name, uniqueID, 0.0);
    }

    public EPlayer(String name, UUID uniqueID, double balance) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
