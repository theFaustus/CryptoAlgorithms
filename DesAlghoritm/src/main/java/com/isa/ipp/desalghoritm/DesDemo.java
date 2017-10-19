/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.ipp.desalghoritm;

/**
 *
 * @author Faust
 */
public class DesDemo {

    public static void main(String[] args) {
        DesAlgorithm d = new DesAlgorithm();
        String encrypt = d.encrypt("0123456789ABCDEF", "133457799BBCDFF1");
        System.out.println(encrypt);
        String decrypt = d.decrypt(encrypt, "133457799BBCDFF1");
        System.out.println(decrypt);
    }
}
