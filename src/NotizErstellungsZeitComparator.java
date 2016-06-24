import java.util.Comparator;

/**
 * Created by hmueller on 02.06.2016.
 */
public class NotizErstellungsZeitComparator implements Comparator<Notiz> {

    @Override
    public int compare(Notiz o1, Notiz o2) {
        return o1.getErstelltAmUm().compareTo(o2.getErstelltAmUm());
    }
}
