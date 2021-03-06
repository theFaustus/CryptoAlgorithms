package com.isa.si.rsaalgorithm.controller;

import com.isa.si.rsaalgorithm.RsaAlgorithm;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.util.Pair;

public class FXMLController implements Initializable {

    private File file;

    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private TextArea privateKeyTextArea;

    @FXML
    private void onEncryptButton(ActionEvent event) {
        file = getFile();
        String toEncrypt = readFile();
        outputTextArea.setText(toEncrypt);
        RsaAlgorithm rsaAlgorithm = new RsaAlgorithm();
        byte[] encrypt = rsaAlgorithm.encrypt(toEncrypt.getBytes());
        final String encrypted = RsaAlgorithm.bytesToString(encrypt);
        outputTextArea.setText(encrypted);
        privateKeyTextArea.setText(rsaAlgorithm.getPrivateKey().getKey() + " " + rsaAlgorithm.getPrivateKey().getValue());
        file = getFile();
        saveFile(encrypted);
    }

    @FXML
    private void onDecryptButton(ActionEvent event) {
        if (!privateKeyTextArea.getText().trim().isEmpty()) {
            file = getFile();
            String toDecrypt = readFile();
            outputTextArea.setText(toDecrypt);
            String[] split = privateKeyTextArea.getText().split(" ");
            RsaAlgorithm rsaAlgorithm = new RsaAlgorithm(new Pair<>(new BigInteger(split[0]), new BigInteger(split[1])));
            byte[] decrypt = rsaAlgorithm.decrypt(RsaAlgorithm.stringOfBytesToBytes(toDecrypt));
            outputTextArea.setText(new String(decrypt));
            privateKeyTextArea.clear();
            file = getFile();
            saveFile(new String(decrypt));
        } else {
            alertThatNoPrivateKeyEntered();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outputTextArea.setWrapText(true);
        privateKeyTextArea.setWrapText(true);
    }

    @FXML
    public void alertThatFileNotLoaded() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Oops, looks like something went wrong.\nWe couldn`t open your file.", ButtonType.CLOSE);
        alert.setHeaderText("Not loaded");
        alert.setTitle("Error Dialog");
        alert.showAndWait();
    }

    @FXML
    public void alertThatNoPrivateKeyEntered() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Oops, looks like something went wrong.\nThere is no private key.", ButtonType.CLOSE);
        alert.setHeaderText("Private key not found");
        alert.setTitle("Error Dialog");
        alert.showAndWait();
    }

    private File getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter("Files", "*.*")); // files
        return fileChooser.showOpenDialog(null);
    }

    private String readFile() {
        InputStream is = null;
        BufferedReader buf = null;
        String result = "";

        if (file != null) {
            try {
                is = new FileInputStream(file);
                buf = new BufferedReader(new InputStreamReader(is));
                String line;

                while ((line = buf.readLine()) != null) {
                    result += line;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alertThatFileNotLoaded();
        }
        return result;
    }

    private void saveFile(String content) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
