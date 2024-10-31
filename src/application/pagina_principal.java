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
        System.out.println("\nDigite a sua chave de autênticação:");
        String pass_login = sc.nextLine();

        String fullname = null;
        String cpf = db.getCPF(cpf_login);
        String auth = db.getAuth_code(pass_login);
        String name = db.getFull_name(fullname);

        if ((cpf != null && cpf.equals(cpf_login)) && (auth != null && auth.equals(pass_login))) {
            System.out.printf("Bem vindo(a) novamente " + fullname +"!");
        } else {

        }

    }
}
