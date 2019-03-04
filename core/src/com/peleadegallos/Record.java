package com.peleadegallos;

public class Record {

    String jugador1, jugador2, mapa, arma1, arma2,tiempo;
    int  balas, idGanador;

    public Record(String jugador1, String jugador2, String mapa, String arma1, String arma2, String tiempo, int balas,int idGanador) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.mapa = mapa;
        this.arma1 = arma1;
        this.arma2 = arma2;
        this.tiempo = tiempo;
        this.balas = balas;
        this.idGanador=idGanador;
    }
}
