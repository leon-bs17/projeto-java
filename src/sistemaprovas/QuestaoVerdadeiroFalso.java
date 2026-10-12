package sistemaprovas;

import java.util.Locale;

/** Terceiro formato (bônus), integrado pelo mesmo contrato polimórfico. */
public final class QuestaoVerdadeiroFalso extends Questao {
    private final boolean respostaCorreta;

    public QuestaoVerdadeiroFalso(String enunciado, double valor, boolean respostaCorreta) {
        super(enunciado, valor);
        this.respostaCorreta = respostaCorreta;
    }

    @Override
    public double corrigir(String resposta) {
        String texto = resposta == null ? "" : resposta.trim().toLowerCase(Locale.ROOT);
        if (texto.equals("v") || texto.equals("verdadeiro")) {
            return respostaCorreta ? getValor() : 0;
        }
        if (texto.equals("f") || texto.equals("falso")) {
            return respostaCorreta ? 0 : getValor();
        }
        return 0;
    }

    @Override
    public String apresentar() { return getEnunciado() + "\nResponda V (verdadeiro) ou F (falso)."; }
}
