package me.imjaxs.silexmc.economy.tools;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Utils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> colorize(List<String> strings) {
        return strings.stream().map(Utils::colorize).collect(Collectors.toList());
    }

    @SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
    public static String format(double value) {
        if (value < 1000L)
            return formatNumber(BigDecimal.valueOf(value));

        if (value < 1000000L)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000))) + "k";

        if (value < 1000000000L)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000))) + "M";

        if (value < 1000000000000L)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000))) + "B";

        if (value < 1000000000000000L)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000.0))) + "T";

        if (value < 1000000000000000000L)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000000.0))) + "Qd";

        if (value < 1000000000000000000000.0)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000000000.0))) + "Qt";

        if (value < 1000000000000000000000000.0)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000000000000.0))) + "Sx";

        if (value < 1000000000000000000000000000.0)
            return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000000000000000.0))) + "Sp";
        return formatNumber(BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000000000000000000000000.0))) + "O";
    }

    private static String formatNumber(BigDecimal value) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(value.doubleValue());
    }
}
