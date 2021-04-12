package bzh.strawberry.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * This file Encrypt is part of a project StrawAuth.
 * It was created on 05/04/2021 19:59 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawAuth author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class Encrypt {

    public static String getSHA512(String passwordToHash) {
        String generatedPassword = null;
        String saltStart = "u)_}5n6DZ;69cDcN";
        String saltEnd = "x4,T2Mn_x{(yA57S";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(saltStart.getBytes(StandardCharsets.UTF_8));
            passwordToHash = saltStart + passwordToHash + saltEnd;
            byte[] bytes = messageDigest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}