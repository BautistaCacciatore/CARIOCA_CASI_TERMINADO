package Vistas.interfazGrafica;

import Modelos.Juego;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal {
    private final JFrame frame;
    private JPanel contentPane;
    private JButton nuevaPartidaBtn;
    private JButton mejoresJugadoresBtn;
    private JPanel panelBotones;
    private JLabel label;

    public Principal() {
        //panel
        contentPane.setBackground(new Color(255, 255, 224)); // Color crema o amarillo claro
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)), // Borde interno
                new LineBorder(new Color(70, 130, 180), 5, true) // Borde externo
        );
        contentPane.setBorder(bordeCompuesto);

        //frame
        frame = new JFrame("CARIOCA ");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla

        //label

        //botones
        mejoresJugadoresBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        mejoresJugadoresBtn.setForeground(Color.WHITE);
        mejoresJugadoresBtn.setFont(new Font("Arial", Font.BOLD, 16));

        nuevaPartidaBtn.setBackground(new Color(10, 150, 180));  // Azul acero
        nuevaPartidaBtn.setForeground(Color.WHITE);
        nuevaPartidaBtn.setFont(new Font("Arial", Font.BOLD, 16));

        mejoresJugadoresBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        nuevaPartidaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                nuevaPartida nuevaPartida= new nuevaPartida();
            }
        });
    }

    public void mostrar(){
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Principal a= new Principal();
        a.mostrar();
    }
}
