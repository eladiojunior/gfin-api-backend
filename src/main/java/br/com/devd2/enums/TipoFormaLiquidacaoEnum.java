package br.com.devd2.enums;

public enum TipoFormaLiquidacaoEnum {
    Dinheiro(1, "Dinheiro"),
    CartaoCreditoDebito(2, "Cartão de Crédito/Débito"),
    BoletoCobranca(3, "Boleto de Cobrança"),
    DebitoEmConta(4, "Débito em Conta"),
    FaturaMensal(5, "Fatura");
    
    private final int codigo;
    private final String descricao;
    
    TipoFormaLiquidacaoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    public int getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    
    /*
     * Verificar se o tipo forma de liquidação existe.
     */
    public static boolean hasTipoFormaLiquidacao(int codigo) {
        var hasCodigo = false;
        
        if (codigo == 0) {
            return hasCodigo;
        }

        for (var item : TipoFormaLiquidacaoEnum.values()) {
            if (item.codigo == codigo) {
                hasCodigo = true;
                break;
            }
        } 
        
        return hasCodigo;
        
    }
    
}