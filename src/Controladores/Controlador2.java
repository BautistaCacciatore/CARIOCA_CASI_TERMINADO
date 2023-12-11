package Controladores;

import Modelos.*;
import Vistas.IVista2;

public class Controlador2 implements Observador {
    private IVista2 vista;
    private Juego modelo;

    public Controlador2(IVista2 vista, Juego modelo){
        this.vista= vista;
        this.modelo= modelo;
    }

    public void setCantidadJugadores(int cantidadJugadores){
        modelo.setCantidadJugadores(cantidadJugadores);
    }

    public int getCantidadJugadores(){
        return modelo.getCantidadJugadores();
    }

    public Juego getModelo(){
        return this.modelo;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Evento) {
            switch ((Evento) arg) {
                case CANTIDAD_JUGADORES_INGRESADA:
                    this.vista.procesarCantidadJugadores();
                    break;
                default:
                    break;
            }
        }
    }
}
