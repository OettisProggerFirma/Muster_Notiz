import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by hmueller on 01.06.2016.
 */
public class FrameHolder {

    private final JFrame frame;
    private final JList<Notiz> notizenJList;
    private java.util.List<Notiz> notizen;
    private final NotizenSpeicher speicher;
    private final NotizAnzeige anzeige;

    public FrameHolder() {
        this.frame = new JFrame("Notizenverwaltung");
        this.speicher = new NotizenSpeicher();
        this.anzeige = new NotizAnzeige();

        try {
            this.notizen = speicher.laden();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Laden der Notizen : " + e.getMessage());
            this.notizen = Collections.emptyList();
        }

        this.notizenJList = new JList<>(this.notizen.toArray(new Notiz[0]));
        notizenJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.frame.add(this.createContent());

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    speicher.speichern(notizen);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Fehler beim Speichern der Notizen : " + ex.getMessage());
                }
            }
        });

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel(new BorderLayout());


        panel.add(this.anzeige, BorderLayout.CENTER);
        panel.add(this.createLeftPane(), BorderLayout.WEST);

        notizenJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!notizenJList.isSelectionEmpty()) {
                    anzeige.zeigeNotiz(notizenJList.getSelectedValue());
                }
            }
        });

        JPanel buttonPanel = new JPanel();


        JButton neueNotiz = new JButton("Neue Notiz");
        neueNotiz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anzeige.anzeigeLeeren();
                new NeueNotizDialog(frame, notizen, notizenJList);
            }
        });

        JButton notizLoeschen = new JButton("Notiz Löschen");
        notizLoeschen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!notizenJList.isSelectionEmpty()) {
                    notizen.remove(notizenJList.getSelectedValue());
                    sync();
                    anzeige.anzeigeLeeren();
                }
            }
        });

        buttonPanel.add(neueNotiz);
        buttonPanel.add(notizLoeschen);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLeftPane() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setBorder(BorderFactory.createTitledBorder("Sortierung"));
        JButton prioSort = new JButton("Priorität");
        prioSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(notizen);
                sync();
            }
        });
        JButton titleSort = new JButton("Titel");
        titleSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(notizen, new NotizTitelComparator());
                sync();
            }
        });

        JButton timeSort = new JButton("Erstellungszeit");
        timeSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(notizen, new NotizErstellungsZeitComparator());
                sync();
            }
        });
        buttons.add(prioSort);
        buttons.add(titleSort);
        buttons.add(timeSort);

        sync();

        panel.add(buttons, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(notizenJList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void sync() {
        notizenJList.setListData(notizen.toArray(new Notiz[0]));
    }
}
