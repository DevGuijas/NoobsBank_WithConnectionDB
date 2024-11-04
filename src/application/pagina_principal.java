package application;

import entities.connectiondb;
import java.util.Scanner;

public class pagina_principal {

    static connectiondb db = connectiondb.getInstance();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("\n" +
                    "Olá, bem vindo(a) ao NoobsBank!" +
                    "\nO banco da gente! ;)");
            System.out.println("\nPara darmos inicio, precisaremos do seu login, caso não possua um, basta" +
                    "\nacessar: noobsbank.com.br e se cadastrar, é fácil! ;)");

            System.out.print("\nDigite o seu CPF:");
            String cpf_login = sc.nextLine();
            System.out.print("\nDigite a sua chave de autênticação(Token):");
            String pass_login = sc.nextLine();

            String cpf = db.getCPF(cpf_login);
            String auth = db.getAuth_code(pass_login);

            if ((cpf != null && cpf.equals(cpf_login)) && (auth != null && auth.equals(pass_login))) {
                String telephone_number = null;
                String saldo_usuário = null;

                String fullname = db.getFull_name(cpf_login);
                String telephone = db.getNumber_telephone(telephone_number);
                String balance = db.getSaldo(saldo_usuário);

                System.out.printf("\nBem vindo(a) novamente, %s!", fullname);
                System.out.printf("\nSeu telefone é: %s", telephone);
                System.out.printf("\nO seu saldo é: R$%s", balance);
            } else {
                System.out.println("CPF ou chave de autenticação incorretos. Tente novamente.");
            }
        } finally {
            // Fecha a conexão quando o aplicativo termina
            db.closeConnection();
        }
    }
}
