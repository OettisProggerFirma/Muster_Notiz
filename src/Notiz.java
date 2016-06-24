import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Created by hmueller on 30.05.2016.
 */
public class Notiz implements Comparable<Notiz> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private final String titel;

    private final String text;

    private LocalDateTime erstelltAmUm;

    private final int prio;

    public Notiz(String title, String text) {
        this(title, text, 1);
    }

    public Notiz(String title, String text, int prio) {
        this.titel = title;
        this.text = text;
        this.prio = prio;
        this.erstelltAmUm = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return this.titel;
    }

    public String getTitel() {
        return titel;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getErstelltAmUm() {
        return erstelltAmUm;
    }

    public int getPrio() {
        return prio;
    }

    @Override
    public int compareTo(Notiz o) {
        return this.prio - o.prio;
    }

    public static Notiz zeileZuNotiz(String zeile) {
        String[] splitter = zeile.split(":-:");

        Notiz ergebnis = new Notiz(splitter[1], splitter[2].replace("\\newline", "\n"), Integer.parseInt(splitter[splitter.length - 1]));
        ergebnis.erstelltAmUm = LocalDateTime.parse(splitter[3], formatter);

        return ergebnis;
    }

    public static String notizZuZeile(Notiz notiz) {
        String trenner = ":-:";
        StringBuilder sb = new StringBuilder("Notiz");
        sb.append(trenner);
        sb.append(notiz.titel);
        sb.append(trenner);
        sb.append(notiz.text.replace("\n", "\\newline"));
        sb.append(trenner);
        sb.append(notiz.erstelltAmUm.format(formatter));
        sb.append(trenner);
        sb.append(Integer.toString(notiz.prio));

        return sb.toString();
    }


}
