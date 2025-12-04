## 📦 Controle de Estoque de Produtos

Este projeto Java/Maven implementa um sistema básico de controle de estoque que permite registrar e gerenciar movimentações de produtos (entrada e saída), atualizando o saldo final com base em dados iniciais fornecidos em formato JSON.

## ✨ Funcionalidades

* **Leitura de Estoque Inicial:** Carrega automaticamente a lista de produtos e seus saldos iniciais a partir de uma *string* JSON.
* **Movimentação:** Permite lançar entradas (compras, ajustes) ou saídas (vendas, baixas) de produtos.
* **Identificador Único:** Cada movimentação é registrada com um número identificador sequencial único (`idMovimentacao`).
* **Controle de Saldo:** Calcula e retorna o saldo atualizado do produto após cada movimentação.
* **Validação:** Impede que a saída de estoque seja maior que o saldo atual do produto.

---

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando a plataforma Java e o gerenciador de dependências Maven.

* **Linguagem:** Java (JDK 17+)
* **Gerenciador de Dependências:** Apache Maven
* **Dependências:**
    * `org.json`: Utilizada para fazer o *parsing* (leitura) dos dados de estoque inicial no formato JSON.

## 💾 Estrutura dos Dados Iniciais

Os dados iniciais do estoque são fornecidos internamente na classe `ControleEstoque.java` e seguem o seguinte formato:

```json
{
    "estoque": [
        { "codigoProduto": 101, "descricaoProduto": "Caneta Azul", "estoque": 150 },
        { "codigoProduto": 102, "descricaoProduto": "Caderno Universitário", "estoque": 75 },
        // ... outros produtos
    ]
}
```
---

## 🚀 Como Executar o Projeto

Para rodar este projeto, você precisa ter o **JDK 17+** e o **Maven** instalados.

---

## 🤝 Contribuição
Sinta-se à vontade para sugerir melhorias, como mover o JSON para um arquivo externo ou implementar testes unitários.

---

## Autor: Vinícius Santana Lima / https://github.com/ViniVSL / www.linkedin.com/in/vinivsl-dev
