package sistemaprovas;

/** Abstração comum a todos os formatos de questão. */
public abstract class Questao {
    private final String enunciado;
    private final double valor;

    protected Questao(String enunciado, double valor) {
        this.enunciado = exigirTexto(enunciado, "Enunciado");
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException("O valor deve ser finito e maior que zero.");
        }
        this.valor = valor;
    }

    protected static String exigirTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ficar vazio.");
        }
        return texto.trim();
    }

    public final String getEnunciado() { return enunciado; }
    public final double getValor() { return valor; }
    public abstract double corrigir(String resposta);
    public abstract String apresentar();
}
