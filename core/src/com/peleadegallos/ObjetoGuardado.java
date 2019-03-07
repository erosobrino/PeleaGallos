package com.peleadegallos;

import java.util.ArrayList;

/**
 * The type Objeto guardado.
 */
public class ObjetoGuardado {
    private ArrayList<Record> records;
    private int tiempo, partidas,balas;

    /**
     * Gets records.
     *
     * @return the records
     */
    public ArrayList<Record> getRecords() {
        return records;
    }

    /**
     * Sets records.
     *
     * @param records the records
     */
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    /**
     * Gets tiempo.
     *
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Sets tiempo.
     *
     * @param tiempo the tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Gets partidas.
     *
     * @return the partidas
     */
    public int getPartidas() {
        return partidas;
    }

    /**
     * Sets partidas.
     *
     * @param partidas the partidas
     */
    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    /**
     * Gets balas.
     *
     * @return the balas
     */
    public int getBalas() {
        return balas;
    }

    /**
     * Sets balas.
     *
     * @param balas the balas
     */
    public void setBalas(int balas) {
        this.balas = balas;
    }

    /**
     * Add record.
     *
     * @param record the record
     */
    public void addRecord(Record record){
        this.balas+=record.balas;
        this.tiempo+=record.tiempo;
        this.partidas++;
        this.records.add(record);
    }

    /**
     * Instantiates a new Objeto guardado.
     *
     * @param records  the records
     * @param tiempo   the tiempo
     * @param partidas the partidas
     * @param balas    the balas
     */
    public ObjetoGuardado(ArrayList<Record> records, int tiempo, int partidas, int balas) {
        this.records = records;
        this.tiempo = tiempo;
        this.partidas = partidas;
        this.balas = balas;
    }

    /**
     * Instantiates a new Objeto guardado.
     */
    public ObjetoGuardado(){
        this.partidas=0;
        this.tiempo=0;
        this.balas=0;
        this.records=new ArrayList<>();
    }
}
