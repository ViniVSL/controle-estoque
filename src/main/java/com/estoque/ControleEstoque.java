package com.estoque;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class Produto {
    private final int codigoProduto;
    private final String descricaoProduto;
    private int estoque;

    public Produto(int codigoProduto, String descricaoProduto, int estoque) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.estoque = estoque;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}

public class ControleEstoque {

    private static final String DADOS_ESTOQUE_JSON =
            "{\"estoque\": [ " +
                    "  { \"codigoProduto\": 101, \"descricaoProduto\": \"Caneta Azul\", \"estoque\": 150 }," +
                    "  { \"codigoProduto\": 102, \"descricaoProduto\": \"Caderno Universitário\", \"estoque\": 75 }," +
                    "  { \"codigoProduto\": 103, \"descricaoProduto\": \"Borracha Branca\", \"estoque\": 200 }," +
                    "  { \"codigoProduto\": 104, \"descricaoProduto\": \"Lápis Preto HB\", \"estoque\": 320 }," +
                    "  { \"codigoProduto\": 105, \"descricaoProduto\": \"Marcador de Texto Amarelo\", \"estoque\": 90 }" +
                    "]}";

    private final Map<Integer, Produto> estoqueMap = new HashMap<>();

    private final AtomicInteger idMovimentacao = new AtomicInteger(1000);

    public ControleEstoque() {
        lerEstoqueInicial();
    }

    private void lerEstoqueInicial() {
        try {
            JSONObject jsonObject = new JSONObject(DADOS_ESTOQUE_JSON);
            JSONArray estoqueArray = jsonObject.getJSONArray("estoque");

            for (int i = 0; i < estoqueArray.length(); i++) {
                JSONObject item = estoqueArray.getJSONObject(i);
                int codigo = item.getInt("codigoProduto");
                String descricao = item.getString("descricaoProduto");
                int qtde = item.getInt("estoque");

                Produto produto = new Produto(codigo, descricao, qtde);
                estoqueMap.put(codigo, produto);
            }
        } catch (Exception e) {
            System.err.println("❌ ERRO ao ler o JSON de estoque inicial: " + e.getMessage());
        }
    }

    public void movimentarEstoque(int codigoProduto, String tipoMovimentacao, int quantidade, String descricaoMovimentacao) {
        Produto produto = estoqueMap.get(codigoProduto);

        if (produto == null) {
            System.out.println("⚠️ Produto com código " + codigoProduto + " não encontrado.");
            return;
        }

        if (quantidade <= 0) {
            System.out.println("⚠️ Quantidade inválida. A movimentação deve ser maior que zero.");
            return;
        }

        int novoEstoque = produto.getEstoque();
        int id = idMovimentacao.getAndIncrement();

        System.out.println("\n--- Detalhes da Movimentação #" + id + " ---");
        System.out.println("  Descrição Mov.: " + descricaoMovimentacao);
        System.out.println("  Produto: " + produto.getDescricaoProduto() + " (" + codigoProduto + ")");
        System.out.println("  Tipo: " + tipoMovimentacao.toUpperCase() + " | Quantidade: " + quantidade);

        if (tipoMovimentacao.toUpperCase().startsWith("E")) {
            novoEstoque += quantidade;
            produto.setEstoque(novoEstoque);
            System.out.println("  Ação: Estoque AUMENTADO.");
        } else if (tipoMovimentacao.toUpperCase().startsWith("S")) {
            if (novoEstoque < quantidade) {
                System.out.println("🚫 ERRO: Estoque insuficiente! Qtde Atual: " + novoEstoque + ", Qtde Saída: " + quantidade);
            } else {
                novoEstoque -= quantidade;
                produto.setEstoque(novoEstoque);
                System.out.println("  Ação: Estoque DIMINUÍDO.");
            }
        } else {
            System.out.println("⚠️ Tipo de movimentação ('" + tipoMovimentacao + "') inválido. Use 'E' para Entrada ou 'S' para Saída.");
            return;
        }

        System.out.println("✅ Saldo Final: " + produto.getEstoque() + " unidades.");
    }

    public void exibirEstoqueAtual() {
        System.out.println("\n=============================================");
        System.out.println("          📊 ESTOQUE ATUAL GERAL");
        System.out.println("=============================================");
        System.out.printf("%-10s %-30s %s\n", "CÓDIGO", "DESCRIÇÃO", "QTDE");
        System.out.println("---------------------------------------------");

        for (Produto produto : estoqueMap.values()) {
            System.out.printf("%-10d %-30s %d\n",
                    produto.getCodigoProduto(),
                    produto.getDescricaoProduto(),
                    produto.getEstoque()
            );
        }
        System.out.println("=============================================");
    }

    public static void main(String[] args) {
        ControleEstoque controle = new ControleEstoque();
        new Scanner(System.in);

        controle.exibirEstoqueAtual();

        System.out.println("\n### Módulo de Teste de Movimentação ###");


        controle.movimentarEstoque(
                101,
                "ENTRADA",
                50,
                "Recebimento de compra NFe 12345"
        );

        controle.movimentarEstoque(
                102,
                "SAIDA",
                25,
                "Venda para Cliente A"
        );

        controle.movimentarEstoque(
                104,
                "S",
                500,
                "Venda para Cliente B - Estoque Insuficiente"
        );

        controle.movimentarEstoque(
                999,
                "E",
                10,
                "Produto Inexistente"
        );


        controle.exibirEstoqueAtual();

    }
}