/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eugeniosolucoes.util;

/**
 *
 * @author eugenio
 */
public class Picture {

    Type type;

    int length;

    boolean comma;

    public Picture( Type type, int length ) {
        this.type = type;
        this.length = length;
    }

    public Picture( Type type, int length, boolean comma ) {
        this( type, length );
        this.comma = comma;
    }

    public Type getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public boolean isComma() {
        return comma;
    }

    @Override
    public String toString() {
        return "Picture{" + "type=" + type + ", length=" + length + ", comma=" + comma + '}';
    }
    
    public static enum Type {
        /**
         * TIPO STRING
         */
        S, 
        /**
         *  TIPO NUMERICO
         */
        N;
    }

}
