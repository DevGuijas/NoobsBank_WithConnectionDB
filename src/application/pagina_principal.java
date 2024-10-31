package application;

import entities.connectiondb;
import java.util.Scanner;

public class pagina_principal {

    static connectiondb db = new connectiondb();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Olá, bem vindo(a) ao NoobsBank!" +
                "\nO banco da gente! ;)");
        System.out.println("Para darmos inicio, precisaremos do seu login, caso não possua um, basta" +
                "\nacessar: noobsbank.com.br");

        System.out.print("\nDigite o seu CPF:");
        String cpf_login = sc.nextLine();
        System.out.print("\nDigite a sua chave de autênticação(Token):");
        String pass_login = sc.nextLine();

        String cpf = db.getCPF(cpf_login);
        String auth = db.getAuth_code(pass_login);
        String fullname = db.getFull_name(cpf_login);

        if ((cpf != null && cpf.equals(cpf_login)) && (auth != null && auth.equals(pass_login))) {
            System.out.printf("\nBem vindo(a) novamente, %s!", fullname);
        } else {
            System.out.println("CPF ou chave de autenticação incorretos. Tente novamente.");
        }
    }
}