package Vistas.interfazGrafica;

import Controladores.Controlador;
import Controladores.Controlador2;
import Modelos.Juego;
import Vistas.IVista2;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class nuevaPartida implements IVista2 {
    private final JFrame frame;
    private JLabel label;
    private JButton enterBtn;
    private JLabel label2;
    private JPanel contentPane;
    private JSpinner spinner1;

    private Controlador2 controlador;

    public nuevaPartida() {
        //panel
        contentPane.setBackground(new Color(255, 255, 224)); // Color crema o amarillo claro
        // Crear bordes estéticos
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)), // Borde interno
                new LineBorder(new Color(70, 130, 180), 5, true) // Borde externo
        );

        contentPane.setBorder(bordeCompuesto);

        //labels
        label.setBorder(new CompoundBorder(
                // Borde interno para espacio interno
                new EmptyBorder(10, 10, 10, 10),
                // Borde externo para simular sombreado
                new CompoundBorder(
                        new LineBorder(new Color(0, 0, 0, 80), 3, true), // Sombra
                        new LineBorder(Color.BLACK, 1) // Borde principal
                )
        ));

        //frame
        frame = new JFrame("NUEVA PARTIDA");
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
                //procesarCantidadJugadores();
                procesarIngreso();
            }
        });
    }

    public void setControlador(Controlador2 controlador){
        this.controlador= controlador;
    }

    private void procesarIngreso(){
        Integer jugadores = (Integer) spinner1.getValue();
        if (jugadores < 2 || jugadores > 4) {
            JOptionPane.showMessageDialog(null, "Cantidad de jugadores invalida.");
            return;
        }
        else{
            controlador.setCantidadJugadores(jugadores);
        }
    }

    public void procesarCantidadJugadores(){
        frame.setVisible(false);
        for (int i=0; i<controlador.getCantidadJugadores(); i++){
            VistaJugador vistaJugador= new VistaJugador();
            Controlador controlador= new Controlador(vistaJugador);
            vistaJugador.setControlador(controlador);
            controlador.setModelo(this.controlador.getModelo());
            this.controlador.getModelo().addObserver(controlador);
        }
    }

    /*public void procesarCantidadJugadores() {
        Integer jugadores = (Integer) spinner1.getValue();
        if (jugadores < 2 || jugadores > 4) {
            JOptionPane.showMessageDialog(null, "Cantidad de jugadores invalida.");
            return;
        }
        else{
            Juego modelo= new Juego();
            modelo.setCantidadJugadores(jugadores);
            frame.setVisible(false);
            for (int i=0; i<jugadores; i++){
                //ingresoJugador jugador= new ingresoJugador(modelo);
                VistaJugador vistaJugador= new VistaJugador();
                Controlador controlador= new Controlador(vistaJugador);
                vistaJugador.setControlador(controlador);
                controlador.setModelo(modelo);
                modelo.addObserver(controlador);
            }
        }
    }*/
}
