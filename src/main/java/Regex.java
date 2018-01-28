import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vadim on 28.01.18.
 */
public class Regex {
    public static void main(String[] args) {
        String filename1 = "asd/dfg/gfh/ghj/MSK_16.03.2017/z_p_154_USD_CAD_messages.txt";
        String filename2 = "asd/dfg/gfh/ghj/16.03.2017/z_MOEX_USD_RUB_TOM_messages.txt";
        match(filename1);
        match(filename2);
    }

    private static void match(String input) {
        System.out.println();
        String currencies = "(" + Arrays.stream(Currency.values())
                                        .map(Object::toString)
                                        .peek(System.out::println)
                                        .reduce((x, y) -> x + "|" + y)
                                        .get() + ")";
        Pattern pattern = Pattern.compile(String.format(
                "(?<date>\\d{2})\\.(\\d{2})\\.(\\d{4})/z_(p_([0-9]+)|[A-Z0-9_]+)_%s_%s_(TO[DM])?",
                currencies,
                currencies));
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i < matcher.groupCount() + 1; i++) {
                System.out.println(String.format("%d: %s, [%s, %s]",
                                                 i,
                                                 matcher.group(i),
                                                 matcher.start(i),
                                                 matcher.end(i)));
            }
            LocalDate date = LocalDate.of(Integer.valueOf(matcher.group(3)),
                                          Integer.valueOf(matcher.group(2)),
                                          Integer.valueOf(matcher.group("date")));
            String provider = matcher.group(5) != null
                    ? "RZBM_" + matcher.group(5)
                    : matcher.group(4);
            String instrument = matcher.group(8) != null
                    ? matcher.group(6) + "/" + matcher.group(7) + "_" + matcher.group(8)
                    : matcher.group(6) + "/" + matcher.group(7);
            System.out.println(date + " " + provider + " " + instrument);
        } else {
            System.out.println("Nothing found");
        }
    }

    enum Currency {
        USD,
        RUB,
        CAD
    }
}
