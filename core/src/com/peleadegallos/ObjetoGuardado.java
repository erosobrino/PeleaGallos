package com.peleadegallos;

import java.util.ArrayList;

/**
 * El objeto con los datos que se guardan
 */
public class ObjetoGuardado {
    private ArrayList<Record> records;
    private int tiempo, partidas,balas;

    /**
     * Devuelve records.
     *
     * @return los records
     */
    public ArrayList<Record> getRecords() {
        return records;
    }

    /**
     * Establece records.
     *
     * @param records los records
     */
    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    /**
     * Devuelve tiempo.
     *
     * @return el tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Establece tiempo.
     *
     * @param tiempo el tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Devuelve partidas.
     *
     * @return las partidas
     */
    public int getPartidas() {
        return partidas;
    }

    /**
     * Establece partidas.
     *
     * @param partidas las partidas
     */
    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    /**
     * Devuelve balas.
     *
     * @return las balas
     */
    public int getBalas() {
        return balas;
    }

    /**
     * Establece balas.
     *
     * @param balas las balas
     */
    public void setBalas(int balas) {
        this.balas = balas;
    }

    /**
     * Añade un record modificando el resto de datos
     *
     * @param record el record
     */
    public void addRecord(Record record){
        this.balas+=record.balas;
        this.tiempo+=record.tiempo;
        this.partidas++;
        this.records.add(record);
    }

    /**
     *Inicialia el objeto para guardado
     *
     * @param records  los records
     * @param tiempo   el tiempo total
     * @param partidas las partidas totales
     * @param balas    las balas totales
     */
    public ObjetoGuardado(ArrayList<Record> records, int tiempo, int partidas, int balas) {
        this.records = records;
        this.tiempo = tiempo;
        this.partidas = partidas;
        this.balas = balas;
    }

    /**
     * Inicialia el objeto para guardado con datos preestablecidos
     */
    public ObjetoGuardado(){
        this.partidas=0;
        this.tiempo=0;
        this.balas=0;
        this.records=new ArrayList<>();
    }
}
