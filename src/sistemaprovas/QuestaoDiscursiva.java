package sistemaprovas;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class QuestaoDiscursiva extends Questao {
    private final List<List<String>> criterios;

    /**
     * Cada grupo representa um conceito e contém palavras/expressões equivalentes.
     * Cada conceito encontrado recebe uma fração igual do valor da questão.
     * A comparação ignora acentos, maiúsculas e pontuação, mas exige palavras
     * completas. É uma aproximação didática: não interpreta significado ou negação.
     */
    public QuestaoDiscursiva(String enunciado, double valor, List<List<String>> criterios) {
        super(enunciado, valor);
        if (criterios == null || criterios.isEmpty()) {
            throw new IllegalArgumentException("Informe pelo menos um critério de correção.");
        }
        List<List<String>> copia = new ArrayList<>();
        for (List<String> grupo : criterios) {
            if (grupo == null || grupo.isEmpty()) {
                throw new IllegalArgumentException("Cada critério deve possuir termos.");
            }
            List<String> termos = new ArrayList<>();
            for (String termo : grupo) {
                String normalizado = normalizar(exigirTexto(termo, "Termo"));
                if (normalizado.isEmpty()) {
                    throw new IllegalArgumentException("Termo deve conter letras ou números.");
                }
                termos.add(normalizado);
            }
            copia.add(Collections.unmodifiableList(termos));
        }
        this.criterios = Collections.unmodifiableList(copia);
    }

    private static String normalizar(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replaceAll("\\p{M}+", "").toLowerCase(Locale.ROOT)
            .replaceAll("[^\\p{L}\\p{N}]+", " ").trim().replaceAll("\\s+", " ");
    }

    @Override
    public double corrigir(String resposta) {
        String texto = " " + normalizar(resposta) + " ";
        int atendidos = 0;
        for (List<String> grupo : criterios) {
            for (String termo : grupo) {
                if (texto.contains(" " + termo + " ")) {
                    atendidos++;
                    break;
                }
            }
        }
        return getValor() * atendidos / criterios.size();
    }

    @Override
    public String apresentar() { return getEnunciado() + "\nResponda com suas palavras."; }
}
