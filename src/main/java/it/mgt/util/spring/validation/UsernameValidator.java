package it.mgt.util.spring.validation;

public class UsernameValidator {

    private int minLength;
    private int maxLength;
    private String specialCharsWhiteList;
    private String specialCharsBlackList;

    public UsernameValidator(int minLength, int maxLength, String specialCharsWhiteList, String specialCharsBlackList) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.specialCharsWhiteList = specialCharsWhiteList;
        this.specialCharsBlackList = specialCharsBlackList;
    }

    public boolean validate(String username) {
        if (username == null || username.length() == 0)
            return false;

        int len0 = username.length();

        username = username.trim();
        int len = username.length();
        if (len0 != len)
            return false;

        if(len < minLength || len > maxLength)
            return false;

        char[] chars = username.toCharArray();
        for(char c : chars) {
            if (Character.isUpperCase(c)) {
                continue;
            }
            else if (Character.isLowerCase(c)) {
                continue;
            }
            else if (Character.isDigit(c)) {
                continue;
            }
            else if (specialCharsWhiteList != null) {
                if (specialCharsWhiteList.indexOf(c) < 0)
                    return false;
            }
            else if (specialCharsBlackList != null) {
                if (specialCharsWhiteList.indexOf(c) >= 0)
                    return false;
            }
        }

        return true;
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

    public String getSpecialCharsWhiteList() {
        return specialCharsWhiteList;
    }

    public void setSpecialCharsWhiteList(String specialCharsWhiteList) {
        this.specialCharsWhiteList = specialCharsWhiteList;
    }

    public String getSpecialCharsBlackList() {
        return specialCharsBlackList;
    }

    public void setSpecialCharsBlackList(String specialCharsBlackList) {
        this.specialCharsBlackList = specialCharsBlackList;
    }

    

}
