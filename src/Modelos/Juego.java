package Modelos;

import Controladores.Controlador;
import Controladores.Controlador2;
import Enumerados.Forma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Juego implements Observable {
    private int cantidadJugadores;
    private int cantidadJugadoresAgregados;
    private ArrayList<Controlador> misObservadores;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Ronda> rondas;
    private Ronda rondaActual;
    private int numeroRondaActual;
    private Mazo mazo;
    private int turno;

    //
    private ArrayList<Controlador2> observadoresPantallaPrincipal;
    //

    public Juego(){
        rondas= new ArrayList<>();
        misObservadores= new ArrayList<>();
        cargarRondas();
        this.rondaActual= rondas.get(0);
        this.numeroRondaActual= 0;
        jugadores= new ArrayList<>();
        cantidadJugadoresAgregados=0;

        observadoresPantallaPrincipal= new ArrayList<>();
    }

    /**
     * Mezcla el mazo, otorga 12 cartas a cada jugador.
     * @return void
     **/
    public void repartir(){
        mazo= new Mazo();
        mazo.mezclar();
        for (int i=0; i<jugadores.size(); i++){
            jugadores.get(i).setCartas(mazo.darCartas());
        }
    }

    /**
     * Agrega un jugador al juego.
     * @param jugador: jugador a agregar
     * @return void
     **/
    public void agregarJugador(Jugador jugador){
        if (this.jugadores.size()<this.cantidadJugadores){
            this.jugadores.add(jugador);
            cantidadJugadoresAgregados+=1;
            notificar(Evento.NUEVO_JUGADOR_AGREGADO, jugador);
            jugadoresCompletos();
        }
    }

    private void jugadoresCompletos(){
        if (this.jugadores.size()==cantidadJugadores){
            notificar(Evento.JUGADORES_COMPLETOS);
            nuevaRonda();
        }
    }

    /**
     * Asigna un turno, genera un numero aleatorio entre la cantidad de jugadores.
     * @return void
     **/
    public void asignarTurno(){
        Random random= new Random();
        int indiceTurnoAleatorio;
        indiceTurnoAleatorio= random.nextInt(jugadores.size());
        this.turno= indiceTurnoAleatorio;
        notificar(Evento.NUEVO_TURNO, jugadorActual());
    }

    /**
     * Cambia el turno al siguiente jugador.
     * @return void
     **/
    public void cambiarTurno(){
        if (this.turno==jugadores.size()-1){
            this.turno= 0;
        }
        else{
            this.turno+=1;
        }
        notificar(Evento.NUEVO_TURNO, jugadorActual());
    }

    /**
     * Retorna el jugador actual, es decir, el jugador que posee el turno actual.
     * @return void
     **/
    public Jugador jugadorActual(){
        return jugadores.get(turno);
    }

    public int cantidadCartasJugadorActual(){
        Jugador j= jugadorActual();
        return j.getCantidadCartas();
    }

    /**
     * Inicializa y carga todas las rondas para comenzar con el juego.
     * @return void
     **/
    private void cargarRondas(){
        //Inicializo todas las rondas y las cargo en el juego
        Ronda ronda;
        for (int i= 0; i<10; i++){
            switch (i){
                case 0:
                    ronda= new Ronda(1);
                    ronda.añadirForma(Forma.TRIO, 2);
                    rondas.add(ronda);
                    break;
                case 1:
                    ronda= new Ronda(2);
                    ronda.añadirForma(Forma.ESCALA, 1);
                    ronda.añadirForma(Forma.TRIO, 1);
                    rondas.add(ronda);
                    break;
                case 2:
                    ronda= new Ronda(3);
                    ronda.añadirForma(Forma.ESCALA, 2);
                    rondas.add(ronda);
                    break;
                case 3:
                    ronda= new Ronda(4);
                    ronda.añadirForma(Forma.TRIO, 3);
                    rondas.add(ronda);
                    break;
                case 4:
                    ronda= new Ronda(5);
                    ronda.añadirForma(Forma.TRIO, 2);
                    ronda.añadirForma(Forma.ESCALA, 1);
                    rondas.add(ronda);
                    break;
                case 5:
                    ronda= new Ronda(6);
                    ronda.añadirForma(Forma.ESCALA, 2);
                    ronda.añadirForma(Forma.TRIO, 1);
                    rondas.add(ronda);
                    break;
                case 6:
                    ronda= new Ronda(7);
                    ronda.añadirForma(Forma.ESCALA, 3);
                    rondas.add(ronda);
                    break;
                case 7:
                    ronda= new Ronda(8);
                    ronda.añadirForma(Forma.TRIO, 4);
                    rondas.add(ronda);
                    break;
                case 8:
                    ronda= new Ronda(9);
                    ronda.añadirForma(Forma.ESCALERASUCIA, 1);
                    rondas.add(ronda);
                    break;
                case 9:
                    ronda= new Ronda(10);
                    ronda.añadirForma(Forma.ESCALERAREAL, 1);
                    rondas.add(ronda);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Indica si finalizo la ronda, es decir, si el jugador actual no posee cartas.
     * @param actual: jugador actual
     * @return void
     **/
    public boolean finRonda(Jugador actual){
        if (actual.sinCartas()){
            cargarPuntos();
            notificar(Evento.TERMINO_RONDA);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Recorre los jugadores, calcula los puntos de cada uno y los carga.
     * @return void
     **/
    public void cargarPuntos(){
        for (int i=0; i<jugadores.size(); i++){
            int puntos= jugadores.get(i).calcularPuntosRonda();
            jugadores.get(i).incrementarPuntos(puntos);
        }
    }

    /**
     * Inicializa una nueva ronda siempre y cuando el juego no haya terminado,
     * limpia las cartas bajadas de cada jugador y vuelve a repartir.
     * @return void
     **/
    public void nuevaRonda(){
        this.numeroRondaActual+=1;
        if (numeroRondaActual<=10){
            this.rondaActual= rondas.get(this.numeroRondaActual-1);
            for (int i=0; i<jugadores.size(); i++){
                jugadores.get(i).limpiarCartasBajadas();
                jugadores.get(i).setSeBajo(false);
            }
            repartir();
            notificar(Evento.NUEVA_RONDA);
            asignarTurno();
        }
        else{
            finJuego();
        }
    }

    /**
     * Controla si el juego termino, si es asi, determina el ganador del juego.
     * @return boolean: indica si el juego finalizo
     **/
    public boolean finJuego(){
        if (this.numeroRondaActual+1 > 10){
            notificar(Evento.TERMINO_JUEGO);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Verifica si algun jugador se quedo sin cartas y lo retorna.
     * @return Jugador: ganador de la ronda
     **/
    public Jugador ganadorRonda(){
        Jugador j= null;
        for (int i=0; i<jugadores.size(); i++){
            if (jugadores.get(i).sinCartas()){
                j= jugadores.get(i);
            }
        }
        return j;
    }

    /**
     * Verifica e indica quien fue el ganador del juego, controlando quien es el que posee
     * la menor cantidad de puntos.
     * @return Jugador: ganador del juego
     **/
    public Jugador ganadorJuego(){
        int minimoPuntos=0;
        Jugador ganador= null;
        int flag= 0;
        for (int i=0; i<jugadores.size(); i++){
            if (flag==0){
                ganador= jugadores.get(i);
                minimoPuntos= jugadores.get(i).getPuntos();
                flag= 1;
            }
            else{
                if (jugadores.get(i).getPuntos() < minimoPuntos){
                    ganador= jugadores.get(i);
                    minimoPuntos= jugadores.get(i).getPuntos();
                } else if (jugadores.get(i).getPuntos() == minimoPuntos) {
                    return null;
                }
            }
        }
        return ganador;
    }

    /**
     * Verifica si el jugador actual se bajo, es decir, formo las cartas indicadas
     * en la ronda.
     * @return boolean: indica si el jugador se bajo
     **/
    public boolean jugadorActualSeBajo(){
        return jugadorActual().getSeBajo();
    }

    /**
     * Hace que el jugador actual recoja una carta del mazo.
     * @return void
     **/
    public void jugadorActualTomarCartaMazo(){
        jugadorActual().recogerCarta(this.mazo, true);
        notificar(Evento.JUGADOR_TOMO_CARTA_MAZO, jugadorActual());
    }

    /**
     * Hace que el jugador actual recoja una carta del pozo.
     * @return void
     **/
    public void jugadorActualTomarCartaPozo(){
        jugadorActual().recogerCarta(this.mazo, false);
        notificar(Evento.JUGADOR_TOMO_CARTA_POZO, jugadorActual());
    }

    /**
     * Hace que el jugador actual deje una carta en el pozo.
     * @param indice: indica la posicion de carta a dejar
     * @return void
     **/
    public void jugadorActualDejarCarta(int indice){
        if (indice>=1 && indice<=cantidadCartasJugadorActual()){
            jugadorActual().dejarCarta(this.mazo, indice);
            notificar(Evento.JUGADOR_DEJO_CARTA, jugadorActual());
        }
    }

    /**
     * Verifica si el jugador actual puede bajarse, es decir, formar las formas que
     * indica la ronda.
     * @return boolean: determina si el jugador pudo bajarse
     **/
    public boolean jugadorActualPuedeBajarse(){
        boolean puedeBajarse= jugadorActual().bajarse(this.rondaActual);
        if (puedeBajarse==true){
            notificar(Evento.JUGADOR_SE_BAJO, jugadorActual());
        }
        else{
            notificar(Evento.JUGADOR_NO_PUDO_BAJARSE, jugadorActual());
        }
        return puedeBajarse;
    }

    /**
     * Verifica si el jugador actual pudo bajar la carta indicada.
     * @param indice: indica la posicion de la carta a dejar
     * @return boolean: indica si la carta pudo ser bajada
     **/
    public boolean jugadorActualPudoBajarCarta(int indice){
        if (indice>=1 && indice<=cantidadCartasJugadorActual()){
            boolean pudoBajarCarta= jugadorActual().bajarCarta(indice, this.jugadores);
            if (pudoBajarCarta==true){
                notificar(Evento.JUGADOR_BAJO_CARTA, jugadorActual());
            }
            else{
                notificar(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA, jugadorActual());
            }
            return pudoBajarCarta;
        }
        else{
            notificar(Evento.JUGADOR_NO_PUDO_BAJAR_CARTA, jugadorActual());
            return false;
        }
    }

    /**
     * Verifica si el jugador actual no posee cartas.
     * @return boolean: indica si el jugador actual no posee cartas
     **/
    public boolean jugadorActualSinCartas(){
        return jugadorActual().sinCartas();
    }

    /**
     * Obtiene el tope del pozo.
     * @return String: tostring de la carta que se encuentra en el tope del pozo
     **/
    public String obtenerTopePozo(){
        return this.mazo.obtenerTopePozo();
    }

    /**
     * Obtiene todas las cartas del jugador actual.
     * @return Arraylist</Carta>: cartas del jugador actual
     **/
    public ArrayList<Carta> cartasJugadorActual(){
        return jugadorActual().getCartas();
    }

    public int obtenerNumeroRondaActual(){
        return rondaActual.getNumero();
    }

    public HashMap<Forma, Integer> obtenerFormasRondaActual(){
        return rondaActual.getFormas();
    }

    public int cantidadJugadores(){
        return jugadores.size();
    }

    public ArrayList<Bajada> formasArmadas(int indice){
        return jugadores.get(indice).getFormasArmadas();
    }

    public ArrayList<Bajada> formasArmadas(Jugador jugador){
        return jugador.getFormasArmadas();
    }

    public String obtenerNickname(Jugador jugador){
        return jugador.getNickname();
    }

    public Forma obtenerNombreForma(Bajada bajada){
        return bajada.getNombreForma();
    }

    public ArrayList<Carta> cartasQueFormanLaBajada(Bajada bajada){
        return bajada.getCartasQueLaForman();
    }

    public int obtenerPuntosJugador(Jugador jugador){
        return jugador.getPuntos();
    }

    public ArrayList<Carta> obtenerCartasJugador(String nickname){
        for (int i=0; i<jugadores.size(); i++){
            if (jugadores.get(i).getNickname().equals(nickname)){
                return jugadores.get(i).getCartas();
            }
        }
        return null;
    }

    public String nicknameJugadorActual(){
        return jugadorActual().getNickname();
    }

    public int cantidadTotalFormasArmadas(){
        int total=0;
        for (int i=0; i<jugadores.size(); i++){
            total+= jugadores.get(i).getFormasArmadas().size();
        }
        return total;
    }

    public void continuarRonda(){
        Jugador actual = jugadorActual();
        boolean terminoRonda = finRonda(actual);
        if (terminoRonda == true) {
            nuevaRonda();
            return;
        }
        cambiarTurno();
    }

    //Observer
    @Override
    public void addObserver(Controlador controlador) {
        misObservadores.add(controlador);
    }

    @Override
    public void deleteObserver(Controlador controlador) {
        misObservadores.remove(controlador);
    }

    @Override
    public void notificar(Evento evento, Jugador jugador) {
        for (int i=0; i<misObservadores.size(); i++){
            misObservadores.get(i).update(this, evento, jugador);
        }
    }

    public void notificar(Evento evento) {
        for (int i=0; i<misObservadores.size(); i++){
            misObservadores.get(i).update(this, evento);
        }
    }

    //
    public void addObserverPP(Controlador2 controlador){
        observadoresPantallaPrincipal.add(controlador);
    }

    public void notificarPP(Evento evento){
        for (int i=0; i<observadoresPantallaPrincipal.size(); i++){
            observadoresPantallaPrincipal.get(i).update(this, evento);
        }
    }
    //

    /*Setters*/
    public void setCantidadJugadores(int cantidadJugadores){
        this.cantidadJugadores= cantidadJugadores;
        notificarPP(Evento.CANTIDAD_JUGADORES_INGRESADA);
    }

    /*Getters*/
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<Ronda> getRondas() {
        return rondas;
    }

    public Ronda getRondaActual() {
        return rondaActual;
    }

    public int getNumeroRondaActual() {
        return numeroRondaActual;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public int getTurno() {
        return turno;
    }

    public int getCantidadJugadores(){
        return this.cantidadJugadores;
    }

    public int getCantidadJugadoresAgregados(){
        return this.cantidadJugadoresAgregados;
    }

}
