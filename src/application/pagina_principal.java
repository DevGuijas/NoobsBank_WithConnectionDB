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

                int exit_method = 0;
                while (exit_method != 5) {
                    System.out.println("\n(1) Consultar informações pessoais." +
                            "\n(2) Consultar saldo." +
                            "\n(3) Efetuar deposito." +
                            "\n(4) Efetuar saque." +
                            "\n(5) Sair.");
                    System.out.print("\nA sua escolha:");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("\nInformações pessoais da conta:");
                            System.out.println("* Nome completo: " + fullname);
                            System.out.println("* CPF: " + cpf);
                            System.out.println("* Número de telefone cadastrado: " + telephone);
                            break;
                        case 2:
                            System.out.println("* Saldo: " + balance);
                            break;
                        case 3:
                            db.invalide_operation();
                            break;
                        case 4:
                            db.invalide_operation();
                            break;
                        case 5:
                            System.out.printf("Entendemos a sua decisão. Até logo! ;)");
                            exit_method = 5;
                            break;
                    }
                }
            } else {
                System.out.println("CPF ou chave de autenticação incorretos. Tente novamente.");
            }
        } finally {
            // Fecha a conexão quando o aplicativo termina
            db.closeConnection();
        }
    }

    public void invalide_option() {
        System.out.printf("Infelizmente essa opção ainda não está disponível. Tente novamente mais tarde");
    }
}
