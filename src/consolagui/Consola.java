package consolagui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class Consola extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JLabel labelTitulo = new JLabel("REGISTER COMAND");
    private JTextField texto = new JTextField();
    private JTextArea areaTerminal = new JTextArea("");
    private HashMap<String, String> usuarios = new HashMap<>();
    private String fileName = "usuarios.txt";
    private String binaryFileName = "usuarios.dat";
    private String usuarioActual = "";
    private boolean registroPendiente = false;
    private String nombreUsuarioPendiente = "";

    public Consola() {
        initComponents();
        setTitle("CMD-BCA");
        setLayout(new GridLayout(2, 1));
        setBounds(200, 50, 600, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        cargarUsuarios();

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
                    String comando = texto.getText().trim();
                    texto.setText("");
                    procesarComando(comando);
                }
            }
        });

        areaTerminal.append("Bienvenido!\n");
        mostrarMenuPrincipal();
    }

    private void procesarComando(String comando) {
        if (registroPendiente) {
            completarRegistro(comando);
            return;
        }
        switch (comando) {
            case "registro":
                iniciarRegistro();
                break;
            case "login":
                pedirLogin();
                break;
            case "salir":
                System.exit(0);
                break;
            default:
                areaTerminal.append("Comando no reconocido.\n");
        }
    }

    private void mostrarMenuPrincipal() {
        areaTerminal.append("Ingrese 'registro' para crear una cuenta o 'login' para iniciar sesión.\n");
        areaTerminal.append("Ingrese 'salir' para cerrar el programa.\n");
    }

    private void iniciarRegistro() {
        areaTerminal.append("Ingrese un nombre de usuario nuevo: ");
        registroPendiente = true;
    }

    private void completarRegistro(String contraseña) {
        String nombreUsuario = nombreUsuarioPendiente;
        if (!usuarios.containsKey(nombreUsuario)) {
            guardarUsuario(nombreUsuario, contraseña);
            areaTerminal.append("Usuario registrado exitosamente.\n");
        } else {
            areaTerminal.append("El nombre de usuario ya está en uso.\n");
        }
        registroPendiente = false;
        mostrarMenuPrincipal();
    }

    private void guardarUsuario(String nombreUsuario, String contraseña) {
        usuarios.put(nombreUsuario, contraseña);
        guardarUsuarios();
    }

    private void pedirLogin() {
        areaTerminal.append("Ingrese su nombre de usuario: ");
        texto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = texto.getText().trim();
                texto.setText("");
                if (usuarios.containsKey(nombreUsuario)) {
                    String contraseñaAlmacenada = usuarios.get(nombreUsuario);
                    areaTerminal.append("Ingrese su contraseña: ");
                    texto.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String contraseñaIngresada = texto.getText().trim();
                            texto.setText("");
                            if (contraseñaIngresada.equals(contraseñaAlmacenada)) {
                                areaTerminal.append("Inicio de sesión exitoso.\n");
                                usuarioActual = nombreUsuario;
                                mostrarMenuCRUD();
                            } else {
                                areaTerminal.append("Contraseña incorrecta.\n");
                                mostrarMenuPrincipal();
                            }
                        }
                    });
                } else {
                    areaTerminal.append("Usuario no encontrado. Por favor, regístrese.\n");
                    mostrarMenuPrincipal();
                }
            }
        });
    }

    private void mostrarMenuCRUD() {
        areaTerminal.append("¡Bienvenido de vuelta, " + usuarioActual + "!\n");
        areaTerminal.append("Ingrese 'mostrar' para ver sus datos.\n");
        areaTerminal.append("Ingrese 'actualizar' para modificar sus datos.\n");
        areaTerminal.append("Ingrese 'borrar' para eliminar su cuenta.\n");
        areaTerminal.append("Ingrese 'salir' para cerrar sesión.\n");
    }

    private void cargarUsuarios() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binaryFileName));
            usuarios = (HashMap<String, String>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de usuarios no encontrado. Se creará uno nuevo al registrar un usuario.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuarios() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binaryFileName));
            oos.writeObject(usuarios);
            oos.close();
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Consola();
    }
}
