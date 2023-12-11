import Controladores.Controlador;
import Controladores.Controlador2;
import Modelos.Juego;
import Vistas.consolagrafica.PantallaPrincipal;
import Vistas.interfazGrafica.nuevaPartida;

public class cariocaApp {
    public static void main(String[] args) {
        Juego modelo= new Juego();
        //CONSOLA GRAFICA
        PantallaPrincipal pantallaPrincipal= new PantallaPrincipal();
        Controlador2 controlador = new Controlador2(pantallaPrincipal, modelo);
        pantallaPrincipal.setControlador(controlador);
        modelo.addObserverPP(controlador);
        pantallaPrincipal.iniciar();

        //INTERFAZ GRAFICA
        nuevaPartida nuevaPartida= new nuevaPartida();
        Controlador2 controlador2= new Controlador2(nuevaPartida, modelo);
        nuevaPartida.setControlador(controlador2);
        modelo.addObserverPP(controlador2);
    }
}
