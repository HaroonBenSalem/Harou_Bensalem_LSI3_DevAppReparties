package serveurPackage;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serveur21 {

    public static void main(String[] args) {
        int port = 1234;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur en attente du client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(
                         socket.getOutputStream(), true)) {

                System.out.println("Client connecté !");

                String operation = in.readLine();
                System.out.println("Opération reçue : " + operation);

                Pattern pattern = Pattern.compile(
                        "(-?\\d+(?:\\.\\d+)?)\\s*([+*/-])\\s*(-?\\d+(?:\\.\\d+)?)");

                Matcher matcher = pattern.matcher(operation);

                if (!matcher.matches()) {
                    out.println("Erreur : opération invalide");
                } else {
                    double a = Double.parseDouble(matcher.group(1));
                    String operateur = matcher.group(2);
                    double b = Double.parseDouble(matcher.group(3));

                    double resultat;

                    switch (operateur) {
                        case "+":
                            resultat = a + b;
                            break;
                        case "-":
                            resultat = a - b;
                            break;
                        case "*":
                            resultat = a * b;
                            break;
                        case "/":
                            if (b == 0) {
                                out.println("Erreur : division par zéro");
                                return;
                            }
                            resultat = a / b;
                            break;
                        default:
                            out.println("Erreur : opérateur inconnu");
                            return;
                    }

                    out.println("Résultat : " + resultat);
                    System.out.println("Résultat envoyé : " + resultat);
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
