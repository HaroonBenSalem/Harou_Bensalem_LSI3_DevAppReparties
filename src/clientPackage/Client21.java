package clientPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client21 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez une opération (exemple : 55 * 25) : ");
        String operation = scanner.nextLine().trim();

        Pattern pattern = Pattern.compile(
                "(-?\\d+(?:\\.\\d+)?)\\s*([+*/-])\\s*(-?\\d+(?:\\.\\d+)?)");

        if (!pattern.matcher(operation).matches()) {
            System.out.println("Erreur : syntaxe invalide !");
            scanner.close();
            return;
        }

        try (Socket socket = new Socket("localhost", 1234);
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            out.println(operation);

            String resultat = in.readLine();
            System.out.println("Réponse du serveur : " + resultat);

        } catch (Exception e) {
            System.out.println("Erreur client : " + e.getMessage());
        }

        scanner.close();
    }
}