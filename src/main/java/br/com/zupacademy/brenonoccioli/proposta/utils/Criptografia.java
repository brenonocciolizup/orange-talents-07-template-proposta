package br.com.zupacademy.brenonoccioli.proposta.utils;


import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Criptografia {

    private String secret = "${SECRET_CRIPTOGRAFIA}";

    public String criptografa(String documentoLimpo){
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(secret);

        String documentoCriptografado = textEncryptor.encrypt(documentoLimpo);
        return documentoCriptografado;
    }

    public String descriptografa(String documentoCriptografado){
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(secret);

        String documentoLimpo = textEncryptor.decrypt(documentoCriptografado);
        return documentoLimpo;
    }

    public byte[] gerarHash(String documento) {
       MessageDigest algoritmo = null;
       try{
           algoritmo = MessageDigest.getInstance("MD5");
       }catch (NoSuchAlgorithmException e){
           e.getStackTrace();
       }
       return algoritmo.digest(documento.getBytes(StandardCharsets.UTF_8));
    }

}
