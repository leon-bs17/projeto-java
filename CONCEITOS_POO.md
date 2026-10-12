# Conceitos de Programação Orientada a Objetos

## Abstração

`Questao` é uma classe abstrata que representa as características comuns dos
formatos de questão: enunciado e valor. Define os contratos `corrigir(String)`
e `apresentar()`, que são implementados pelas subclasses. Não pode ser
instanciada diretamente.

## Encapsulamento

Todos os atributos são privados. Enunciados e valores são definidos no construtor
e não possuem setters indiscriminados. Uma questão sempre possui texto não vazio
e valor finito maior que zero. Os títulos e disciplinas também são validados.

`Prova` controla a inclusão de questões pelo método `adicionarQuestao`, rejeita
objetos nulos e impede que a soma ultrapasse o limite numérico. As coleções são
copiadas e expostas somente para leitura. As alternativas e os critérios
não podem ser alterados por listas externas. `ResultadoProva` guarda cópias,
preservando o resultado mesmo quando novas questões são adicionadas à prova.

## Herança

`QuestaoMultiplaEscolha`, `QuestaoDiscursiva` e `QuestaoVerdadeiroFalso`
herdam de `Questao`. Reutilizam atributos, validação e métodos comuns, mas
possuem características próprias: alternativas/gabarito, critérios de correção
e um gabarito booleano, respectivamente.

## Polimorfismo

`Prova` armazena todos os formatos em uma `List<Questao>`. No método `corrigir`,
a chamada `questao.corrigir(resposta)` executa a implementação da subclasse
correspondente. Não há `instanceof` nem testes de tipos na lógica de correção.
A interface de console também chama `questao.apresentar()` polimorficamente.

O terceiro formato foi integrado apenas criando sua subclasse e disponibilizando
sua construção no cadastro e no exemplo. A classe `Prova` não precisa identificar
manualmente o formato das questões.

## Estratégia de correção discursiva

A correção usa grupos de termos para representar conceitos esperados. Cada grupo
vale uma fração igual da pontuação. Basta encontrar um termo equivalente do grupo
para pontuar naquele conceito, sem contar repetições.

Na prova de exemplo, uma questão de 3 pontos possui três grupos:

1. Proteção: proteger, proteção, ocultar ou ocultação.
2. Estado: dados, atributos ou estado.
3. Acesso: métodos, acesso controlado, getters ou setters.

Assim, `proteger dados` atende a dois conceitos e recebe 2 pontos; `proteger dados
por métodos` atende aos três e recebe 3 pontos. O algoritmo ignora acentos,
maiúsculas e pontuação, e busca palavras/expressões completas, evitando pontuar
por fragmentos dentro de outras palavras.

Essa estratégia foi escolhida por ser simples, determinística e permitir nota
parcial. É uma simplificação didática: não avalia contexto, negações ou equivalentes
que não estejam cadastrados. Uma avaliação discursiva real requer revisão humana
ou uma estratégia semântica mais sofisticada.

## Responsabilidades

`Main` apenas inicia e coordena a execução. `AplicacaoConsole` cuida da interação
e do cadastro. `Prova` organiza as questões e coordena a correção. Cada subclasse
corrige sua própria resposta. `ResultadoProva` calcula os totais e gera o relatório.

## Funcionalidades implementadas

Criação de prova; título e disciplina; cadastro de questões; validação dos valores;
consulta de quantidade e valor total; exibição de questões; coleta de respostas;
correção; pontuação por questão; nota final e relatório. O terceiro tipo,
verdadeiro/falso, atende ao desafio adicional.
