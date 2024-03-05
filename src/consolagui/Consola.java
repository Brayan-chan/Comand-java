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

public class Consola extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JLabel labelTitulo = new JLabel("Consola de Comandos en Java");
    private JTextField texto = new JTextField();
    private JTextArea areaTerminal = new JTextArea("");
    private String[] preguntas = {
        "¿Cuál es tu nombre?",
        "¿Cuál es tu edad?",
        "¿Cuál es tu ocupación?"
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
        texto.setBounds(150, 90, 600, 20);
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
