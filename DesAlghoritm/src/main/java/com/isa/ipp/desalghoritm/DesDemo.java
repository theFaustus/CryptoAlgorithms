/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.ipp.desalghoritm;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Faust
 */
public class DesDemo {

    private static final String ENCRYPTION_KEY = "5C84C203069AAAAA";  //6666666666666666666
    private static final String TO_ENCRYPT = "002BDC546291F4B1"; //‭12345678987654321‬
    private static final String HELLO__JO = "Hello Jo";

    public static void main(String[] args) {
        DesAlgorithm d = new DesAlgorithm();
        System.out.println("\nTo encrypt " + TO_ENCRYPT);
        System.out.println("Encryption key " + ENCRYPTION_KEY);
        String encrypt = d.encrypt(TO_ENCRYPT, ENCRYPTION_KEY);
        System.out.println("Encrypted " + encrypt + " with " + ENCRYPTION_KEY + " key.");
        String decrypt = d.decrypt(encrypt, ENCRYPTION_KEY);
        System.out.println("Decrypted " + decrypt + " with " + ENCRYPTION_KEY + " key.");
        try {
            System.out.println("\nTo encrypt " + HELLO__JO);
            System.out.println("Encryption key " + ENCRYPTION_KEY);
            encrypt = d.encrypt(Hex.encodeHexString(HELLO__JO.getBytes("UTF-8")), ENCRYPTION_KEY);
            System.out.println("Encrypted " + encrypt + " with " + ENCRYPTION_KEY + " key.");
            decrypt = new String(Hex.decodeHex((d.decrypt(encrypt, ENCRYPTION_KEY)).toCharArray()));
            System.out.println("Decrypted " + decrypt + " with " + ENCRYPTION_KEY + " key.");
        } catch (Exception ex) {
            Logger.getLogger(DesDemo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
