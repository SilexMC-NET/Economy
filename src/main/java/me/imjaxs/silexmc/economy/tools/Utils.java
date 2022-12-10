package me.imjaxs.silexmc.economy.tools;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> colorize(List<String> strings) {
        return strings.stream().map(Utils::colorize).collect(Collectors.toList());
    }

    public static String format(double value) {
        if (value < 1000L)
            return formatNumber(value);

        if (value < 1000000L)
            return formatNumber(value / 1000L) + "K";

        if (value < 1000000000L)
            return formatNumber(value / 1000000L) + "M";

        if (value < 1000000000000L)
            return formatNumber(value / 1000000000L) + "B";

        if (value < 1000000000000000L)
            return formatNumber(value / 1000000000000L) + "T";

        if (value < 1000000000000000000L)
            return formatNumber(value / 1000000000000000L) + "Q";

        if (value < 1000000000000000000000.0)
            return formatNumber(value / 1000000000000000000L) + "QT";

        if (value < 1000000000000000000000000.0)
            return formatNumber(value / 1000000000000000000000.0) + "SX";

        if (value < 1000000000000000000000000000.0)
            return formatNumber(value / 1000000000000000000000000.0) + "SP";

        return formatNumber(value / 1000000000000000000000000000.0) + "OC";
    }
    private static String formatNumber(double d) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(d);
    }
}
