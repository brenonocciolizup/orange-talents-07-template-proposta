package br.com.zupacademy.brenonoccioli.proposta.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class FingerPrintEncoded {

    private String FingerPrint;

    public FingerPrintEncoded(String fingerBase64){
        if(!Base64.isBase64(fingerBase64)){
            throw new IllegalArgumentException("não é um Base64 válido!");
        }

        this.FingerPrint = fingerBase64;
    }

    public String get() {
        return FingerPrint;
    }
}
