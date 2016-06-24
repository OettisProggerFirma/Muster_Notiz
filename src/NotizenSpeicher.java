import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hmueller on 01.06.2016.
 */
public class NotizenSpeicher {

    private final String dateiName;

    public NotizenSpeicher() {
        this.dateiName = "notizen2.note";
    }

    public List<Notiz> laden() throws IOException {
        List<Notiz> liste = new ArrayList<>();

        try (BufferedReader bfr = new BufferedReader(new FileReader(dateiName))) {
            String zeile = "";
            while ((zeile = bfr.readLine()) != null) {
                if (zeile.startsWith("Notiz")) {
                    liste.add(Notiz.zeileZuNotiz(zeile));
                }
            }
        }
        Collections.sort(liste);
        return liste;
    }

    public void speichern(List<Notiz> zuSpeichern) throws IOException {

        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(this.dateiName))) {
            for (Notiz notiz : zuSpeichern) {
                bwr.write(Notiz.notizZuZeile(notiz));
                bwr.write(System.lineSeparator());
            }
        }

    }

}
