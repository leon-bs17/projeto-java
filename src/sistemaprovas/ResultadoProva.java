package sistemaprovas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/** Registro imutável do resultado, independente de alterações posteriores na prova. */
public final class ResultadoProva {
    private final String titulo;
    private final String disciplina;
    private final List<String> respostas;
    private final List<Double> pontos;
    private final List<Double> valores;

    ResultadoProva(String titulo, String disciplina, List<String> respostas,
                   List<Double> pontos, List<Double> valores) {
        this.titulo = titulo;
        this.disciplina = disciplina;
        this.respostas = Collections.unmodifiableList(new ArrayList<>(respostas));
        this.pontos = Collections.unmodifiableList(new ArrayList<>(pontos));
        this.valores = Collections.unmodifiableList(new ArrayList<>(valores));
    }

    public double getPontuacaoObtida() {
        double total = 0;
        for (double ponto : pontos) total += ponto;
        return total;
    }
    public double getValorTotal() {
        double total = 0;
        for (double valor : valores) total += valor;
        return total;
    }
    public List<Double> getPontuacoes() { return pontos; }

    public String gerarRelatorio() {
        StringBuilder texto = new StringBuilder("================================\nRESULTADO\n================================\n");
        texto.append(titulo).append("\nDisciplina: ").append(disciplina).append("\n\n");
        for (int i = 0; i < pontos.size(); i++) {
            texto.append(String.format(Locale.forLanguageTag("pt-BR"),
                "Questão %d: %.2f / %.2f%n", i + 1, pontos.get(i), valores.get(i)));
            String resposta = respostas.get(i);
            texto.append("Resposta: ").append(resposta == null || resposta.trim().isEmpty()
                ? "(em branco)" : resposta).append("\n\n");
        }
        texto.append(String.format(Locale.forLanguageTag("pt-BR"),
            "NOTA FINAL: %.2f / %.2f%n================================%n",
            getPontuacaoObtida(), getValorTotal()));
        return texto.toString();
    }
}
