/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.si.desalghoritm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

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

    public static byte[] createBlock(byte[] bytes, int blockIndex, int blockSize) {
        byte[] block = new byte[blockSize];
        System.arraycopy(bytes, blockIndex * blockSize, block, 0, blockSize);
        return block;
    }

    public static List<byte[]> createBlocks(byte[] messageBytes) {
        return DesConversion.createBlocks(messageBytes, 8);
    }

    public static String asHexString(byte[] data) {
        return DatatypeConverter.printHexBinary(data);
    }

    public static List<byte[]> createBlocks(byte[] messageBytes, int blockSize) {
        List<byte[]> blocks = new ArrayList<>();
        int numberOfBlocks = messageBytes.length / blockSize;
        int remainingBytes = messageBytes.length % blockSize;
        for (int i = 0; i < numberOfBlocks; ++i) {
            blocks.add(createBlock(messageBytes, i, blockSize));
        }
        if (remainingBytes != 0) {
            byte[] block = new byte[blockSize];
            System.arraycopy(messageBytes, numberOfBlocks * blockSize, block, 0, remainingBytes);
            blocks.add(block);
        }
        return blocks;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
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
