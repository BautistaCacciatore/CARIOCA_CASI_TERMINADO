package Vistas.interfazGrafica;
import Controladores.Controlador;
import Modelos.Juego;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ingresoJugador {
    private final JFrame frame;
    private JPanel contentPane;
    private JTextField ingresoNickname;
    private JButton enterBtn;
    private JLabel labelBienvenida;
    private JLabel labelIngreso;

    private String nickname;
    private Juego modelo;

    public ingresoJugador(Juego modelo){
        this.modelo= modelo;
        //paneles
        contentPane.setBackground(new Color(255, 255, 224)); // Color crema o amarillo claro
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)), // Borde interno
                new LineBorder(new Color(70, 130, 180), 5, true) // Borde externo
        );
        contentPane.setBorder(bordeCompuesto);

        //frame
        frame = new JFrame("JUGADOR");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla
        frame.setVisible(true);

        //botones
        enterBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setFont(new Font("Arial", Font.BOLD, 16));

        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarIngresoNickname();
                //JOptionPane.showMessageDialog(null, "Bienvenido " + nickname + " , ya puedes jugar.");
            }
        });
    }

    private void procesarIngresoNickname(){
        frame.setVisible(false);
        this.nickname= ingresoNickname.getText();
        vistaGraficaJugador vistaGraficaJugador= new vistaGraficaJugador(nickname);
        Controlador controlador =  new Controlador(vistaGraficaJugador);
        controlador.setModelo(modelo);
        vistaGraficaJugador.setControlador(controlador);
        modelo.addObserver(controlador);
    }
}
