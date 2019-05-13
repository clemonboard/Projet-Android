package com.example.projetoutil;

import java.util.regex.Pattern;

public class Validating {
    private final static String PASSWORD_PATTERN = "^[a-zA-Z0-9\\!\\$\\#\\%]{6,20}$";
    private final static String POSTAL_PATTERN = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean checkPassword(String password)
    {
        return  PASSWORD_PATTERN.matches(password);

    }

    public boolean checkPostal(String Postal_Code)
    {

        return  POSTAL_PATTERN.matches(Postal_Code);
    }



}
