package com.peleadegallos;

import java.util.ArrayList;

public class ObjetoGuardado {
    private ArrayList<Record> records;
    private int tiempo, partidas,balas;

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getPartidas() {
        return partidas;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    public int getBalas() {
        return balas;
    }

    public void setBalas(int balas) {
        this.balas = balas;
    }

    public void addRecord(Record record){
        this.balas+=record.balas;
        this.tiempo+=record.tiempo;
        this.partidas++;
        this.records.add(record);
    }

    public ObjetoGuardado(ArrayList<Record> records, int tiempo, int partidas, int balas) {
        this.records = records;
        this.tiempo = tiempo;
        this.partidas = partidas;
        this.balas = balas;
    }

    public ObjetoGuardado(){
        this.partidas=0;
        this.tiempo=0;
        this.balas=0;
        this.records=new ArrayList<>();
    }
}
