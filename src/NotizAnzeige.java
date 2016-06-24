import javax.swing.*;
import java.awt.*;

/**
 * Created by hmueller on 01.06.2016.
 */
public class NotizAnzeige extends JPanel {

    private final JTextField titel;
    private final JTextArea text;
    private final JTextField priority;

    public NotizAnzeige() {
        this.setLayout(new BorderLayout());

        this.titel = new JTextField(50);

        this.text = new JTextArea(20, 55);
        this.text.setLineWrap(true);
        this.text.setWrapStyleWord(true);

        this.priority = new JTextField(4);


        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel prioPanel = new JPanel();
        prioPanel.add(this.priority);
        prioPanel.setBorder(BorderFactory.createTitledBorder("Prio"));

        JPanel titlePanel = new JPanel();
        titlePanel.add(this.titel);
        titlePanel.setBorder(BorderFactory.createTitledBorder("Titel"));

        top.add(titlePanel);
        top.add(prioPanel);

        this.add(top, BorderLayout.NORTH);

        JScrollPane center = new JScrollPane(text,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        center.setBorder(BorderFactory.createTitledBorder("Text"));

        this.add(center, BorderLayout.CENTER);
    }

    public void zeigeNotiz(Notiz n) {
        this.titel.setText(n.getTitel());
        this.text.setText(n.getText());
        this.priority.setText(Integer.toString(n.getPrio()));
    }

    public Notiz erzeugeNotiz() {

        if (this.text.getText().length() > 0 && this.titel.getText().length() > 0) {
            int prio = 1;
            try {
                prio = Integer.parseInt(this.priority.getText());
            } catch (NumberFormatException ex) {
            }

            return new Notiz(titel.getText(), text.getText(), prio);
        } else {
            throw new RuntimeException("Titel und Text müssen befüllt sein!");
        }
    }

    public void anzeigeLeeren() {
        this.titel.setText("");
        this.text.setText("");
        this.priority.setText("");
    }


}
