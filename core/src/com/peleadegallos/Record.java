package com.peleadegallos;

/**
 * Cada record almacena quien juega, con que arma, el tiempo, el ganador y las balas utilizadas
 */
public class Record {

    String jugador1, jugador2, mapa, arma1, arma2;

    int balas, idGanador, tiempo;

    /**
     * Inicializa el reocrd
     *
     * @param jugador1  el jugador 1
     * @param jugador2  el jugador 2
     * @param mapa      el mapa
     * @param arma1     el arma del jugador 1
     * @param arma2     el arma del jugador 2
     * @param tiempo    el tiempo
     * @param balas     las balas utilizadas
     * @param idGanador el id ganador
     */
    public Record(String jugador1, String jugador2, String mapa, String arma1, String arma2, int tiempo, int balas, int idGanador) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.mapa = mapa;
        this.arma1 = arma1;
        this.arma2 = arma2;
        this.tiempo = tiempo;
        this.balas = balas;
        this.idGanador = idGanador;
    }

    /**
     * Inicializa el reocrd, necesario para deserializar el archivo de guardado json
     */
    public Record() {

    }
}
