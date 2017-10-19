/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.ipp.desalghoritm;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Faust
 */
public class DesConversion {

    static final Map<String, String> binToHex = new HashMap<>();

    static {
        binToHex.put("0", "0000");
        binToHex.put("1", "0001");
        binToHex.put("2", "0010");
        binToHex.put("3", "0011");
        binToHex.put("4", "0100");
        binToHex.put("5", "0101");
        binToHex.put("6", "0110");
        binToHex.put("7", "0111");
        binToHex.put("8", "1000");
        binToHex.put("9", "1001");
        binToHex.put("A", "1010");
        binToHex.put("B", "1011");
        binToHex.put("C", "1100");
        binToHex.put("D", "1101");
        binToHex.put("E", "1110");
        binToHex.put("F", "1111");

    }

    public static String bitSetToString(BitSet bs, int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            result.append(bs.get(i) ? "1" : "0");
        }
        return result.toString();
    }

    public static String[] stringToSixBit(String s) {
        int howManyBlocks = s.length() / 6;
        String[] blocks = new String[howManyBlocks];
        for (int i = 0; i < howManyBlocks; ++i) {
            blocks[i] = s.substring(i * 6, i * 6 + 6);
        }
        return blocks;
    }

    public static String hexStringToBin(String s) {
        StringBuilder key = new StringBuilder();
        for (String hexValue : s.toUpperCase().split("")) {
            key.append(hexDigitToBinary(hexValue));
        }
        return key.toString();
    }

    private static String hexDigitToBinary(String hexDigit) {

        return binToHex.get(hexDigit);
    }

    public static BitSet binStringToBitSet(String s) {
        BitSet bs = new BitSet();
        int i = 0;
        for (String binValue : s.split("")) {
            if (binValue.equals("1")) {
                bs.set(i);
            }
            ++i;
        }
        return bs;
    }

    public static String binStringToHex(String s) {
        StringBuilder result = new StringBuilder();
        int howManyBlocks = s.length() / 4;
        String[] blocks = new String[howManyBlocks];
        for (int i = 0; i < howManyBlocks; ++i) {
            blocks[i] = s.substring(i * 4, i * 4 + 4);
        }
        for (String block : blocks) {
            result.append(binTetradeToHex(block));
        }
        return result.toString();
    }

    private static String binTetradeToHex(String block) {
        for (Map.Entry<String, String> entry : binToHex.entrySet()) {
            if (entry.getValue().equals(block)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
