package sistemaprovas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Prova {
    private final String titulo;
    private final String disciplina;
    private final List<Questao> questoes = new ArrayList<>();

    public Prova(String titulo, String disciplina) {
        this.titulo = Questao.exigirTexto(titulo, "Título");
        this.disciplina = Questao.exigirTexto(disciplina, "Disciplina");
    }

    public String getTitulo() { return titulo; }
    public String getDisciplina() { return disciplina; }
    public List<Questao> getQuestoes() {
        return Collections.unmodifiableList(new ArrayList<>(questoes));
    }
    public int getQuantidadeQuestoes() { return questoes.size(); }

    public void adicionarQuestao(Questao questao) {
        if (questao == null) throw new IllegalArgumentException("Questão não pode ser nula.");
        if (!Double.isFinite(getValorTotal() + questao.getValor())) {
            throw new IllegalArgumentException("Valor total excede o limite numérico.");
        }
        questoes.add(questao);
    }

    public double getValorTotal() {
        double total = 0;
        for (Questao questao : questoes) total += questao.getValor();
        return total;
    }

    public ResultadoProva corrigir(List<String> respostas) {
        if (questoes.isEmpty()) throw new IllegalStateException("Adicione questões antes de corrigir.");
        if (respostas == null || respostas.size() != questoes.size()) {
            throw new IllegalArgumentException("Informe uma resposta para cada questão.");
        }
        List<Double> pontos = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            // A subclasse decide como corrigir; não há inspeção manual do tipo.
            double nota = questao.corrigir(respostas.get(i));
            if (!Double.isFinite(nota) || nota < 0 || nota > questao.getValor()) {
                throw new IllegalStateException("Correção retornou pontuação inválida.");
            }
            pontos.add(nota);
            valores.add(questao.getValor());
        }
        return new ResultadoProva(titulo, disciplina, respostas, pontos, valores);
    }
}
