///usr/bin/env jbang "$0" "$@" ; exit $?

/*
 * Public domain - no really, snippets should be free for all.
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Please, give me a year!");
            return;
        }

        LocalDate[] dates = new LocalDate[4];
        dates[0] = LocalDate.of(Integer.valueOf(args[0]), Month.JANUARY, 17);
        dates[1] = LocalDate.of(Integer.valueOf(args[0]), Month.APRIL, 17);
        dates[2] = LocalDate.of(Integer.valueOf(args[0]), Month.JULY, 17);
        dates[3] = LocalDate.of(Integer.valueOf(args[0]), Month.OCTOBER, 17);

        var formatter = DateTimeFormatter.ofPattern("MM");

        for (LocalDate date : dates) {
            Month month = date.getMonth();
            var next = date.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
            var previous = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));

            var diff0 = next.getDayOfYear() - date.getDayOfYear();
            var diff1 = date.getDayOfYear() - previous.getDayOfYear();

            String movedTo = "releasing later";
            LocalDate when = next;
            if (diff1 <= diff0) {
                when = previous;
                movedTo = "releasing earlier";
            }

            System.err.println("The CPU for " + month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                               date.getYear() +
                               " will be released on the " + when.get(ChronoField.DAY_OF_MONTH) +
                               " (target "  + date + " is a " +
                               date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) +
                               ", " + movedTo +")");
        }
    }
}
