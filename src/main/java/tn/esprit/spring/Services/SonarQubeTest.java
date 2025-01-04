package tn.esprit.spring.Services;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class SonarQubeTest {

    // Code Smell: Complex Method (Too many lines in a single method)
    public void complexMethod() {
        String result = "";
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                result += i + ":" + j + " ";
            }
        }
        System.out.println(result);
    }
    public void secureHashing() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Secure hashing algorithm
            md.update("test".getBytes());
            byte[] hash = md.digest();
            System.out.println(new String(hash));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // XSS vulnerability (no sanitization of user input)
    public void xssRisk(String userInput) {
        String htmlContent = "<div>" + userInput + "</div>";  // Dangerous if userInput contains script
        System.out.println(htmlContent);
    }
    // Weak randomness (using Math.random())
    public void weakRandomness() {
        double randomValue = Math.random();  // Should use SecureRandom instead
        System.out.println("Random Value: " + randomValue);
    }
    // Insecure network communication (HTTP instead of HTTPS)
    public void insecureNetworkCommunication() {
        String url = "http://example.com";  // HTTP is insecure, SonarQube should flag this
        System.out.println("Sending data to: " + url);
    }
    // Sensitive data logging
    public void sensitiveDataLogging(String password) {
        System.out.println("Password: " + password);  // Logging sensitive data
    }
    // Empty catch block (ignoring exceptions)
    private void emptyCatchBlock1() {
        try {
            int result = 10 / 0;  // Will throw ArithmeticException
        } catch (ArithmeticException e) {
            // Exception is silently ignored without logging, making debugging difficult
        }
    }// SQL Injection Vulnerability
    public void sqlInjectionRisk(String userInput) {
        String query = "SELECT * FROM users WHERE username = '" + userInput + "'"; // No sanitization
        // This will make the query vulnerable to SQL injection attacks
        System.out.println(query);
    }
    // Insecure Hashing using SHA-1 (SonarQube should detect this)
    public void insecureHashingNO() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1"); // Insecure hash function
            md.update("test".getBytes());
            byte[] hash = md.digest();
            System.out.println(new String(hash));  // Vulnerable to attacks
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void secureHashinginsecure() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1"); // Secure hashing algorithm
            md.update("test".getBytes());
            byte[] hash = md.digest();
            System.out.println(new String(hash));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Bug: Null Pointer Dereference
    public void nullPointerBug() {
        String str = null;
        System.out.println(str.length());  // Will throw NullPointerException
    }

    // Vulnerability: Using SHA1 which is deprecated and insecure
    public void insecureHashing() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1"); // Vulnerable to attacks
            md.update("test".getBytes());
            byte[] hash = md.digest();
            System.out.println(new String(hash));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Code Smell: Duplicated Code (The same logic appears twice)
    public void duplicatedCode1() {
        int a = 0;
        int b = 0;
        int c = 0;

        // Code Block 1
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }

        // Code Block 2 (Duplicated)
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
    }

    // Code Smell: Duplicated Code (The same logic appears twice)
    public void duplicatedCode5() {
        int a = 0;
        int b = 0;
        int c = 0;

        // Code Block 1
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }

        // Code Block 2 (Duplicated)
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
    }

    // Code Smell: Duplicated Code (The same logic appears twice)
    public void duplicatedCode() {
        int a = 0;
        int b = 0;
        int c = 0;

        // Code Block 1
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }

        // Code Block 2 (Duplicated)
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
        for (int i = 0; i < 10; i++) {
            a += i;
            b += i;
            c += i;
        }
    }


    // Code Smell: Long Class (Class is too long and should be split into smaller classes)
    public void longClassMethod() {
        int total = 0;
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < 100; i++) {
            total += numbers.get(i);
        }
        System.out.println(total);
    }

    // Bug: Array Index Out of Bounds Exception
    public void arrayIndexOutOfBounds() {
        int[] arr = new int[5];
        System.out.println(arr[10]);  // Will throw ArrayIndexOutOfBoundsException
    }

    // Unused Variable (Code Smell)
    public void unusedVariable() {
        int unused = 100;  // Variable is never used
    }

    // Deprecated API Usage (Code Smell)
    public void deprecatedApi() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");

        for (String item : list) {
            System.out.println(item);
        }
    }

    // Method with empty catch block (Hides exceptions, making debugging difficult)
    public void emptyCatchBlock() {
        try {
            // Some logic
            int result = 10 / 0;  // This will throw an ArithmeticException
        } catch (ArithmeticException e) {
            // Catch block is empty, exception is silently ignored
        }
    }

    // Method with hardcoded magic numbers (Code Smell)
    public void magicNumber() {
        int total = 0;
        for (int i = 0; i < 100; i++) {  // 100 is a magic number
            total += i;
        }
        System.out.println(total);
    }

    // Method with unreachable code (Code Smell)
    public void unreachableCode() {
        System.out.println("This code will never be executed");  // Unreachable code
    }

    // Code Smell: Method with too many parameters
    public void tooManyParameters(String name, int age, String address, String phone, String email, boolean isActive) {
        System.out.println(name + " " + age + " " + address + " " + phone + " " + email + " " + isActive);
    }

    // Main method to run the class
    public static void main(String[] args) {
        SonarQubeTest example = new SonarQubeTest();
        example.complexMethod();
        example.nullPointerBug();
        example.insecureHashing();
        example.duplicatedCode();
        example.longClassMethod();
        example.arrayIndexOutOfBounds();
        example.unusedVariable();
        example.deprecatedApi();
        example.emptyCatchBlock();
        example.magicNumber();
        example.unreachableCode();
        example.tooManyParameters("John", 25, "Some Address", "123-456-7890", "john@example.com", true);
    }
}
