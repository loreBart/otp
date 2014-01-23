/*
 * %CopyrightBegin%
 * 
 * Copyright Ericsson AB 2004-2009. All Rights Reserved.
 * 
 * The contents of this file are subject to the Erlang Public License,
 * Version 1.1, (the "License"); you may not use this file except in
 * compliance with the License. You should have received a copy of the
 * Erlang Public License along with this software. If not, it can be
 * retrieved online at http://www.erlang.org/.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 * 
 * %CopyrightEnd%
 */
package com.ericsson.otp.erlang;

import java.util.regex.Pattern;

final class OtpSystem {
	private static final String IPV4_BASIC_PATTERN_STRING =
            "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}" + // initial 3 fields, 0-255 followed by .
             "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"; // final field, 0-255

    private static final Pattern IPV4_PATTERN =
        Pattern.compile("^" + IPV4_BASIC_PATTERN_STRING + "$");

    private static final Pattern IPV4_MAPPED_IPV6_PATTERN = // TODO does not allow for redundant leading zeros
            Pattern.compile("^::[fF]{4}:" + IPV4_BASIC_PATTERN_STRING + "$");

    private static final Pattern IPV6_STD_PATTERN =
        Pattern.compile(
                "^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
        Pattern.compile(
                "^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)" + // 0-6 hex fields
                 "::" +
                 "(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$"); // 0-6 hex fields
    /*
     *  The above pattern is not totally rigorous as it allows for more than 7 hex fields in total
     */
    private static final char COLON_CHAR = ':';

    // Must not have more than 7 colons (i.e. 8 fields)
    private static final int MAX_COLON_COUNT = 7;

    /**
     * Checks whether the parameter is a valid IPv4 or IPv6 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid IPv4 or IPv6 address
     */
    public static boolean isValidIPAddress(final String input) {
    	if (isIPv4Address(input))
    		return true;
        return isIPv6Address(input);
    }
    /**
     * Checks whether the parameter is a valid IPv4 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid IPv4 address
     */
    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid IPv6 address (including compressed).
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid standard or compressed IPv6 address
     */
    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public static boolean isIPv4MappedIPv64Address(final String input) {
        return IPV4_MAPPED_IPV6_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid standard (non-compressed) IPv6 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid standard (non-compressed) IPv6 address
     */
    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    /**
     * Checks whether the parameter is a valid compressed IPv6 address
     *
     * @param input the address string to check for validity
     * @return true if the input parameter is a valid compressed IPv6 address
     */
    public static boolean isIPv6HexCompressedAddress(final String input) {
        int colonCount = 0;
        for(int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == COLON_CHAR) {
                colonCount++;
            }
        }
        return  colonCount <= MAX_COLON_COUNT && IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    // Place status variables here

    static {

	final String rel = System.getProperty("OtpCompatRel", "0");

	try {

	    switch (Integer.parseInt(rel)) {
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
	    case 8:
	    case 9:
	    case 0:
	    default:
		break;
	    }
	} catch (final NumberFormatException e) {
	    /* Ignore ... */
	}

    }
    
    // Place query functions here

}
