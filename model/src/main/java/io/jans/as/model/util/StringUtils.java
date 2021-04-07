/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2020, Janssen Project
 */

package io.jans.as.model.util;

import io.jans.as.model.common.HasParamName;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author Javier Rojas Blum
 * @version July 18, 2017
 */
public class StringUtils {

    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String EASY_TO_READ_CHARACTERS = "BCDFGHJKLMNPQRSTVWXZ";

    public static String nullToEmpty(String str) {
        if (str == null) {
            return EMPTY_STRING;
        } else {
            return str;
        }
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null && str2 != null) {//note: str2!=null is always NOT null, see previous 'if' statement*/
            return false;
        } else if (str1 != null && str2 == null) {  //note: str1!=null is ALWAYS true
            return false;
        } else {
            return str1 != null && str1.equals(str2);
        }
    }

    public static boolean equalsIgnoringSpaces(String a, String b) {
        if (a == null || b == null)
            return false;
        return a.replaceAll("\\s+","").equalsIgnoreCase(b.replaceAll("\\s+",""));
    }

    /**
     * Method to join array elements of type string
     *
     * @param inputArray Array which contains strings
     * @param glueString String between each array element
     * @return String containing all array elements separated by glue string.
     */
    public static String implode(String[] inputArray, String glueString) {
        String output = EMPTY_STRING;

        if (inputArray != null && inputArray.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(inputArray[0]);

            for (int i = 1; i < inputArray.length; i++) {
                sb.append(glueString);
                sb.append(inputArray[i]);
            }

            output = sb.toString();
        }

        return output;
    }

    /**
     * Method to join a list of elements of type string
     *
     * @param collection List which contains strings
     * @param glueString String between each array element
     * @return String containing all array elements separated by glue string.
     */
    public static String implode(Collection collection, String glueString) {
        String output = EMPTY_STRING;

        if (collection != null && !collection.isEmpty()) {
            StringJoiner sb = new StringJoiner(glueString);

            for (Object obj : collection) {
                sb.add(obj.toString());
            }

            output = sb.toString();
        }

        return output;
    }

    public static String implodeEnum(List<? extends HasParamName> inputList, String glueString) {
        String output = EMPTY_STRING;

        if (inputList != null && !inputList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(inputList.get(0));

            for (int i = 1; i < inputList.size(); i++) {
                sb.append(glueString);
                sb.append(inputList.get(i).getParamName());
            }

            output = sb.toString();
        }

        return output;
    }

    public static List<String> spaceSeparatedToList(String spaceSeparatedString) {
        List<String> list = new ArrayList<String>();

        if (isNotBlank(spaceSeparatedString)) {
            list = Arrays.asList(spaceSeparatedString.split(StringUtils.SPACE));
        }

        return list;
    }

    public static JSONArray toJSONArray(List inputList) {
        JSONArray jsonArray = new JSONArray();

        if (inputList != null && !inputList.isEmpty()) {
            jsonArray = new JSONArray(inputList);
        }

        return jsonArray;
    }

    public static List<String> toList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }

    public static Date parseSilently(String p_string) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            return parser.parse(p_string);
        } catch (Exception e) {
            return null;
        }
    }

    public static void addQueryStringParam(StringBuilder p_queryStringBuilder, String key, Object value) throws UnsupportedEncodingException {
        if (p_queryStringBuilder != null && isNotBlank(key) && value != null) {
            if (p_queryStringBuilder.length() > 0) {
                p_queryStringBuilder.append("&");
            }
            p_queryStringBuilder.append(key).append("=")
                    .append(URLEncoder.encode(value.toString(), Util.UTF8_STRING_ENCODING));
        }
    }

    public static void addQueryStringParam(StringBuilder p_queryStringBuilder, String key, Collection value) throws UnsupportedEncodingException {
        if (p_queryStringBuilder != null && isNotBlank(key) && value != null && !value.isEmpty()) {
            if (p_queryStringBuilder.length() > 0) {
                p_queryStringBuilder.append("&");
            }
            p_queryStringBuilder.append(key).append("=")
                    .append(URLEncoder.encode(value.toString(), Util.UTF8_STRING_ENCODING));
        }
    }

    /**
     * Generates a code using a base of 20 characters easy to read for users, using parametrized
     * length separated by dashes with intervals of 4 characters.
     */
    public static String generateRandomReadableCode(byte length) {
        StringBuilder sb = new StringBuilder();
        SecureRandom sc = new SecureRandom();
        for (int i = 0; i < length; i++) {
            if (i % 4 == 0 && i > 0) {
                sb.append('-');
            }
            char item = EASY_TO_READ_CHARACTERS.charAt(sc.nextInt(EASY_TO_READ_CHARACTERS.length()));
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * Generates a random code using a byte array as its seed.
     * @param seedLength Length of the byte array
     */
    public static String generateRandomCode(byte seedLength) {
        byte[] seed = new byte[seedLength];
        new SecureRandom().nextBytes(seed);
        return Util.byteArrayToHexString(seed);
    }

}