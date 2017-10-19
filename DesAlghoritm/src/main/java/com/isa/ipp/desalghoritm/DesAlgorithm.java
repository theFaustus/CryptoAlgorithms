/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isa.ipp.desalghoritm;

import static com.isa.ipp.desalghoritm.DesConversion.*;
import static com.isa.ipp.desalghoritm.DesHelper.*;
import static com.isa.ipp.desalghoritm.DesTables.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Faust
 */
public class DesAlgorithm {

    public String encrypt(String hexMessage, String hexKey) {
        String keyAfterPermutation = applyPermutation(hexStringToBin(hexKey), PERMUTATION_CHOICE_1);
        List<String> keysAfterRotation = applyDivideAndLeftRotationOnKey(keyAfterPermutation);
        String messageAfterPermutation = applyPermutation(hexStringToBin(hexMessage), INITIAL_PERMUTATION);
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

    public String decrypt(String hexMessage, String hexKey) {
        String keyAfterPermutation = applyPermutation(hexStringToBin(hexKey), PERMUTATION_CHOICE_1);
        List<String> keysAfterRotation = applyDivideAndLeftRotationOnKey(keyAfterPermutation);
        String messageAfterPermutation = applyPermutation(hexStringToBin(hexMessage), INITIAL_PERMUTATION);
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

}
