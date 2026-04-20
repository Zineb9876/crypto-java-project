import java.util.Scanner;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AppCrypto {
    private static final String SECRET_KEY = "1234567890123456";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Chiffrer un texte");
            System.out.println("2. Dechiffrer un texte");
            System.out.println("3. Calculer le SHA-256 d'un texte");
            System.out.println("4. Comparer deux hashes");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choix = sc.nextInt();
            sc.nextLine();
            switch (choix) {
                case 1:
                    System.out.print("Entrer le texte : ");
                    String texte = sc.nextLine();
                    String chiffre = chiffrer(texte);
                    System.out.println("Texte chiffre : " + chiffre);
                    break;

                case 2:
                    System.out.print("Entrer le texte chiffre : ");
                    String texteChiffre = sc.nextLine();
                    String dechiffre = dechiffrer(texteChiffre);
                    System.out.println("Texte dechiffre : " + dechiffre);
                    break;

                case 3:
                    System.out.print("Entrer le texte : ");
                    String t = sc.nextLine();
                    String hash = sha256(t);
                    System.out.println("SHA-256 : " + hash);
                    break;

                case 4:
                    System.out.print("Entrer le premier hash : ");
                    String h1 = sc.nextLine();
                    System.out.print("Entrer le deuxieme hash : ");
                    String h2 = sc.nextLine();

                    if (h1.equals(h2)) {
                        System.out.println("Les deux hashes sont IDENTIQUES");
                    } else {
                        System.out.println("Les hashes sont DIFFERENTS (modification détectée)");
                    }
                    break;

                case 0:
                    System.out.println("Fin du programme.");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }

        } while (choix != 0);

        sc.close();
    }

    // ===== CHIFFREMENT AES =====
    public static String chiffrer(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            return "Erreur de chiffrement";
        }
    }

    // ===== DECHIFFREMENT AES =====
    public static String dechiffrer(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(data);
            return new String(cipher.doFinal(decoded));

        } catch (Exception e) {
            return "Erreur de dechiffrement";
        }
    }

    // ===== SHA-256 =====
    public static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String s = Integer.toHexString(0xff & b);
                if (s.length() == 1) hex.append('0');
                hex.append(s);
            }
            return hex.toString();

        } catch (Exception e) {
            return "Erreur de hash";
        }
    }
}