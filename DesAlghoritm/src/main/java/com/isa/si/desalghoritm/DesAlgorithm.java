/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.si.desalghoritm;

import static com.isa.si.desalghoritm.DesConversion.*;
import static com.isa.si.desalghoritm.DesHelper.*;
import static com.isa.si.desalghoritm.DesTables.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javafx.util.Pair;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Faust
 */
public class DesAlgorithm {

    public String encrypt(String hexContent, String hexKey) {
        String keyAfterPermutation = applyPermutation(hexStringToBin(hexKey), PERMUTATION_CHOICE_1);
        List<String> keysAfterRotation = applyDivideAndLeftRotationOnKey(keyAfterPermutation);
        String messageAfterPermutation = applyPermutation(hexStringToBin(hexContent), INITIAL_PERMUTATION);
        Pair<String, String> dividedMessage = divide(messageAfterPermutation);
        for (int i = 0; i < keysAfterRotation.size(); ++i) {
            String left = dividedMessage.getValue();
            String right = dividedMessage.getKey();
            String result = applyFunctionF(left, keysAfterRotation.get(i));
            right = bitSetToString(applyXorOperation(binStringToBitSet(right), binStringToBitSet(result)), 32);
            dividedMessage = new Pair<>(left, right);
        }
        String reversedAndConcatinatedMessage = dividedMessage.getValue() + dividedMessage.getKey();
        String permutted = applyPermutation(reversedAndConcatinatedMessage, FINAL_PERMUTATION);
        return binStringToHex(permutted);
    }

    public String decrypt(String hexContent, String hexKey) {
        String keyAfterPermutation = applyPermutation(hexStringToBin(hexKey), PERMUTATION_CHOICE_1);
        List<String> keysAfterRotation = applyDivideAndLeftRotationOnKey(keyAfterPermutation);
        String messageAfterPermutation = applyPermutation(hexStringToBin(hexContent), INITIAL_PERMUTATION);
        Pair<String, String> dividedMessage = divide(messageAfterPermutation);
        for (int i = keysAfterRotation.size() - 1; i >= 0; --i) {
            String left = dividedMessage.getValue();
            String right = dividedMessage.getKey();
            String result = applyFunctionF(left, keysAfterRotation.get(i));
            right = bitSetToString(applyXorOperation(binStringToBitSet(right), binStringToBitSet(result)), 32);
            dividedMessage = new Pair<>(left, right);
        }
        String reversedAndConcatinatedMessage = dividedMessage.getValue() + dividedMessage.getKey();
        String permutted = applyPermutation(reversedAndConcatinatedMessage, FINAL_PERMUTATION);
        return binStringToHex(permutted);
    }

    public byte[] decrypt(byte[] data, byte[] key) throws DecoderException {
        List<byte[]> dataBlocks = createBlocks(data, 16);
        String hexKey = asHexString(key);
        StringBuilder result = new StringBuilder();
        for (byte[] block : dataBlocks) {
            String hexData = new String(block);
            String decrypted = decrypt(hexData, hexKey);
            result.append(decrypted);
        }
        return Hex.decodeHex(result.toString().toCharArray());
    }

    public byte[] encrypt(byte[] data, byte[] key) throws UnsupportedEncodingException {
        List<byte[]> dataBlocks = createBlocks(data);
        String hexKey = asHexString(key);
        StringBuilder result = new StringBuilder();
        for (byte[] block : dataBlocks) {
            String hexData = asHexString(block);
            String encrypted = encrypt(hexData, hexKey);
            result.append(encrypted);
        }
        return (result.toString()).getBytes("UTF-8");
    }

}
