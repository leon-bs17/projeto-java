package sistemaprovas;

import java.util.Scanner;

public final class Main {
    private Main() { }
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in, "UTF-8")) {
            new AplicacaoConsole(entrada, System.out).executar();
        } catch (IllegalStateException erro) {
            System.out.println("Execução encerrada: " + erro.getMessage());
        }
    }
}
