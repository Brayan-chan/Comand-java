package consolagui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class HolaMundoTerminalPrueba {
    public HolaMundoTerminalPrueba() {
        // Lógica de la clase...
        int resultado = 2 + 3; // Por ejemplo, aquí se podría realizar alguna operación
        System.out.println("El resultado de la operación es: " + resultado);
    }
    
    public void redirectOutput(JTextArea areaTerminal) {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                areaTerminal.append(String.valueOf((char) b));
            }
        });
        System.setOut(printStream);
    }
}
