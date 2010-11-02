package khaos.codec
import khaos.lang.StringHelper

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

object DigestHelper {

	/**
     * Returns a <code>MessageDigest</code> for the given <code>algorithm</code>.
     * 
     * @param algorithm
     *            the name of the algorithm requested. See <a
     *            href="http://java.sun.com/j2se/1.3/docs/guide/security/CryptoSpec.html#AppA">Appendix A in the Java
     *            Cryptography Architecture API Specification & Reference</a> for information about standard algorithm
     *            names.
     * @return An MD5 digest instance.
     * @see MessageDigest#getInstance(String)
     * @throws RuntimeException
     *             when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    def getDigest(algorithm: String) = {
        try {
            MessageDigest.getInstance(algorithm);
        } catch {
        	case ex: NoSuchAlgorithmException=> throw new RuntimeException(ex.getMessage());
        }
    }
    
    /**
     * Returns an MD5 MessageDigest.
     * 
     * @return An MD5 digest instance.
     * @throws RuntimeException
     *             when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    private def getMd5Digest = getDigest("MD5")
    
    /**
     * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    def md5(data: Array[Byte]) = getMd5Digest.digest(data)
    
    /**
     * Calculates the MD5 digest and returns the value as a 16 element <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    def md5(data:String):Array[Byte] = md5(getBytesUtf8(data))
    
    
    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    def md5Hex(data:String) = HexHelper.encodeHexString(md5(data))
    
    /**
     * Calls {@link StringUtils#getBytesUtf8(String)}
     * 
     * @param data
     *            the String to encode
     * @return encoded bytes
     */
    def getBytesUtf8(data:String) = StringHelper.getBytesUtf8(data)
}