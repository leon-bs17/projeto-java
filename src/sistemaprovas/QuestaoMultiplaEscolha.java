package sistemaprovas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class QuestaoMultiplaEscolha extends Questao {
    private final List<String> alternativas;
    private final char alternativaCorreta;

    public QuestaoMultiplaEscolha(String enunciado, double valor,
                                 List<String> alternativas, char alternativaCorreta) {
        super(enunciado, valor);
        if (alternativas == null || alternativas.size() < 2 || alternativas.size() > 26) {
            throw new IllegalArgumentException("Informe de 2 a 26 alternativas.");
        }
        List<String> copia = new ArrayList<>();
        for (String alternativa : alternativas) {
            copia.add(exigirTexto(alternativa, "Alternativa"));
        }
        char correta = Character.toUpperCase(alternativaCorreta);
        if (correta < 'A' || correta >= 'A' + copia.size()) {
            throw new IllegalArgumentException("A alternativa correta deve existir.");
        }
        this.alternativas = Collections.unmodifiableList(copia);
        this.alternativaCorreta = correta;
    }

    public List<String> getAlternativas() { return alternativas; }

    @Override
    public double corrigir(String resposta) {
        String normalizada = resposta == null ? "" : resposta.trim().toUpperCase(Locale.ROOT);
        return normalizada.equals(String.valueOf(alternativaCorreta)) ? getValor() : 0;
    }

    @Override
    public String apresentar() {
        StringBuilder texto = new StringBuilder(getEnunciado());
        for (int i = 0; i < alternativas.size(); i++) {
            texto.append(System.lineSeparator()).append((char) ('A' + i))
                 .append(") ").append(alternativas.get(i));
        }
        return texto.toString();
    }
}
