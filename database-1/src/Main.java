import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/login";
    static final String USER = "root";
    static final String PASS = "";

//    Menyiapkan objek yang diperlukan untuk mengelola database
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    public static void main(String[] args) {
        String user, pass, id;
        int pilih;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("1. Create account\n2. Login\n3. Show data\n4. Delete data\n5. Update data\n6. Back");
            pilih = input.nextInt();
//            Create
            if(pilih == 1) {
                try {
                    input.nextLine();

                    System.out.print("Enter new username : ");
                    user = input.nextLine();
                    System.out.print("Enter new password : ");
                    pass = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login";
                    rs = stmt.executeQuery(sql);
//                    Cek apakah username sudah ada
                    if(rs.next()) {
                        if(user.equals(rs.getString("username"))) {
                            System.out.println("Username already exists");
                        } else {
                            int rowsInserted = statement1.executeUpdate("INSERT INTO login VALUES (NULL, '"+user+"', '"+pass+"');");
                            if (rowsInserted > 0) {
                                System.out.println("A new user was inserted successfully!");
                            }
                        }
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(pilih == 2 ) {
                try {
                    input.nextLine();

                    System.out.print("Enter username : ");
                    user = input.nextLine();
                    System.out.print("Enter password : ");
                    pass = input.nextLine();

//                    Register driver yang akan dipakai
                    Class.forName(JDBC_DRIVER);
//                    Buat koneksi ke database
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                    Buat objek statement
                    stmt = conn.createStatement();
//                    Buat query ke database
                    String sql = "SELECT * FROM login WHERE username='"+user+"' AND password='"+pass+"'";
//                    Eksekusi query dan simpan hasilnya di obj ResultSet
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        if(user.equals(rs.getString("username")) && pass.equals(rs.getString("password"))) {
                            System.out.println("Login successful!");
                        }
                    } else {
                        System.out.println("Wrong username or password!");
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Read
            else if(pilih == 3) {
                try {
                    String sql = "SELECT * FROM login";
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    Statement statement1 = conn.createStatement();
                    ResultSet result = statement1.executeQuery(sql);

                    while (result.next()){
                        String id_num = result.getString(1);
                        String username = result.getString(2);
                        String password = result.getString(3);

                        System.out.println("Id : "+String.format(id_num));
                        System.out.println("Username : "+String.format(username));
                        System.out.println("Password : "+String.format(password)+"\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Delete
            else if(pilih == 4) {
                try {
                    input.nextLine();

                    System.out.print("Enter username : ");
                    user = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login WHERE username='"+user+"'";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        if(!user.equals(rs.getString("username"))) {
                            System.out.println("Username not found");
                        } else {
                            int rowsDeleted = statement1.executeUpdate("DELETE FROM login WHERE username='"+user+"'");
                            if (rowsDeleted > 0) {
                                System.out.println("A user was deleted successfully!");
                            }
                        }
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Update
            else if (pilih == 5) {
                try {
                    input.nextLine();

                    System.out.print("Enter existing id : ");
                    id = input.nextLine();

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    Statement statement1 = conn.createStatement();

                    String sql = "SELECT * FROM login";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        if(id.equals(rs.getString("id"))) {
                            System.out.println("id username not found");
                        } else {
                            System.out.print("Enter new username : ");
                            user = input.nextLine();
                            System.out.print("Enter new password : ");
                            pass = input.nextLine();
                            int rowsUpdated = statement1.executeUpdate("UPDATE login SET username='"+user+"', password='"+pass+"' WHERE id='"+id+"'");
                            if (rowsUpdated > 0) {
                                System.out.println("An existing user was updated successfully!");
                            }
                        }
                    }
                    stmt.close();
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } while(pilih == 1 || pilih == 2 || pilih == 3 || pilih == 4 || pilih == 5);
        System.out.println("Program berakhir!");
    }
}