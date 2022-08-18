package bigint;

public class BigInt {

    public static void main(String[] args) {
        BigInt bi = new BigInt("12345432323");
        System.out.println("value of BigInt is: " + bi.getValue());
        System.out.println("Addition of 12345432323 and 1233224 equals: " + bi.add("1233224"));
        System.out.println("Difference of 12345432323 and 1233224 equals: " + bi.difference("1233224"));
        System.out.println("Multiplacation of 12345432323 and 1233224 equals: " + bi.multiply("1233224"));
        System.out.println("Division of 12345432323 by 1233224 equals: " + bi.division("1233224"));

    }

    private String value;
    private String changable;

    BigInt(String v) {
        value = v;
        changable = value;
    }

    String add(String integer2) {
        if (changable.length() > integer2.length()) {
            String t = changable;
            changable = integer2;
            integer2 = t;
        }
        String result = "";
        int integer1_Length = changable.length(), integer2_Length = integer2.length();
        changable = new StringBuilder(changable).reverse().toString();
        integer2 = new StringBuilder(integer2).reverse().toString();
        int carry = 0;
        for (int i = 0; i < integer1_Length; i++) {
            int sum = ((int) (changable.charAt(i) - '0') + (int) (integer2.charAt(i) - '0') + carry);
            result += (char) (sum % 10 + '0');
            carry = sum / 10;
        }
        for (int i = integer1_Length; i < integer2_Length; i++) {
            int sum = ((int) (integer2.charAt(i) - '0') + carry);
            result += (char) (sum % 10 + '0');
            carry = sum / 10;
        }
        if (carry > 0) {
            result += (char) (carry + '0');
        }
        result = new StringBuilder(result).reverse().toString();
        changable = value;
        return result;
    }

    boolean isSmaller(String integer1, String integer2) {
        int integer1_Length = integer1.length(), integer2_Length = integer2.length();
        if (integer1_Length < integer2_Length) {
            return true;
        }
        if (integer2_Length < integer1_Length) {
            return false;
        }
        for (int i = 0; i < integer1_Length; i++) {
            if (integer1.charAt(i) < integer2.charAt(i)) {
                return true;
            } else if (integer1.charAt(i) > integer2.charAt(i)) {
                return false;
            }
        }
        return false;
    }

    String difference(String integer2) {
        if (isSmaller(changable, integer2)) {
            String t = changable;
            changable = integer2;
            integer2 = t;
        }
        String result = "";
        int integer1_Length = changable.length(), integer2_Length = integer2.length();
        changable = new StringBuilder(changable).reverse().toString();
        integer2 = new StringBuilder(integer2).reverse().toString();
        int carry = 0;
        for (int i = 0; i < integer2_Length; i++) {
            int sub = ((int) (changable.charAt(i) - '0') - (int) (integer2.charAt(i) - '0') - carry);
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }

            result += (char) (sub + '0');
        }
        for (int i = integer2_Length; i < integer1_Length; i++) {
            int sub = ((int) (changable.charAt(i) - '0') - carry);
            if (sub < 0) {
                sub = sub + 10;
                carry = 1;
            } else {
                carry = 0;
            }
            result += (char) (sub + '0');
        }
        result = new StringBuilder(result).reverse().toString();
        if (result.charAt(0) == '0' && result.length() > 1) {
            int i = 0;
            try {
                while (result.charAt(i) == '0') {
                    result = result.replaceFirst("0", "");
                }
            } catch (Exception e) {
                return "0";
            }
        }
        changable = value;
        return result;
    }

    String multiply(String integer2) {
        String temp1 = changable;
        String temp2 = integer2;
        if (changable.charAt(0) == '-' && integer2.charAt(0) != '-') {
            changable = changable.substring(1);
        } else if (changable.charAt(0) != '-' && integer2.charAt(0) == '-') {
            integer2 = integer2.substring(1);
        } else if (changable.charAt(0) == '-' && integer2.charAt(0) == '-') {
            changable = changable.substring(1);
            integer2 = integer2.substring(1);
        }
        changable = new StringBuffer(changable).reverse().toString();
        integer2 = new StringBuffer(integer2).reverse().toString();
        int[] m = new int[changable.length() + integer2.length()];
        for (int i = 0; i < changable.length(); i++) {
            for (int j = 0; j < integer2.length(); j++) {
                m[i + j] = m[i + j] + (changable.charAt(i) - '0') * (integer2.charAt(j) - '0');
            }
        }
        String result = new String();
        for (int i = 0; i < m.length; i++) {
            int digit = m[i] % 10;
            int carry = m[i] / 10;
            if (i + 1 < m.length) {
                m[i + 1] = m[i + 1] + carry;
            }
            result = digit + result;
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        if (temp1.charAt(0) == '-' && temp2.charAt(0) != '-') {
            result = new StringBuffer(result).insert(0, '-').toString();
        } else if (temp1.charAt(0) != '-' && temp2.charAt(0) == '-') {
            result = new StringBuffer(result).insert(0, '-').toString();
        } else if (temp1.charAt(0) == '-' && temp2.charAt(0) == '-') {
            result = result;
        }
        changable = value;
        return result;
    }

    String division(String integer2) {
        int result = 0;
        String dividend = changable;
        while (!isSmaller(dividend, integer2)) {
            changable = dividend;
            dividend = difference(integer2);
            result++;
        }
        return Integer.toString(result);
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
