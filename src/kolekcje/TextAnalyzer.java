package kolekcje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextAnalyzer extends JFrame implements WindowListener, ActionListener {

    private JTextArea textarea;
    private JComboBox<String> encoding;

    public TextAnalyzer() {

   
        setBounds(150, 150, 450, 600);
        setLayout(null);

        addWindowListener(this);

        textarea = new JTextArea("Wciśnij \"Wczytaj Plik\" aby wczytać plik tekstowy.");
        textarea.setEditable(false);
        JScrollPane pane = new JScrollPane(textarea);
        pane.setBounds(10, 50, 360, 450);
        add(pane);

        JButton button = new JButton("Wczytaj Plik");
        button.setBounds(10, 10, 125, 30);
        button.setActionCommand("Wczytaj");
        button.addActionListener(this);
        add(button);

        List<Entry<String, Charset>> set = new ArrayList<Entry<String, Charset>>(Charset.availableCharsets().entrySet());

        String[] elements = new String[set.size()];

        for (int i = 0; i < elements.length; i++) {
            elements[i] = set.get(i).getKey();
        }

        encoding = new JComboBox<String>(elements);
        encoding.setBounds(145, 10, 125, 30);
        add(encoding);

        setVisible(true);

    }

    public void updateTextArea(List<Entry<Character, Integer>> list) {

        textarea.setText("");

        Iterator<Entry<Character, Integer>> it = list.iterator();
        Entry<Character, Integer> listelement;

        while (it.hasNext()) {

            listelement = it.next();

            textarea.setText(textarea.getText() + "Znak: \"" + characterConversion(listelement.getKey()) + "\" ilość: " + listelement.getValue() + "\n");

        }

    }

    private String characterConversion(char c) {

        String ret;

        switch (c) {

            case '\n': {
                ret = "ENTER";
                break;
            }
            case ' ': {
                ret = "SPACE";
                break;
            }
            case '\t': {
                ret = "TAB";
                break;
            }
            default:
                ret = c + "";

        }

        return ret;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Wczytaj")) {

            JFileChooser filechooser = new JFileChooser();
            filechooser.setDialogTitle("Wybierz Plik");

            filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = filechooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {

                File file = filechooser.getSelectedFile();

                Map<Character, Integer> map = new TreeMap<Character, Integer>();

                InputStreamReader in = null;

                try {
                    in = new InputStreamReader(new FileInputStream(file), (String) encoding.getSelectedItem());

                    int b;
                    char character;
                    while ((b = in.read()) != -1) {

                        if (b == 13) {
                            continue;
                        }

                        character = (char) b;

                        if (map.containsKey(character)) {

                            map.replace(character, map.get(character) + 1);

                        } else {
                            map.put(character, 1);
                        }

                    }

                    List<Entry<Character, Integer>> set = new ArrayList<Entry<Character, Integer>>(map.entrySet());

                    Collections.sort(set, new Comparator< Entry<Character, Integer>>() {

                        @Override
                        public int compare(Entry<Character, Integer> o1,
                                Entry<Character, Integer> o2) {

                            return o2.getValue().compareTo(o1.getValue());

                        }
                    });

                    updateTextArea(set);

                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

}
