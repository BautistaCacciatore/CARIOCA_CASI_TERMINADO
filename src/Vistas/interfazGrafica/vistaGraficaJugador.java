package Vistas.interfazGrafica;

import Controladores.Controlador;
import Enumerados.Forma;
import Modelos.Bajada;
import Modelos.Carta;
import Modelos.Jugador;
import Vistas.IVista;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class vistaGraficaJugador implements IVista {
    private final JFrame frame;
    private JPanel contentPane;
    private JButton mazoBtn;
    private JButton bajarBtn;
    private JButton bajarseBtn;
    private JButton pozoBtn;
    private JButton dejarBtn;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JSpinner spinner1;
    private JLabel labelCartas;
    private JLabel labelRonda;
    private JLabel labelNotificaciones;

    private String nickname;
    private Controlador controlador;

    private int tomoCartaMazo;
    private int tomoCartaPozo;
    private int dejoCarta;
    private int seBajo;
    private int  bajoCarta;


    public vistaGraficaJugador(String nickname){
        this.nickname=nickname;

        contentPane.setBackground(new Color(255, 255, 224)); // Color crema o amarillo claro
        Border bordeCompuesto = new CompoundBorder(
                new MatteBorder(10, 10, 10, 10, new Color(200, 255, 224)), // Borde interno
                new LineBorder(new Color(70, 130, 180), 5, true) // Borde externo
        );
        contentPane.setBorder(bordeCompuesto);

        //frame
        frame = new JFrame(nickname);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla
        frame.setVisible(true);

        //textAreas
        textArea1.setBackground(new Color(255, 228, 225));
        textArea1.setForeground(new Color(100, 0,0));
        textArea2.setBackground(new Color(255, 228, 225));
        textArea2.setForeground(new Color(100, 0,0));
        textArea3.setBackground(new Color(255, 228, 225));
        textArea3.setForeground(new Color(100, 0,0));

        //
        mazoBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        mazoBtn.setForeground(Color.WHITE);
        mazoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        mazoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.cantidadJugadores()== controlador.cantidadJugadoresAgregados()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                            controlador.jugadorActualTomarCartaMazo();
                            tomoCartaMazo=1;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No puedes tomar una carta.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que ingresen los demas jugadores.");
                }
            }
        });

        pozoBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        pozoBtn.setForeground(Color.WHITE);
        pozoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        pozoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.cantidadJugadores()==controlador.cantidadJugadoresAgregados()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo==0 && tomoCartaPozo==0 && seBajo==0 && dejoCarta==0 && bajoCarta==0){
                            controlador.jugadorActualTomarCartaPozo();
                            tomoCartaPozo=1;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No puedes tomar una carta.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que ingresen los demas jugadores.");
                }
            }
        });

        bajarseBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        bajarseBtn.setForeground(Color.WHITE);
        bajarseBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.cantidadJugadores()==controlador.cantidadJugadoresAgregados()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (controlador.jugadorActualSeBajo()==false){
                            if (tomoCartaMazo == 1 || tomoCartaPozo == 1) {
                                JOptionPane.showMessageDialog(null, "No puede bajarse, primero debe dejar una carta.");
                            } else {
                                boolean puedeBajarse = controlador.jugadorActualPuedeBajarse();
                                if (puedeBajarse == true) {
                                    if (controlador.obtenerNumeroRondaActual() != 9 && controlador.obtenerNumeroRondaActual() != 10) {
                                        JOptionPane.showMessageDialog(null, "Se ha bajado con exito, ahora debe dejar una carta.");
                                        controlador.setJugadorActualSeBajoRecien(true);
                                        seBajo = 1;
                                    } else {
                                        continuarRonda();
                                    }
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Ya te has bajado.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que ingresen los demas jugadores.");
                }
            }
        });

        dejarBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        dejarBtn.setForeground(Color.WHITE);
        dejarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        dejarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname == null) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.cantidadJugadores()==controlador.cantidadJugadoresAgregados()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (tomoCartaMazo == 1 || tomoCartaPozo == 1 || seBajo == 1) {
                            Integer indiceCarta = (Integer) spinner1.getValue();
                            if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                controlador.jugadorActualDejarCarta(indiceCarta);
                                dejoCarta = 1;
                                if (controlador.jugadorActualSeBajoRecien()) {
                                    controlador.setJugadorActualSeBajoRecien(false);
                                }
                                continuarRonda();
                            } else {
                                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe tomar una carta primero.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que ingresen los demas jugadores.");
                }
            }
        });

        bajarBtn.setBackground(new Color(70, 130, 180));  // Verde claro
        bajarBtn.setForeground(Color.WHITE);
        bajarBtn.setFont(new Font("Arial", Font.BOLD, 16));
        bajarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname==null){
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nickname primero.");
                } else if (controlador.cantidadJugadores()==controlador.cantidadJugadoresAgregados()) {
                    if (controlador.obtenerNickname(controlador.jugadorActual()).equals(nickname)){
                        if (controlador.jugadorActualSeBajo() && tomoCartaPozo==0 && tomoCartaMazo==0 && dejoCarta==0 && !controlador.jugadorActualSeBajoRecien()){
                            Integer indiceCarta = (Integer) spinner1.getValue();
                            if (indiceCarta >= 1 && indiceCarta <= controlador.cantidadCartasJugadorActual()) {
                                boolean pudoBajarCarta = controlador.jugadorActualPudoBajarCarta(indiceCarta);
                                if (pudoBajarCarta == true) {
                                    if (controlador.jugadorActualSinCartas()) {
                                        continuarRonda();
                                    } else {
                                        mostrarCartasJugador();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "La carta no pudo ser bajada.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Debes ingresar un numero valido.");
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Primero debes bajarte.");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No es tu turno.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes esperar a que ingresen los demas jugadores.");
                }
            }
        });
    }

    public void setControlador(Controlador controlador){
        this.controlador= controlador;
        controlador.agregarJugadores(nickname);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        textArea1.append(mensaje + "\n");
    }

    public void mostrarMensaje2(String mensaje) {
        textArea2.append(mensaje + "\n");
    }

    public void mostrarMensaje3(String mensaje) {
        textArea3.append(mensaje + "\n");
    }


    public void continuarRonda() {
        Jugador actual = controlador.jugadorActual();
        boolean terminoRonda = controlador.finRonda(actual);
        if (terminoRonda == true) {
            controlador.nuevaRonda();
            return;
        }
        controlador.cambiarTurno();
    }

    @Override
    public void mostrarRondaActual() {
        this.mostrarMensaje3("------------------------------------------------------------");
        this.mostrarMensaje3("RONDA: " + controlador.obtenerNumeroRondaActual());
        this.mostrarMensaje3("SE DEBE FORMAR: ");
        for (HashMap.Entry<Forma, Integer> entry : controlador.obtenerFormasRondaActual().entrySet()) {
            Forma forma = entry.getKey();
            int cantidad = entry.getValue();
            this.mostrarMensaje3("Forma: " + forma + ", Cantidad: " + cantidad);
        }
        this.mostrarMensaje3("------------------------------------------------------------");
    }

    @Override
    public void mostrarFormasArmadasDeCadaJugador() {
        int totalFormasArmadas= controlador.cantidadTotalFormasArmadas();
        if (totalFormasArmadas>0){
            ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
            for (int i=0; i<controlador.cantidadJugadores(); i++){
                if (controlador.formasArmadasJugador(i).size()!=0){
                    mostrarCartasBajadas(jugadores.get(i));
                }
            }
            this.mostrarMensaje3("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarTurno(Jugador jugador) {
        tomoCartaPozo=0;
        tomoCartaMazo=0;
        seBajo=0;
        dejoCarta=0;
        bajoCarta=0;
        textArea2.setText("");
        mostrarRondaActual();
        this.mostrarMensaje3("ES EL TURNO DE: " + controlador.obtenerNickname(jugador));
        this.mostrarMensaje3("------------------------------------------------------------");
        mostrarFormasArmadasDeCadaJugador();
        mostrarCartasJugador();
        mostrarMazo();
        mostrarTopePozo();
    }

    @Override
    public void mostrarCartasBajadas(Jugador jugador) {
        this.mostrarMensaje3("CARTAS BAJADAS DE: " + controlador.obtenerNickname(jugador));
        for (int i=0; i<controlador.formasArmadasJugador(jugador).size(); i++){
            mostrarForma(controlador.formasArmadasJugador(jugador).get(i));
        }
    }

    @Override
    public void mostrarForma(Bajada bajada) {
        if (controlador.obtenerNombreForma(bajada).equals(Forma.TRIO)){
            if (controlador.cartasQueFormanLaBajada(bajada).get(0).getValor().equals("$")){
                if (controlador.cartasQueFormanLaBajada(bajada).get(1).getValor().equals("$")){
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(2).getValor());
                }
                else{
                    this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(1).getValor());
                }
            }
            else{
                this.mostrarMensaje3("TRIO DE " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor());
            }
        } else if (controlador.obtenerNombreForma(bajada).equals(Forma.ESCALA)) {
            this.mostrarMensaje3("ESCALA " + controlador.cartasQueFormanLaBajada(bajada).get(0).getColor() + ", comienza con " + controlador.cartasQueFormanLaBajada(bajada).get(0).getValor() + " ,termina con " + controlador.cartasQueFormanLaBajada(bajada).get(controlador.cartasQueFormanLaBajada(bajada).size()-1).getValor());
        }
    }

    @Override
    public void mostrarCartasJugador() {
        textArea1.setText("");
        ArrayList<Carta> cartas= controlador.obtenerCartasJugador(this.nickname);
        for (int i=0; i<cartas.size(); i++){
            this.mostrarMensaje((i+1) + " - " + cartas.get(i).toString());
        }
    }

    @Override
    public void mostrarPuntosJugadores() {
        this.mostrarMensaje("------------------------------------------------------------");
        ArrayList<Jugador> jugadores= controlador.obtenerJugadores();
        for (int i=0; i<jugadores.size(); i++){
            this.mostrarMensaje(controlador.obtenerNickname(jugadores.get(i)) + ", PUNTOS: " + controlador.obtenerPuntosJugador(jugadores.get(i)));
        }
        this.mostrarMensaje("------------------------------------------------------------");
    }

    @Override
    public void mostrarTopePozo() {
        String a= controlador.obtenerTopePozo();
        this.mostrarMensaje3("TOPE POZO: " + a);
        this.mostrarMensaje3("------------------------------------------------------------");
    }

    @Override
    public void mostrarMazo() {
        this.mostrarMensaje3("MAZO...");
    }

    @Override
    public void mostrarJugadorTomoCartaPozo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("TOMASTE UNA CARTA DEL POZO CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2(controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL POZO CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorTomoCartaMazo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("TOMASTE UNA CARTA DEL MAZO CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2(controlador.obtenerNickname(jugador) + " TOMO UNA CARTA DEL MAZO CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorSeBajo(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("TE BAJASTE CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarCartasJugador();
        }
        else{
            this.mostrarMensaje2( controlador.obtenerNickname(jugador) + " SE HA BAJADO CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarse(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            JOptionPane.showMessageDialog(null, "No puedes bajarte.");
        }
        else{
            this.mostrarMensaje2(controlador.obtenerNickname(jugador) + " INTENTO BAJARSE Y NO PUDO");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorDejoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(this.nickname)){
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("DEJASTE LA CARTA CON EXITO!");
        }
        else{
            this.mostrarMensaje2(controlador.obtenerNickname(jugador) + " HA DEJADO LA CARTA CON EXITO!");
        }
        this.mostrarMensaje2("------------------------------------------------------------");
    }

    @Override
    public void mostrarJugadorBajoCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            this.mostrarMensaje2("------------------------------------------------------------");
            mostrarMensaje2("HAS BAJADO LA CARTA CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
        else{
            this.mostrarMensaje2( controlador.obtenerNickname(jugador) + " HA BAJADO UNA CARTA CON EXITO!");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarJugadorNoPudoBajarCarta(Jugador jugador) {
        if (controlador.obtenerNickname(jugador).equals(nickname)){
            JOptionPane.showMessageDialog(null, "No es posible bajar la carta.");
        }
        else{
            this.mostrarMensaje2("------------------------------------------------------------");
            this.mostrarMensaje2( controlador.obtenerNickname(jugador) + " INTENTO BAJAR UNA CARTA Y NO PUDO.");
            this.mostrarMensaje2("------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarTerminoRonda() {
        this.mostrarMensaje("------------------------------------------------------------");
        Jugador j= controlador.ganadorRonda();
        this.mostrarMensaje("EL GANADOR DE LA RONDA ES: " + controlador.obtenerNickname(j));
        this.mostrarMensaje("------------------------------------------------------------");
        mostrarPuntosJugadores();
    }

    @Override
    public void mostrarFinJuego() {
        this.mostrarMensaje("");
        this.mostrarMensaje("------------------------------------------------------------");
        this.mostrarMensaje("EL JUEGO HA TERMINADO");
        Jugador j= controlador.ganadorJuego();
        this.mostrarMensaje("GANADOR: " + controlador.obtenerNickname(j));
        this.mostrarMensaje("------------------------------------------------------------");
    }

    @Override
    public void mostrarJugadoresCompletos() {

    }

    @Override
    public void mostrarNuevoJugadorAgregado(Jugador jugador) {

    }
}
