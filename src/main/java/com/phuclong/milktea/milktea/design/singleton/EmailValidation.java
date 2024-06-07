package com.phuclong.milktea.milktea.design.singleton;

public class EmailValidation {
    private static EmailValidation validation;
    private EmailValidation() {
    }
    public static EmailValidation getInstance() {
        if (validation == null) {
            validation = new EmailValidation();
        }
        return validation;
    }

    public synchronized boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
