package it.mgt.util.spring.validation;

public class PasswordValidator {

    private int minLength;
    private int maxLength;
    private int minLetters;
    private int minUppercase;
    private int minLowercase;
    private int minDigit;
    private int minSpecialChars;
    private boolean allowUnknownChars;
    private String specialCharsPool;

    public PasswordValidator(int minLength, int maxLength, int minLetters, int minUppercase, int minLowercase, int minDigit, int minSpecialChars, boolean allowUnknownChars, String specialChars) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.minLetters = minLetters;
        this.minUppercase = minUppercase;
        this.minLowercase = minLowercase;
        this.minDigit = minDigit;
        this.minSpecialChars = minSpecialChars;
        this.allowUnknownChars = allowUnknownChars;
        this.specialCharsPool = specialChars;
    }

    public boolean validate(String password) {
        if (password == null || password.length() == 0)
            return false;

        int len0 = password.length();

        password = password.trim();
        int len = password.length();
        if (len0 != len)
            return false;

        if(len < minLength || len > maxLength)
            return false;

        int letters = 0;
        int uppercase = 0;
        int lowercase = 0;
        int digit = 0;
        int specialChars = 0;
        char[] chars = password.toCharArray();
        for(char c : chars) {
            if (Character.isUpperCase(c)) {
                letters++;
                uppercase++;
            }
            else if (Character.isLowerCase(c)) {
                letters++;
                lowercase++;
            }
            else if (Character.isDigit(c)) {
                digit++;
            }
            else {
                if (allowUnknownChars) {
                    specialChars++;
                }
                else {
                    if (specialCharsPool.indexOf(c) >= 0)
                        specialChars++;
                    else
                        return false;
                }
            }
        }

        return  letters >= minLetters
                && uppercase >= minUppercase
                && lowercase >= minLowercase
                && digit >= minDigit
                && specialChars >= minSpecialChars;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLetters() {
        return minLetters;
    }

    public void setMinLetters(int minLetters) {
        this.minLetters = minLetters;
    }

    public int getMinUppercase() {
        return minUppercase;
    }

    public void setMinUppercase(int minUppercase) {
        this.minUppercase = minUppercase;
    }

    public int getMinLowercase() {
        return minLowercase;
    }

    public void setMinLowercase(int minLowercase) {
        this.minLowercase = minLowercase;
    }

    public int getMinDigit() {
        return minDigit;
    }

    public void setMinDigit(int minDigit) {
        this.minDigit = minDigit;
    }

    public int getMinSpecialChars() {
        return minSpecialChars;
    }

    public void setMinSpecialChars(int minSpecialChars) {
        this.minSpecialChars = minSpecialChars;
    }

    public boolean isAllowUnknownChars() {
        return allowUnknownChars;
    }

    public void setAllowUnknownChars(boolean allowUnknownChars) {
        this.allowUnknownChars = allowUnknownChars;
    }

    public String getSpecialChars() {
        return specialCharsPool;
    }

    public void setSpecialChars(String specialCharsPool) {
        this.specialCharsPool = specialCharsPool;
    }

        
    
}
