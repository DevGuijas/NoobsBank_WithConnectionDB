package entities;

import java.sql.*;
import application.pagina_principal;

public class connectiondb {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    static public void connection_server(Connection conn, Statement stmt, ResultSet rs) {
        try{
            String url = "";
            String user = "";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users");

            } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getCPF(String cpf_user){
        String cpf = null;
        try{
            String url = "";
            String user = "";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            String query = "SELECT cpf FROM users WHERE cpf = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cpf_user); // Substitui o parâmetro

            rs = pstmt.executeQuery();
            if(rs.next()){
                cpf = rs.getString("cpf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Certifique-se de fechar os recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cpf;
    }

    public String getAuth_code(String auth_code){
        String auth = null;

        try {
            String url = "";
            String user = "";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            String query = "SELECT auth_code FROM user WHERE auth_code = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, auth_code);

            rs = pstmt.executeQuery();
            if(rs.next()){
                auth = rs.getString("auth_code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return auth;
    }

    public String getFull_name(String full_name){
        String name = null;

        try {
            String url = "";
            String user = "";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            String query = "SELECT auth_code FROM user WHERE full_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, full_name);

            rs = pstmt.executeQuery();
            if(rs.next()){
                name = rs.getString("full_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return name;
    }
}
