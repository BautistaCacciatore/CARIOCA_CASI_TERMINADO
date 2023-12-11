package Vistas.consolagrafica;

import Controladores.Controlador;
import Controladores.Controlador2;
import Modelos.Bajada;
import Modelos.Juego;
import Modelos.Jugador;
import Vistas.IVista;
import Vistas.IVista2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaPrincipal implements IVista2 {
    private final JFrame frame;
    private JPanel contentPane;
    private JButton btnEnter;
    private JTextField txtEntrada;
    private JTextArea txtSalidaConsola;

    Controlador2 controlador;

    public PantallaPrincipal() {
        frame = new JFrame("<CARIOCA>");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        txtSalidaConsola.setBackground(Color.BLACK);
        txtSalidaConsola.setForeground(Color.GREEN);
        txtSalidaConsola.setEditable(false);

        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSalidaConsola.append(txtEntrada.getText() + "\n");
                //procesarCantidadJugadores(txtEntrada.getText());
                procesarIngreso(txtEntrada.getText());
                txtEntrada.setText("");
            }
        });
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void mostrarMensaje(String texto) {
        txtSalidaConsola.append(texto + "\n");
    }

    public void setControlador(Controlador2 controlador){
        this.controlador= controlador;
    }

    public void iniciar(){
        mostrar();
        mostrarMensaje("----------BIENVENIDO A CARIOCA----------");
        mostrarMensaje("Ingrese la cantidad de jugadores: ");
    }

    public void procesarIngreso(String input) {
        try{
            int cantidadJugadores = Integer.parseInt(input);

            if (cantidadJugadores < 2 || cantidadJugadores > 4) {
                mostrarMensaje("CANTIDAD INVALIDA");
                return;
            }

            controlador.setCantidadJugadores(cantidadJugadores);

            /*Juego modelo = new Juego();
            modelo.setCantidadJugadores(cantidadJugadores);
            //controlador.setCantidadJugadores(cantidadJugadores);
            for (int i = 0; i < cantidadJugadores; i++) {
                ConsolaGrafica vista = new ConsolaGrafica();
                Controlador controlador = new Controlador(vista);
                vista.setControlador(controlador);
                controlador.setModelo(modelo);
                modelo.addObserver(controlador);
                vista.comenzarJuego();

                //ConsolaGrafica vista = new ConsolaGrafica();
                //Controlador controlador1 = new Controlador(vista);
                //vista.setControlador(controlador1);
                //controlador1.setModelo(controlador.getModelo());
                //controlador.getModelo().addObserver(controlador1);
                //vista.comenzarJuego();
            }
            frame.setVisible(false);*/
        }
        catch (NumberFormatException e){
            mostrarMensaje("ERROR: CANTIDAD INVALIDA");
        }
    }

    @Override
    public void procesarCantidadJugadores() {
        frame.setVisible(false);
        for (int i = 0; i < controlador.getCantidadJugadores(); i++) {
            ConsolaGrafica vista = new ConsolaGrafica();
            Controlador controlador = new Controlador(vista);
            vista.setControlador(controlador);
            controlador.setModelo(this.controlador.getModelo());
            this.controlador.getModelo().addObserver(controlador);
            vista.comenzarJuego();

            //ConsolaGrafica vista = new ConsolaGrafica();
            //Controlador controlador1 = new Controlador(vista);
            //vista.setControlador(controlador1);
            //controlador1.setModelo(controlador.getModelo());
            //controlador.getModelo().addObserver(controlador1);
            //vista.comenzarJuego();
        }
    }
}
