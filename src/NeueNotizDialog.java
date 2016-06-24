import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by hmueller on 02.06.2016.
 */
public class NeueNotizDialog extends JDialog {

    private final NotizAnzeige anzeige;
    private final List<Notiz> notizen;
    private final JList<Notiz> notizenJList;


    public NeueNotizDialog(JFrame frame, List<Notiz> notizen, JList<Notiz> notizenJList) {
        super(frame, "Neue Notiz");
        this.notizen = notizen;
        this.notizenJList = notizenJList;
        this.anzeige = new NotizAnzeige();

        this.add(this.createContent());

        this.pack();
        this.setLocationRelativeTo(frame);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    private JPanel createContent() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(this.anzeige, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAndDispose();
            }
        });

        JButton speichern = new JButton("Übernehmen");
        speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Notiz n;
                try {
                    n = anzeige.erzeugeNotiz();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(anzeige, ex.getMessage());
                    return;
                }

                if (n != null) {
                    notizen.add(n);
                    notizenJList.setListData(notizen.toArray(new Notiz[0]));
                    closeAndDispose();
                }
            }
        });

        buttons.add(abbrechen);
        buttons.add(speichern);

        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private void closeAndDispose() {
        setVisible(false);
        dispose();
    }
}
