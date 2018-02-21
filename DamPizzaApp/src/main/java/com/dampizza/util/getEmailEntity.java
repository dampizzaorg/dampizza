/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/*
 * Class that allows to get Email credentials in a string
 * @author Jon Xabier Gimenez
 */
public class getEmailEntity {

	public static void main(String[] args) {
		
	}
        
        public static String start(){
            String credentials= null;
            try {
			String key = "squirrel123"; // needs to be at least 8 characters for DES
                        //Take absolute Path of the files
                        String encrypt1 = new File("src/main/java/com/dampizza/util/Encrypted.txt").getAbsolutePath();
                        String decrypt1 = new File("src/main/java/com/dampizza/util/Decrypted.txt").getAbsolutePath();
                        //Replacing \ character making use of the pattern
                        encrypt1 = encrypt1.replaceAll("\\\\", "/");
                        decrypt1 = decrypt1.replaceAll("\\\\", "/");
                        //Inicializing the streams
			FileInputStream fis2 = new FileInputStream(encrypt1);
			FileOutputStream fos2 = new FileOutputStream(decrypt1);
                        //Decrypting data and saving in another file
			decrypt(key, fis2, fos2);
                        //Take data of the decrypted file
                        credentials = readAllBytesJava7(decrypt1);
                        //Delete Decrypted.txt
                        new File(decrypt1).delete();
		} catch (Throwable e) {
			e.printStackTrace();
		}
            return credentials;
        }

	private  static void decrypt(String key, InputStream is, OutputStream os) throws Throwable {
		Decrypt2(key, Cipher.DECRYPT_MODE, is, os);
	}
        
        private  static void Decrypt2(String key, int mode, InputStream is, OutputStream os) throws Throwable {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

		cipher.init(Cipher.DECRYPT_MODE, desKey);
		CipherOutputStream cos = new CipherOutputStream(os, cipher);
		doCopy(is, cos);	
	}

	private static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
                        
		}
		os.flush();
		os.close();
		is.close();
	}
        private static String readAllBytesJava7(String filePath){
        String content = "";
        try{
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }
    
}
