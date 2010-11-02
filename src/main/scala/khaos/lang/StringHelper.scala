package khaos.lang

import java.io.UnsupportedEncodingException
import _root_.scala.util.Random

object StringHelper {
	
	private val RANDOM = new Random()
	
	/**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of characters whose
     * ASCII value is between <code>32</code> and <code>126</code> (inclusive).</p>
     *
     * @param count  the length of random string to create
     * @return the random string
     */
    def randomAscii(count:Int): String = {
        random(count, 32, 127, false, false);
    }
	
	/**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of alpha-numeric
     * characters.</p>
     *
     * @param count  the length of random string to create
     * @return the random string
     */
    def randomAlphanumeric(count:Int):String = {
        random(count, true, true);
    }
    
    /**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of alpha-numeric
     * characters as indicated by the arguments.</p>
     *
     * @param count  the length of random string to create
     * @param letters  if <code>true</code>, generated string will include
     *  alphabetic characters
     * @param numbers  if <code>true</code>, generated string will include
     *  numeric characters
     * @return the random string
     */
    def random(count:Int, letters:Boolean, numbers:Boolean):String = {
        random(count, 0, 0, letters, numbers);
    }
    
    /**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of alpha-numeric
     * characters as indicated by the arguments.</p>
     *
     * @param count  the length of random string to create
     * @param start  the position in set of chars to start at
     * @param end  the position in set of chars to end before
     * @param letters  if <code>true</code>, generated string will include
     *  alphabetic characters
     * @param numbers  if <code>true</code>, generated string will include
     *  numeric characters
     * @return the random string
     */
    def random(count:Int, start:Int, end:Int, letters:Boolean, numbers:Boolean):String = {
        random(count, start, end, letters, numbers, null, RANDOM);
    }
    
    /**
     * <p>Creates a random string whose length is the number of characters
     * specified.</p>
     *
     * <p>Characters will be chosen from the set of characters specified.</p>
     *
     * @param count  the length of random string to create
     * @param chars  the character array containing the set of characters to use,
     *  may be null
     * @return the random string
     * @throws IllegalArgumentException if <code>count</code> &lt; 0.
     */
    def random(count:Int, chars:Array[Char]):String = {
        if (chars == null) {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, 0, chars.length, false, false, chars, RANDOM);
    }

    /**
     * <p>Creates a random string based on a variety of options, using
     * supplied source of randomness.</p>
     *
     * <p>If start and end are both <code>0</code>, start and end are set
     * to <code>' '</code> and <code>'z'</code>, the ASCII printable
     * characters, will be used, unless letters and numbers are both
     * <code>false</code>, in which case, start and end are set to
     * <code>0</code> and <code>Integer.MAX_VALUE</code>.
     *
     * <p>If set is not <code>null</code>, characters between start and
     * end are chosen.</p>
     *
     * <p>This method accepts a user-supplied {@link Random}
     * instance to use as a source of randomness. By seeding a single 
     * {@link Random} instance with a fixed seed and using it for each call,
     * the same random sequence of strings can be generated repeatedly
     * and predictably.</p>
     *
     * @param count  the length of random string to create
     * @param start  the position in set of chars to start at
     * @param end  the position in set of chars to end before
     * @param letters  only allow letters?
     * @param numbers  only allow numbers?
     * @param chars  the set of chars to choose randoms from.
     *  If <code>null</code>, then it will use the set of all chars.
     * @param random  a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not
     *  <code>(end - start) + 1</code> characters in the set array.
     * @throws IllegalArgumentException if <code>count</code> &lt; 0.
     */
	def random(count:Int, start:Int, end:Int, letters:Boolean, numbers:Boolean, 
			chars:Array[Char], random:Random): String = {
		var lstart = start
		var lend = end
		var lcount = count
		
		if (count == 0) {
			return ""
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		
		if (start==0 && end==0) {
			lend = 'z' + 1
			lstart = ' '
			if (!letters && !numbers) {
				lstart = 0
				lend = Integer.MAX_VALUE
			}
		}
		
		val buffer = new Array[Char](count)
		val gap = lend - lstart
		
		while (lcount != 0) {
			lcount -= 1
			
			var ch = ' '
			if (chars == null) {
				ch = (random.nextInt(gap) + start).asInstanceOf[Char]
			} else {
				ch = chars(random.nextInt(gap) + start)
			}
			if ((letters && Character.isLetter(ch))
					|| (numbers && Character.isDigit(ch))
					|| (!letters && !numbers)) {
				if (ch>=56320 && ch<=57343) {
					if (lcount == 0) {
						lcount += 1
					} else {
						buffer(lcount) = ch
						lcount -= 1
						buffer(lcount) = (55296 + random.nextInt(128)).asInstanceOf[Char]
					}
				} else if (ch>=55296 && ch<=56191) {
					if (lcount == 0) {
						lcount += 1
					} else {
						buffer(lcount) = (56320 + random.nextInt(128)).asInstanceOf[Char]
						lcount -= 1
						buffer(lcount) = ch
					}
				} else if(ch >= 56192 && ch <= 56319) {
					lcount += 1
				} else {
					buffer(lcount) = ch
				}
			} else {
				lcount += 1
			}
		}
		new String(buffer)
	}
	
	/**
     * Encodes the given string into a sequence of bytes using the UTF-8 charset, storing the result into a new byte
     * array.
     * 
     * @param string
     *            the String to encode, may be <code>null</code>
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws IllegalStateException
     *             Thrown when the charset is missing, which should be never according the the Java specification.
     * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/nio/charset/Charset.html">Standard charsets</a>
     * @see #getBytesUnchecked(String, String)
     */
    def getBytesUtf8(string: String) = getBytesUnchecked(string, "UTF-8")
    
    /**
     * Encodes the given string into a sequence of bytes using the named charset, storing the result into a new byte
     * array.
     * <p>
     * This method catches {@link UnsupportedEncodingException} and rethrows it as {@link IllegalStateException}, which
     * should never happen for a required charset name. Use this method when the encoding is required to be in the JRE.
     * </p>
     * 
     * @param string
     *            the String to encode, may be <code>null</code>
     * @param charsetName
     *            The name of a required {@link java.nio.charset.Charset}
     * @return encoded bytes, or <code>null</code> if the input string was <code>null</code>
     * @throws IllegalStateException
     *             Thrown when a {@link UnsupportedEncodingException} is caught, which should never happen for a
     *             required charset name.
     * @see CharEncoding
     * @see String#getBytes(String)
     */
    def getBytesUnchecked(string:String, charsetName:String) = {
        if (string == null) {
            null
        } else
        try {
            string.getBytes(charsetName);
        } catch {
        	case ex: UnsupportedEncodingException =>
            throw newIllegalStateException(charsetName, ex);
        }
    }
    
    private def newIllegalStateException(charsetName:String, e:UnsupportedEncodingException) = {
        new IllegalStateException(charsetName + ": " + e)
    }
}