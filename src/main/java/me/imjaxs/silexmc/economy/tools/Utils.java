package me.imjaxs.silexmc.economy.tools;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    public static String format(double value) {
        BigDecimal balance = new BigDecimal(value);
        if (balance.compareTo(new BigDecimal(1000)) < 0)
            return String.valueOf(balance);

        BigDecimal var2 = null;
        BigDecimal var3 = null;

        String symbol = "";
        String format = "";
        if (balance.compareTo(new BigDecimal("1000000000000000000000000000")) > 0 || balance.compareTo(new BigDecimal("1000000000000000000000000000")) == 0) {
            var2 = new BigDecimal(100000000000000000L);
            var3 = new BigDecimal("1.0E10");

            symbol = "OC";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal("1000000000000000000000000")) > 0 || balance.compareTo(new BigDecimal("1000000000000000000000000")) == 0) {
            var2 = new BigDecimal(1000000000000000L);
            var3 = new BigDecimal("1.0E9");

            symbol = "SP";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal("1000000000000000000000")) > 0 || balance.compareTo(new BigDecimal("1000000000000000000000")) == 0) {
            var2 = new BigDecimal(10000000000000L);
            var3 = new BigDecimal("1.0E8");

            symbol = "SX";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000000000000000000L)) > 0 || balance.compareTo(new BigDecimal(1000000000000000000L)) == 0) {
            var2 = new BigDecimal(100000000000L);
            var3 = new BigDecimal("1.0E7");

            symbol = "QT";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000000000000000L)) > 0 || balance.compareTo(new BigDecimal(1000000000000000L)) == 0) {
            var2 = new BigDecimal(1000000000L);
            var3 = new BigDecimal("1000000.0");

            symbol = "Q";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000000000000L)) > 0 || balance.compareTo(new BigDecimal(1000000000000L)) == 0) {
            var2 = new BigDecimal(100000000);
            var3 = new BigDecimal("10000.0");

            symbol = "T";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000000000L)) > 0 || balance.compareTo(new BigDecimal(1000000000L)) == 0) {
            var2 = new BigDecimal(1000000);
            var3 = new BigDecimal("1000.0");

            symbol = "B";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000000)) > 0 || balance.compareTo(new BigDecimal(1000000)) == 0) {
            var2 = new BigDecimal(10000);
            var3 = new BigDecimal("100.0");

            symbol = "M";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
        } else if (balance.compareTo(new BigDecimal(1000)) > 0 || balance.compareTo(new BigDecimal(1000)) == 0) {
            var2 = new BigDecimal(100);
            var3 = new BigDecimal("10.0");

            symbol = "K";
            format = String.valueOf(balance.divide(var2).divide(var3).setScale(1, RoundingMode.HALF_UP));
            return format + symbol;
        }
        return format + symbol;
    }
}
