import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JCheckBox checkBox;
    private JTextField textField;
    private JPanel mainPanel;
    private JButton btnPrevious;
    private JRadioButton radioButton3;
    private JRadioButton radioButton2;
    private JRadioButton radioButton1;
    private JButton btnSave;
    private JButton btnNext;
    private JCheckBox upravCB;
    private JLabel LabelOblibenosti;
    private JButton addButton;
    private JButton seznamBtn;

    private int indexAktualniDeskovky = 0;
    private final SpravceDeskovek spravceDeskovek;

    public GUI(SpravceDeskovek spravceDeskovek) {
        this.spravceDeskovek = spravceDeskovek;
        initMenu();
        initComponents();
        setBounds(400, 80, 700, 700);
        updateGUI();
        btnNext.addActionListener(e -> dalsiDeskovka());
        btnPrevious.addActionListener(e -> predchoziDeskovka());
        btnSave.addActionListener(e -> {
            try {
                ulozDeskovku();
            } catch (OblibenostException ex) {
                throw new RuntimeException(ex.getLocalizedMessage());
            }
        });

        textField.setEditable(false);
        btnSave.setEnabled(false);
        addButton.setEnabled(false);
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        radioButton3.setEnabled(false);
        checkBox.setEnabled(false);
        ImageIcon nextIcon = new ImageIcon("images/nextIcon.png");
        btnNext.setIcon(nextIcon);
        ImageIcon prevIcon = new ImageIcon("images/prevIcon.png");
        btnPrevious.setIcon(prevIcon);
        ImageIcon saveIcon = new ImageIcon("images/saveIcon.png");
        btnSave.setIcon(saveIcon);

        upravCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(upravCB.isSelected() == false){
                    textField.setEditable(false);
                    btnSave.setEnabled(false);
                    addButton.setEnabled(false);
                    radioButton1.setEnabled(false);
                    radioButton2.setEnabled(false);
                    radioButton3.setEnabled(false);
                    checkBox.setEnabled(false);

                }else{
                    textField.setEditable(true);
                    btnSave.setEnabled(true);
                    addButton.setEnabled(true);
                    radioButton1.setEnabled(true);
                    radioButton2.setEnabled(true);
                    radioButton3.setEnabled(true);
                    checkBox.setEnabled(true);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (upravCB.isEnabled() == true){
                    addButton.setEnabled(true);
                }else{
                    addButton.setEnabled(false);
                }
                try {
                    spravceDeskovek.pridejDeskovku(new Deskovka(textField.getText(), checkBox.isSelected(), 1));
                    textField.setText("");
                }catch (OblibenostException ex) {
                    System.err.println("chyba" + ex);
                }
            }
        });
        seznamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seznamHer();
            }
        });
    }

    private void initComponents() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Čtení ze souboru");
        pack();
    }

    private void ulozDeskovku() throws OblibenostException {
        String nazevHry = textField.getText();
        boolean zakoupeno = checkBox.isSelected();
        int oblibenost = 1;
        if (radioButton2.isSelected()) {
            oblibenost = 2;
        } else if (radioButton3.isSelected()) {
            oblibenost = 3;
        }
        Deskovka aktualniDeskovka = spravceDeskovek.getDeskovka(indexAktualniDeskovky);
        aktualniDeskovka.setNazevHry(nazevHry);
        aktualniDeskovka.setZakoupeno(zakoupeno);
        aktualniDeskovka.setOblibenost(oblibenost);
        spravceDeskovek.setDeskovka(indexAktualniDeskovky, aktualniDeskovka);
    }

    private void dalsiDeskovka() {
        btnPrevious.setEnabled(true);
        if (indexAktualniDeskovky < spravceDeskovek.getPocetDeskovek() - 1) {
            indexAktualniDeskovky++;
            updateGUI();
        }
    }

    private void predchoziDeskovka() {
        btnNext.setEnabled(true);
        if (indexAktualniDeskovky > 0) {
            indexAktualniDeskovky--;
            updateGUI();
        }
    }

    private void updateGUI() {
        if (indexAktualniDeskovky == 0) {
            btnPrevious.setEnabled(false);
        }
        if (indexAktualniDeskovky == spravceDeskovek.getPocetDeskovek() - 1) {
            btnNext.setEnabled(false);
        }
        if (spravceDeskovek.getPocetDeskovek() == 0) {
            textField.setText("");
            checkBox.setSelected(false);
            radioButton1.setSelected(true);
        }else {
                Deskovka aktualniDeskovka = spravceDeskovek.getDeskovka(indexAktualniDeskovky);
                textField.setText(aktualniDeskovka.getNazevHry());
                checkBox.setSelected(aktualniDeskovka.isZakoupeno());
                switch (aktualniDeskovka.getOblibenost()) {
                    case 1:
                        radioButton1.setSelected(true);
                        break;
                    case 2:
                        radioButton2.setSelected(true);
                        break;
                    case 3:
                        radioButton3.setSelected(true);
                        break;
                }
        }
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Informace");
        menuBar.add(fileMenu);

        JMenuItem openItem = new JMenuItem("Pozdrav");
        fileMenu.add(openItem);
        JMenuItem openItem1 = new JMenuItem("Informace o vývojáři");
        fileMenu.add(openItem1);
        JMenuItem openItem2 = new JMenuItem("Vývojář aplikace");
        fileMenu.add(openItem2);

        JMenuItem aboutItem = new JMenuItem("O aplikaci...");
        menuBar.add(aboutItem);

        openItem.addActionListener(e -> reakce());

    }
    private void reakce() {
        JOptionPane.showMessageDialog(this, "Ahoj!");
    }


    private void seznamHer() {
        JOptionPane.showMessageDialog(this, "Seznam není k dispozici.");

    }

}