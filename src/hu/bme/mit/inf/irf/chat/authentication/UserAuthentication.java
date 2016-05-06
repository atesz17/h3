package hu.bme.mit.inf.irf.chat.authentication;

import java.util.Scanner;

public class UserAuthentication {

    public static String getAuthenticatedUsername(final RegistrationManager regManager) {
        Scanner scanner = new Scanner(System.in);

        String username = "";
        String password = "";

        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Username:");
            username = scanner.nextLine();

            System.out.println("Password:");
            password = scanner.nextLine();

            if (regManager.isAuthenticated(username, password)) {
                break;
            } else {
                System.out.println(
                        "Authentication failed. Try again with correct username and password.");
            }
        }

        return username;
    }

}
