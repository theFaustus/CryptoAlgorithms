/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.si.rsaalgorithm;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import javafx.util.Pair;

/**
 *
 * @author Faust
 */
public class RsaAlgorithm {

    private BigInteger p;

    private BigInteger q;

    private BigInteger n;

    private BigInteger phi;

    private BigInteger e;

    private BigInteger d;

    private int bitlength = 2048;

    private Random r;

    private final BigInteger k = new BigInteger("2");

    public RsaAlgorithm() {

        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);

    }

    public RsaAlgorithm(Pair<BigInteger, BigInteger> privateKey) {

        n = privateKey.getValue();
        d = privateKey.getKey();

    }

    public byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, n).toByteArray();
    }

    public byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, n).toByteArray();
    }

    public Pair<BigInteger, BigInteger> getPrivateKey() {
        return new Pair<>(d, n);
    }

    public static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b) + " ";
        }
        return test;
    }

    public static byte[] stringOfBytesToBytes(String stringOfBytes) {
        String[] bytesString = stringOfBytes.split(" ");
        byte[] bytes = new byte[bytesString.length];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = Byte.parseByte(bytesString[i]);
        }
        return bytes;
    }
}
