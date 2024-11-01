package entities;

import java.sql.*;

public class connectiondb {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public static Connection getConnection() throws SQLException {
        String url = "";
        String user = "";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public String getCPF(String cpf_user){
        String cpf = null;
        try {
            conn = getConnection();
            String query = "SELECT cpf FROM users WHERE cpf = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cpf_user);

            rs = pstmt.executeQuery();
            if(rs.next()){
                cpf = rs.getString("cpf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return cpf;
    }

    public String getAuth_code(String auth_code) {
        String auth = null;
        try {
            conn = getConnection();
            String query = "SELECT auth_code FROM auth_code WHERE auth_code = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, auth_code);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                auth = rs.getString("auth_code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return auth;
    }

    public String getFull_name(String cpf_user) {
        String name = null;
        try {
            conn = getConnection();
            String query = "SELECT f.full_name FROM full_name f " +
                    "JOIN users u ON f.user_id = u.id " +
                    "WHERE u.cpf = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cpf_user);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("full_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return name;
    }

    public String getNumber_telephone(String telephone_number) {
        String number = null;
        try {
            conn = getConnection();
            String query = "SELECT telephone_number FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                number = rs.getString("telephone_number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return number;
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}