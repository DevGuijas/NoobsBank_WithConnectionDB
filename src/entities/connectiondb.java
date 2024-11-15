package entities;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class connectiondb {
    private static connectiondb instance;
    private Connection conn;
    private ResultSet rs;

    private connectiondb() {
        try {
            this.conn = getConnection();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.printf("Erro de conexão. Verifique a sua internet!");
        }
    }

    public static synchronized connectiondb getInstance() {
        if (instance == null) {
            instance = new connectiondb();
        }
        return instance;
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://autorack.proxy.rlwy.net:11221/railway";
        String user = "postgres";
        String password = "tkRKzWKRYgkURICmnDwIakxUSQSlCTKu";

        return DriverManager.getConnection(url, user, password);
    }

    public String getCPF(String cpf_user){
        String cpf = null;
        try {
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
            closeResultSet();
        }
        return cpf;
    }

    public String getAuth_code(String auth_code) {
        String auth = null;
        try {
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
            closeResultSet();
        }
        return auth;
    }

    public String getFull_name(String cpf_user) {
        String name = null;
        try {
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
            closeResultSet();
        }
        return name;
    }

    public String getNumber_telephone(String telephone_number) {
        String number = null;
        try {
            String query = "SELECT telephone_number FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                number = rs.getString("telephone_number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
        }
        return number;
    }

    public String getSaldo(String saldo_usuário) {
        String saldo = null;
        try {
            String query = "SELECT balance FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if(rs.next()){
                saldo = rs.getString("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
        }
        return saldo;
    }

    /*public String operation_saque(String saque_value) {
        String finally_operation = null;
        String balance_value = null;
        try {
            String query = "SELECT balance FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();
            if(rs.next()){
                balance_value = rs.getString("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResultSet();
        }

        try {
            String query = "";
        }
        return finally_operation;
    }*/

    public void invalide_operation() {
        System.out.println("Infelizmente essa opção ainda não está disponível. Tente novamente mais tarde");
    }

    private void closeResultSet() {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}