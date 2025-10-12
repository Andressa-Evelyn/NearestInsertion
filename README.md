# Projeto Base – TSP (Vizinho Mais Próximo)

Template para implementar a heurística do vizinho mais próximo no Problema do Caixeiro Viajante (TSP). As atividades estão divididas em duas etapas: começar com a versão ingênua e, em seguida, otimizar com `KdTree`.

## Objetivo
1. Implementar `insertNearestNaive` em `Tour.java`, varrendo a lista encadeada circular para encontrar o vizinho mais próximo.
2. Implementar `algs4/KdTree.java` e adaptar `insertNearest` para utilizar `insertNearestKd`, reduzindo o custo de busca.
3. Preencher `questoes.txt` com respostas conceituais, comprimentos de tour e medições de tempo (comparando as duas abordagens).

## Estrutura do template
- `src/`: classes principais (`Tour`, `algs4.Point`, `NearestInsertion`, `TSPVisualizer`, `TSPTimer`).
- `algs4/`: utilitários necessários da biblioteca algs4, incluindo um stub de `KdTree`.
- `data/`: coloque aqui os arquivos de teste (p. ex. `tsp10.txt`, `tsp100.txt`, ...).
- `results/`: contém referências de saída para `tsp10.txt`.

## Fluxo sugerido
1. Complete os métodos faltantes em `Tour` utilizando apenas a varredura ingênua e valide com `NearestInsertion`.
2. Implemente `KdTree` e a versão otimizada, comparando os tempos com `TSPTimer` (semente fixa em `StdRandom.setSeed(123456789L)`).
3. Atualize `questoes.txt` com os comprimentos obtidos e as tabelas de tempo (ingênuo vs. `KdTree`).

## Compilação
```bash
javac src\*.java src\algs4\*.java   
```

## Execução
- Heurística: `java -cp src NearestInsertion data/tsp10.txt`
- Visualizador: `java -cp src TSPVisualizer data/tsp10.txt`
- Temporizador: `java -Xint -cp src TSPTimer 1000`

## Referências de resultado
- `results/tsp10-nearest.ans`: evolução do tour gerado pela heurística nearest (útil para depurar).
- `results/tsp10-optimal.ans`: solução ótima conhecida para comparação.

## Entrega
Submeta `Tour.java` e `questoes.txt` preenchidos com as análises solicitadas.

