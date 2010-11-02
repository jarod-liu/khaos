package khaos.codec

object HexHelper {
    /**
     * Used to build output as Hex
     */
    private val DIGITS_LOWER = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * Used to build output as Hex
     */
    private val DIGITS_UPPER = Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toDigits
     *            the output alphabet
     * @return A char[] containing hexadecimal characters
     * @since 1.4
     */
    protected def encodeHex(data:Array[Byte], toDigits:Array[Char]) = {
        val l = data.length;
        val out = new Array[Char](l << 1)
        // two characters form the hex value.
        var j = 0
        for (i<- 0 until l) {
            out(j) = toDigits((0xF0 & data(i)) >>> 4);
        	j+=1
            out(j) = toDigits(0x0F & data(i))
            j+=1
        }
        out
    }
	
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toLowerCase
     *            <code>true</code> converts to lowercase, <code>false</code> to uppercase
     * @return A char[] containing hexadecimal characters
     * @since 1.4
     */
    def encodeHex(data:Array[Byte], toLowerCase:Boolean):Array[Char] = encodeHex(data, if(toLowerCase) DIGITS_LOWER else DIGITS_UPPER)
	
    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    def encodeHex(data:Array[Byte]):Array[Char] = encodeHex(data, true)
	
    /**
     * Converts an array of bytes into a String representing the hexadecimal values of each byte in order. The returned
     * String will be double the length of the passed array, as it takes two characters to represent any given byte.
     * 
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A String containing hexadecimal characters
     * @since 1.4
     */
    def encodeHexString(data:Array[Byte]) = new String(encodeHex(data))
}