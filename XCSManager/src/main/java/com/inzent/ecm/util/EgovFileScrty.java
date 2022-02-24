package com.inzent.ecm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.codec.binary.Base64;

public class EgovFileScrty {
	private static final Logger logger = LoggerFactory.getLogger(EgovFileScrty.class);
	
    static final char FILE_SEPARATOR = File.separatorChar;
    
    static final int BUFFER_SIZE = 1024;
    
    public static boolean encryptFile(String source, String target) throws Exception {

	boolean result = false;

	String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
	String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
	File srcFile = new File(sourceFile);
	
	BufferedInputStream input = null;
	BufferedOutputStream output = null;
	
	byte[] buffer = new byte[BUFFER_SIZE];
	
	try {
	    if (srcFile.exists() && srcFile.isFile()) {
		
		input = new BufferedInputStream(new FileInputStream(srcFile));
		output = new BufferedOutputStream(new FileOutputStream(targetFile));
		
		int length = 0;
		while ((length = input.read(buffer)) >= 0) {
		    byte[] data = new byte[length];
		    System.arraycopy(buffer, 0, data, 0, length);
		    output.write(encodeBinary(data).getBytes());
		    output.write(System.getProperty("line.separator").getBytes());
		}
		
		result = true;
	    }
	} catch (Exception ex) {
		logger.debug(ex.getMessage());
	} finally {
	   if (input != null) {
	       try {
		   input.close();
	       } catch (Exception ignore) {
	    	   // 	no-op
	    	   logger.debug("IGNORE: " + ignore);
	    	   //Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " + ignore);	//ignore.printStackTrace();
	       }
	   }
	   if (output != null) {
	       try {
		   output.close();
	       } catch (Exception ignore) {
		   // no-op
	    	   logger.debug("IGNORE: " + ignore);
	    	   //Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " + ignore);	//ignore.printStackTrace();
	       }
	   }
	}
	return result;
    }

    public static boolean decryptFile(String source, String target) throws Exception {

	boolean result = false;

	String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
	String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
	File srcFile = new File(sourceFile);

	BufferedReader input = null;
	BufferedOutputStream output = null;
	
	//byte[] buffer = new byte[BUFFER_SIZE];
	String line = null;
	
	try {
	    if (srcFile.exists() && srcFile.isFile()) {
		
		input = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
		output = new BufferedOutputStream(new FileOutputStream(targetFile));
		
		while ((line = input.readLine()) != null) {
		    byte[] data = line.getBytes();
		    output.write(decodeBinary(new String(data)));
		}
		
		result = true;
	    }
	} catch (Exception ex) {
		logger.debug("EX: " + ex);
		//Logger.getLogger(EgovFileScrty.class).debug("EX: " + ex);	//ex.printStackTrace();
	} finally {
	   if (input != null) {
	       try {
		   input.close();
	       } catch (Exception ignore) {
	    	   //no-op
	    	   logger.debug("IGNORE: " + ignore);
	    	   //Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " + ignore);//ignore.printStackTrace();
	       }
	   }
	   if (output != null) {
	       try {
		   output.close();
	       } catch (Exception ignore) {
	    	   // no-op
	    	   logger.debug("IGNORE: " + ignore);
	    	   //Logger.getLogger(EgovFileScrty.class).debug("IGNORE: " + ignore);//ignore.printStackTrace();
	       }
	   }
	}
	return result;
    }

    public static String encodeBinary(byte[] data) throws Exception {
	if (data == null) {
	    return "";
	}

	return new String(Base64.encodeBase64(data));
    }
    
    public static String encode(String data) throws Exception {
	return encodeBinary(data.getBytes());
    }
    
    public static byte[] decodeBinary(String data) throws Exception {
	return Base64.decodeBase64(data.getBytes());
    }

    public static String decode(String data) throws Exception {
	return new String(decodeBinary(data));
    }

    public static String encryptPassword(String data) throws Exception {

	if (data == null) {
	    return "";
	}

	byte[] plainText = null; 
	byte[] hashValue = null; 
	plainText = data.getBytes();

	MessageDigest md = MessageDigest.getInstance("SHA-256");
	hashValue = md.digest(plainText);

	/*
	BASE64Encoder encoder = new BASE64Encoder();
	return encoder.encode(hashValue);
	*/
	return new String(Base64.encodeBase64(hashValue));
    }
}