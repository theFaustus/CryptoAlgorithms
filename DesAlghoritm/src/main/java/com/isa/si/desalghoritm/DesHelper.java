/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.si.desalghoritm;

import static com.isa.si.desalghoritm.DesConversion.*;
import static com.isa.si.desalghoritm.DesTables.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Faust
 */
public class DesHelper {

    public static List<String> applyDivideAndLeftRotationOnKey(String key) {
        List<String> result = new ArrayList<>();
        Pair<String, String> dividedKey = divide(key);
        for (int i = 0; i < ROTATIONS.length; ++i) {
            BitSet C = binStringToBitSet(dividedKey.getKey());
            BitSet D = binStringToBitSet(dividedKey.getValue());
            applyLeftRotation(C, 28, ROTATIONS[i]);
            applyLeftRotation(D, 28, ROTATIONS[i]);
            Pair<String, String> rotatedKeyParts = new Pair<>(bitSetToString(C, 28), bitSetToString(D, 28));
            String concatenatedKey = rotatedKeyParts.getKey() + rotatedKeyParts.getValue();
            String keyAfterPermutation = applyPermutation(concatenatedKey, PERMUTATION_CHOICE_2);
            result.add(keyAfterPermutation);
            dividedKey = divide(concatenatedKey);
        }
        return result;
    }

    public static Pair<String, String> divide(String key) {
        int middleIndex = key.length() / 2;
        String C = key.substring(0, middleIndex);
        String D = key.substring(middleIndex);
        return new Pair<>(C, D);
    }

    public static String applyFunctionF(String messagePart, String key) {
        String expandedPart = applyPermutation(messagePart, E_BIT_SELECTION);
        BitSet keyBits = binStringToBitSet(key);
        BitSet messageBits = binStringToBitSet(expandedPart);
        BitSet xorResult = applyXorOperation(keyBits, messageBits);
        String[] bitBlocks = stringToSixBit(bitSetToString(xorResult, 48));
        String sBoxesResult = applySBoxes(bitBlocks);
        return applyPermutation(sBoxesResult, PERMUTATION);
    }

    public static String applyPermutation(String key, int[] permutationTable) {
        StringBuilder permutedKey = new StringBuilder();
        for (int i : permutationTable) {
            permutedKey.append(key.charAt(i - 1));
        }
        return permutedKey.toString();
    }

    public static String applySBoxes(String[] blocks) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < blocks.length; ++i) {
            String block = blocks[i];
            int row = Integer.parseInt("" + block.charAt(0) + block.charAt(block.length() - 1), 2);
            int col = Integer.parseInt(block.substring(1, block.length() - 1), 2);
            int[][] sBox = getSBox(i + 1);
            int sBoxValue = sBox[row][col];
            result.append(hexStringToBin(sBoxValue + ""));
        }
        return result.toString();
    }

    public static void applyLeftRotation(BitSet bs, int n, int times) {
        for (int i = 0; i < times; ++i) {
            bs = bs.get(n, Math.max(n, bs.length()));
        }
    }

    public static BitSet applyXorOperation(BitSet first, BitSet second) {
        BitSet result = (BitSet) first.clone();
        result.xor(second);
        return result;
    }

    public static int[][] getSBox(int i) {
        switch (i) {
            case 1:
                return S_BOX_1;
            case 2:
                return S_BOX_2;
            case 3:
                return S_BOX_3;
            case 4:
                return S_BOX_4;
            case 5:
                return S_BOX_5;
            case 6:
                return S_BOX_6;
            case 7:
                return S_BOX_7;
            case 8:
                return S_BOX_8;

        }
        return null;
    }

}
