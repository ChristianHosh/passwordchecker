import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainApplication {

    private static final double LOG_2 = Math.log(2);
    public static final int CHAR_SET_SIZE = 95; // total number of printable ASCII chars

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("\nCheck your password! or press 'Enter' to exit");
            System.out.println("Password should match the following rules: ");
            System.out.println("\t- must be at least 8 characters long");
            System.out.println("\t- contain at least one uppercase character");
            System.out.println("\t- contain at least one lowercase character");
            System.out.println("\t- contain at least one number");
            System.out.println("\t- contain at least one special character");
            System.out.print("Enter a password: ");

            String passwordInput = scanner.nextLine().trim();

            if (passwordInput.isBlank())
                break;

            printPasswordStrength(passwordInput);
        }
    }

    private static void printPasswordStrength(String password) {
        double entropy = calculateEntropy(password);
        double variance = calculateVariance(password);

        if (entropy > 80 && variance <= 0.25) {
            System.out.println("Your password is: STRONG");
        } else if (entropy > 60 && variance <= 0.5) {
            printHints(password);
            System.out.println("Your password is: MEDIUM");
        } else {
            printHints(password);
            System.out.println("Your password is: WEAK");
        }
    }

    private static void printHints(String password) {
        int upperCount = calculateUppercaseChars(password);
        int lowerCount = calculateLowercaseChars(password);
        int digitCount = calculateDigitChars(password);
        int specialCount = calculateSpecialChars(password);

        StringBuilder hintBuilder = new StringBuilder();

        if (upperCount < 1)
            hintBuilder.append("add more uppercase characters\n");
        if (lowerCount < 1)
            hintBuilder.append("add more lowercase characters\n");
        if (digitCount < 1)
            hintBuilder.append("add more digits\n");
        if (specialCount < 1)
            hintBuilder.append("add more special characters\n");
        if (password.length() < 8)
            hintBuilder.append("make your password longer\n");

        System.err.println(hintBuilder);
    }

    private static double calculateEntropy(String password) {
        return Math.log(Math.pow(CHAR_SET_SIZE, password.length())) / LOG_2;
    }

    private static double calculateVariance(String password) {
        Collection<Integer> counts = getCharCount(password).values();
        double avgFrequency = (double) counts.stream()
                .mapToInt(Integer::intValue)
                .sum() / password.length();

        return counts.stream()
                .mapToDouble(count -> Math.pow(count - avgFrequency, 2))
                .sum() / password.length();
    }

    private static Map<Character, Integer> getCharCount(String password) {
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : password.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        return charCount;
    }

    private static int calculateUppercaseChars(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                count++;
        }
        return count;
    }

    private static int calculateLowercaseChars(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c))
                count++;
        }
        return count;
    }

    private static int calculateDigitChars(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c))
                count++;
        }
        return count;
    }

    private static int calculateSpecialChars(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c))
                count++;
        }
        return count;
    }
}
