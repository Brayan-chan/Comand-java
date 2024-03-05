package consolagui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.io.File;
import java.util.InputMismatchException;

/*

class Assed extends Accounts implements Implementaciones{

    double current(String nameFile) {
        Arraylist<Accounts> currentAsset = createAccounts(
                "Caja",
                "Bancos",
                "Inversiones temporales",
                "Mercancias",
                "Clientes",
                "Documentos por cobrar",
                "Deudores diversos", 
                "Anticipo a proveedores"
        );
    return account(currentAsset, "Activo", "Circulante", nameFile);
    }
}

class Accounts extends Consola implements Implementaciones{
    public Accounts() {}
    
    private String name;
    
    public Accounts(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Accounts> createAccounts(String... names) {
        ArrayList<Accounts> accounts = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            accounts.add(new Accounts(names[i]));
        }
        return accounts;
    }
    
    public double account(ArrayList<Accounts> accounts, String type, String clasification, String nameFile) {
        System.out.println(clasification);
        new TextFile(nameFile).updateTextFile(type + " " + clasification);
        double[] values = new double[accounts.size()];
        ArrayList<Accounts> nonEmptyAccounts = new ArrayList<>();
        double total = 0;

        try {
            for (int i = 0; i < accounts.size(); i++) {
                System.out.print(accounts.get(i).getName() + " $");
                values[i] = new java.util.Scanner(System.in).nextDouble();
            }
            for (int i = 0; i < accounts.size(); i++) {
                if (values[i] != 0) {
                    nonEmptyAccounts.add(accounts.get(i));
                    total += values[i];

                    if (i < nonEmptyAccounts.size()) {
                        new TextFile(nameFile).updateTextFile(nonEmptyAccounts.get(i).getName() + " $" + values[i]);
                    }
                }
            }
            System.out.println("Total de  " + clasification.toLowerCase() + ": $" + total);

            new TextFile(nameFile).updateTextFile("Total de " + clasification.toLowerCase() + ": $" + total);

        } catch (InputMismatchException | NumberFormatException | ArithmeticException | NoSuchFieldError  | IndexOutOfBoundsException e) {
            System.err.println("Se produjo un error. Intenta de nuevo" + '(' + e.getMessage() + ')');
            restart(nameFile);
        }
    }
    return total;
}
*/

public class Consola extends JFrame implements Implementaciones{
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JLabel labelTitulo = new JLabel("General Comands");
    private JTextField texto = new JTextField();
    private JTextArea areaTerminal = new JTextArea("");
    private String[] preguntas = {
        "Nombre de la empresa",
        "Nombre de la entidad",
        "Ocupación"
    };
    private int preguntaActual = 0;

    public Consola() {
        initComponents();
        setTitle("CMD-BCA");
        setLayout(new GridLayout(2, 1));
        setBounds(200, 50, 600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        panel.setLayout(null);
        panel.setBackground(Color.black);
        label.setIcon(new ImageIcon(getClass().getResource("favicon.png")));
        label.setBackground(Color.black);
        label.setBounds(0, 0, 100, 90);
        labelTitulo.setBounds(200, 10, 300, 20);
        labelTitulo.setBackground(Color.black);
        labelTitulo.setForeground(Color.red);
        labelTitulo.setFont(new Font("consolas", 1, 20));
        texto.setBounds(150, 90, 250, 20);
        texto.setForeground(Color.green);
        texto.setBackground(Color.black);
        texto.setSelectionColor(Color.red);
        texto.setFont(new Font("consolas", 1, 12));
        areaTerminal.setBounds(0, 150, 300, 300);
        areaTerminal.setBackground(Color.black);
        areaTerminal.setForeground(Color.green);
        areaTerminal.setSelectionColor(Color.red);
        areaTerminal.setFont(new Font("consolas", 1, 10));
        areaTerminal.setEnabled(false);
        panel.add(labelTitulo);
        panel.add(label);
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(areaTerminal), BorderLayout.CENTER);
        panel.add(texto);

        texto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String respuesta = texto.getText().trim();
                    texto.setText("");
                    if (preguntaActual < preguntas.length) {
                        areaTerminal.append(preguntas[preguntaActual] + ": " + respuesta + "\n");
                        preguntaActual++;
                        if (preguntaActual < preguntas.length) {
                            areaTerminal.append(preguntas[preguntaActual] + "\n");
                        }
                    } else {
                        areaTerminal.append("No hay más preguntas.\n");
                    }
                }
            }
        });

        if (preguntaActual < preguntas.length) {
            areaTerminal.append(preguntas[preguntaActual] + "\n");
        }
    }

    public static void main(String[] args) {
        new Consola();
    }
}
