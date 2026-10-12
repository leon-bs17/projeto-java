package sistemaprovas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Testes executáveis sem bibliotecas externas. */
public final class Testes {
    private static int verificacoes;
    private static void verificar(boolean condicao) {
        verificacoes++;
        if (!condicao) throw new AssertionError("Falha na verificação " + verificacoes);
    }
    private static void rejeitar(Runnable acao) {
        boolean rejeitada = false;
        try { acao.run(); } catch (IllegalArgumentException | IllegalStateException erro) { rejeitada = true; }
        verificar(rejeitada);
    }
    public static void main(String[] args) {
        Prova prova = AplicacaoConsole.criarExemplo();
        verificar(prova.getQuantidadeQuestoes() == 3);
        verificar(prova.getValorTotal() == 6);
        verificar(prova.corrigir(Arrays.asList(" b ", "Proteger dados por métodos.", "V")).getPontuacaoObtida() == 6);
        verificar(prova.corrigir(Arrays.asList("A", "proteger dados", "F")).getPontuacaoObtida() == 2);
        verificar(prova.corrigir(Arrays.asList("", null, "inválido")).getPontuacaoObtida() == 0);
        verificar(prova.getQuestoes().get(1).corrigir("PROTEÇÃO DOS ATRIBUTOS POR MÉTODOS") == 3);
        verificar(prova.getQuestoes().get(1).corrigir("metodosXYZ dadosXYZ protegerXYZ") == 0);
        verificar(prova.getQuestoes().get(1).corrigir("proteger proteger dados") == 2);
        for (double valor : new double[] {0, -1, Double.NaN, Double.POSITIVE_INFINITY}) {
            rejeitar(() -> new QuestaoVerdadeiroFalso("Enunciado", valor, true));
        }
        rejeitar(() -> new Prova(" ", "Disciplina"));
        rejeitar(() -> new QuestaoVerdadeiroFalso(null, 1, true));
        rejeitar(() -> prova.adicionarQuestao(null));
        rejeitar(() -> prova.corrigir(Arrays.asList("B")));
        rejeitar(() -> new Prova("Título", "Disciplina").corrigir(new ArrayList<>()));
        rejeitar(() -> new QuestaoMultiplaEscolha("Q", 1, Arrays.asList("A", "B"), 'C'));
        rejeitar(() -> new QuestaoDiscursiva("Q", 1, Arrays.asList(Arrays.asList("!!!"))));
        List<String> alternativas = new ArrayList<>(Arrays.asList("Uma", "Outra"));
        QuestaoMultiplaEscolha objetiva = new QuestaoMultiplaEscolha("Q", 1, alternativas, 'A');
        alternativas.set(0, "Alteração externa");
        verificar(objetiva.getAlternativas().get(0).equals("Uma"));
        boolean protegida = false;
        try { prova.getQuestoes().clear(); } catch (UnsupportedOperationException erro) { protegida = true; }
        verificar(protegida);
        ResultadoProva resultado = prova.corrigir(Arrays.asList("B", "proteger dados", "V"));
        prova.adicionarQuestao(new QuestaoVerdadeiroFalso("Nova", 2, false));
        verificar(resultado.getValorTotal() == 6);
        verificar(resultado.gerarRelatorio().contains("NOTA FINAL: 5,00 / 6,00"));
        Questao falso = new QuestaoVerdadeiroFalso("Q", 1, false);
        verificar(falso.corrigir("falso") == 1 && falso.corrigir("V") == 0);
        System.out.println("Todos os " + verificacoes + " testes passaram.");
    }
}
