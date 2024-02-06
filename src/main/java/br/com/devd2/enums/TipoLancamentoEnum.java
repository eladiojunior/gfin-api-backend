package br.com.devd2.enums;

public enum TipoLancamentoEnum {
    Despesa(1, "Despesa"), 
    Receita(2, "Receita");

    private final int codigo;
    private final String descricao;

    TipoLancamentoEnum(int codigo, String descricao) {
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
     * Verificar se o tipo lançamento existe.
     */
    public static boolean hasTipoLancamento(int codigo) {
        var hasCodigo = false;
        
        if (codigo == 0) {
            return hasCodigo;
        }

        for (var item : TipoLancamentoEnum.values()) {
            if (item.codigo == codigo) {
                hasCodigo = true;
                break;
            }
        } 
        
        return hasCodigo;
        
    }
}
