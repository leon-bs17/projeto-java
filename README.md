# Sistema de Aplicação e Correção de Provas

Trabalho N1 de Programação Orientada a Objetos, desenvolvido em Java.

## Identificação da dupla

Preencher antes da entrega:

| Informação | Dados |
| --- | --- |
| Integrante 1 | A preencher |
| Matrícula 1 | A preencher |
| Integrante 2 | A preencher |
| Matrícula 2 | A preencher |
| Universidade | A preencher |
| Curso e turma | A preencher |
| Professor | A preencher |

Repositório: https://github.com/leon-bs17/projeto-java

O nome da conta do GitHub não substitui a identificação dos integrantes acima.

## Entrega pelo AVA

O ZIP inclui o projeto completo e o arquivo `ENTREGA_AVA.txt` com o link do
repositório. A explicação dos conceitos está em `CONCEITOS_POO.md`.
Preencha a identificação antes de enviar. Confira os formatos aceitos no AVA.


## Executar

Requisito: JDK 11 ou superior (o JRE sozinho não inclui o compilador).
Não há bibliotecas externas, banco de dados ou dependências de rede.

No Windows, extraia o ZIP e execute `executar.bat` dentro da pasta SistemaProvas.
No Linux/macOS, abra o terminal nessa pasta e execute `sh executar.sh`.

Também é possível executar manualmente, a partir da pasta SistemaProvas:

```
mkdir bin
javac -encoding UTF-8 -d bin src/sistemaprovas/*.java
java -Dfile.encoding=UTF-8 -cp bin sistemaprovas.Main
```

Em uma IDE, abra a pasta, marque `src` como pasta de código-fonte e execute
`sistemaprovas.Main`.

## Utilização

1. Escolha criar uma prova ou aplicar a prova de exemplo.
2. No cadastro, informe título, disciplina, questões e respectivos gabaritos.
3. Para discursivas, cada conceito avaliado recebe um grupo de termos equivalentes,
   separados por ponto e vírgula. Exemplo: `proteger;proteção;ocultar`.
4. Responda às questões. Enter deixa uma resposta em branco, com nota zero.
5. O sistema mostra respostas, pontuação por questão e nota final sobre o total.

O cadastro e a aplicação são realizados na mesma sessão. A aplicação não salva
provas em disco. As opções numéricas de tipo são usadas apenas para criar objetos;
a correção usa o método polimórfico de cada questão.

## Exemplo de execução

Escolha a opção 2 e informe:

- Questão 1: `B`
- Questão 2: `Proteger dados por métodos.`
- Questão 3: `V`

Resultado esperado: 2,00 + 3,00 + 1,00 = **6,00 / 6,00**.
Se a resposta discursiva for apenas `proteger dados`, recebe 2,00 / 3,00.

## Organização

- `src/sistemaprovas`: todas as classes da aplicação.
- `testes/sistemaprovas/Testes.java`: testes automáticos sem dependências.
- `CONCEITOS_POO.md`: explicação dos conceitos e da correção discursiva.
- `executar.bat` e `executar.sh`: compilação e execução.

## Testes

Na pasta do projeto:

```
javac -encoding UTF-8 -d bin src/sistemaprovas/*.java testes/sistemaprovas/*.java
java -cp bin sistemaprovas.Testes
```

Os testes verificam notas integrais, parciais e zero; respostas em branco;
validações; proteção das coleções; normalização de acentos; limites de palavras;
e estabilidade do resultado após adicionar questões à prova.
