import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/banque_db";
        String user = "postgres"; // Default username
        String password = "rayen"; // Password you set during installation

        try {
            // Explicitly load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC driver loaded successfully!");

            // Connect to the database
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                System.out.println("Connected to the database!");

                // Test CRUD operations
                BanqueDAO banqueDAO = new BanqueDAO(connection);
                CompteDAO compteDAO = new CompteDAO(connection);

                // Create a new banque
                Banque banque = new Banque(1, "Banque Centrale");
                banqueDAO.create(banque);
                System.out.println("Banque created successfully!");

                // Verify Banque table
                System.out.println("Banque table after creation:");
                banqueDAO.list().forEach(System.out::println);

                // Create a new compte
                Compte compte = new Compte(1, 1000.0, 1);
                compteDAO.create(compte);
                System.out.println("Compte created successfully!");

                // Verify Compte table
                System.out.println("Compte table after creation:");
                compteDAO.list().forEach(System.out::println);

                // Read the compte
                Compte compteLu = compteDAO.read(1);
                System.out.println("Compte lu: " + compteLu);

                // Update the compte
                compte.setSolde(1500.0);
                compteDAO.update(compte);
                System.out.println("Compte mis à jour: " + compteDAO.read(1));

                // Verify Compte table after update
                System.out.println("Compte table after update:");
                compteDAO.list().forEach(System.out::println);

                // Delete the compte
                compteDAO.delete(1);
                System.out.println("Compte supprimé.");

                // Verify Compte table after deletion
                System.out.println("Compte table after deletion:");
                compteDAO.list().forEach(System.out::println);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL error occurred!");
            e.printStackTrace();
        }
    }
}