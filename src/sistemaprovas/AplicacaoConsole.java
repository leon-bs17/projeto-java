package sistemaprovas;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Interface de console; as regras de correção ficam nas classes de domínio. */
public final class AplicacaoConsole {
    private final Scanner entrada;
    private final PrintStream saida;

    public AplicacaoConsole(Scanner entrada, PrintStream saida) {
        if (entrada == null || saida == null) throw new IllegalArgumentException("Entrada e saída obrigatórias.");
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.println("SISTEMA DE APLICAÇÃO E CORREÇÃO DE PROVAS");
        saida.println("1 - Criar e cadastrar uma prova\n2 - Executar prova de exemplo");
        Prova prova = lerInteiro("Opção: ", 1, 2) == 1 ? cadastrarProva() : criarExemplo();
        aplicar(prova);
    }

    private Prova cadastrarProva() {
        Prova prova = new Prova(lerTexto("Título: "), lerTexto("Disciplina: "));
        do {
            saida.println("\n1 - Múltipla escolha\n2 - Discursiva\n3 - Verdadeiro ou falso");
            int tipo = lerInteiro("Tipo: ", 1, 3);
            String enunciado = lerTexto("Enunciado: ");
            double valor = lerValor();
            Questao questao;
            switch (tipo) {
                case 1:
                    int quantidade = lerInteiro("Quantidade de alternativas (2 a 26): ", 2, 26);
                    List<String> alternativas = new ArrayList<>();
                    for (int i = 0; i < quantidade; i++) {
                        alternativas.add(lerTexto((char) ('A' + i) + ") "));
                    }
                    String correta;
                    do {
                        correta = lerTexto("Letra da alternativa correta: ").toUpperCase(java.util.Locale.ROOT);
                    } while (correta.length() != 1 || correta.charAt(0) < 'A'
                             || correta.charAt(0) >= 'A' + quantidade);
                    questao = new QuestaoMultiplaEscolha(enunciado, valor, alternativas, correta.charAt(0));
                    break;
                case 2:
                    int numero = lerInteiro("Quantidade de conceitos avaliados: ", 1, 20);
                    List<List<String>> criterios = new ArrayList<>();
                    for (int i = 0; i < numero; i++) {
                        List<String> termos;
                        while (true) {
                            termos = Arrays.asList(lerTexto("Conceito " + (i + 1)
                                + " (termos equivalentes separados por ;): ").split(";", -1));
                            try {
                                new QuestaoDiscursiva("Validação", 1, Arrays.asList(termos));
                                break;
                            } catch (IllegalArgumentException erro) { saida.println(erro.getMessage()); }
                        }
                        criterios.add(termos);
                    }
                    questao = new QuestaoDiscursiva(enunciado, valor, criterios);
                    break;
                default:
                    questao = new QuestaoVerdadeiroFalso(enunciado, valor,
                        lerInteiro("Gabarito (1 - Verdadeiro; 2 - Falso): ", 1, 2) == 1);
            }
            try { prova.adicionarQuestao(questao); }
            catch (IllegalArgumentException erro) { saida.println(erro.getMessage()); }
        } while (lerInteiro("Adicionar outra questão? (1 - Sim; 2 - Não): ", 1, 2) == 1
                 || prova.getQuantidadeQuestoes() == 0);
        return prova;
    }

    public static Prova criarExemplo() {
        Prova prova = new Prova("Prova de Programação", "Programação Orientada a Objetos");
        prova.adicionarQuestao(new QuestaoMultiplaEscolha(
            "Qual palavra-chave declara uma classe que não pode ser instanciada diretamente?", 2,
            Arrays.asList("static", "abstract", "final", "private"), 'B'));
        prova.adicionarQuestao(new QuestaoDiscursiva("Explique o conceito de encapsulamento.", 3,
            Arrays.asList(Arrays.asList("proteger", "proteção", "ocultar", "ocultação"),
                          Arrays.asList("dados", "atributos", "estado"),
                          Arrays.asList("métodos", "acesso controlado", "getters", "setters"))));
        prova.adicionarQuestao(new QuestaoVerdadeiroFalso(
            "O polimorfismo permite usar referências Questao para diferentes subclasses.", 1, true));
        return prova;
    }

    private void aplicar(Prova prova) {
        saida.println("\n================================\n" + prova.getTitulo());
        saida.println("Disciplina: " + prova.getDisciplina());
        saida.println("Questões: " + prova.getQuantidadeQuestoes() + " | Valor total: " + prova.getValorTotal());
        List<String> respostas = new ArrayList<>();
        int numero = 1;
        for (Questao questao : prova.getQuestoes()) {
            saida.println("\n--------------------------------\nQuestão " + numero++ + " - Valor: " + questao.getValor());
            saida.println(questao.apresentar());
            respostas.add(lerLinha("Resposta (Enter para deixar em branco): "));
        }
        saida.println("\n" + prova.corrigir(respostas).gerarRelatorio());
    }

    private String lerLinha(String mensagem) {
        saida.print(mensagem);
        if (!entrada.hasNextLine()) throw new IllegalStateException("Entrada encerrada antes do fim da prova.");
        return entrada.nextLine();
    }
    private String lerTexto(String mensagem) {
        while (true) {
            String texto = lerLinha(mensagem).trim();
            if (!texto.isEmpty()) return texto;
            saida.println("Preenchimento obrigatório.");
        }
    }
    private int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            try {
                int valor = Integer.parseInt(lerLinha(mensagem).trim());
                if (valor >= minimo && valor <= maximo) return valor;
            } catch (NumberFormatException erro) { /* Solicita novamente. */ }
            saida.println("Informe um inteiro de " + minimo + " a " + maximo + ".");
        }
    }
    private double lerValor() {
        while (true) {
            try {
                double valor = Double.parseDouble(lerLinha("Valor da questão: ").trim().replace(',', '.'));
                if (Double.isFinite(valor) && valor > 0) return valor;
            } catch (NumberFormatException erro) { /* Solicita novamente. */ }
            saida.println("Informe um número finito maior que zero.");
        }
    }
}
