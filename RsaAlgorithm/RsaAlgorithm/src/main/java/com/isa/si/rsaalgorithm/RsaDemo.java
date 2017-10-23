/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.si.rsaalgorithm;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Faust
 */
public class RsaDemo {
    private static final String TO_ENCRYPT = "Hello, Georgia!";

    public static void main(String[] args) throws IOException {

        RsaAlgorithm rsa = new RsaAlgorithm();
        
        System.out.println("Encrypting String: " + TO_ENCRYPT);

        System.out.println("String in Bytes: " + bytesToString(TO_ENCRYPT.getBytes()));

        byte[] encrypted = rsa.encrypt(TO_ENCRYPT.getBytes());

        System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

        byte[] decrypted = rsa.decrypt(encrypted);

        System.out.println("Decrypted String in Bytes: " + bytesToString(decrypted));

        System.out.println("Decrypted String: " + new String(decrypted));

    }

    private static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }
}
