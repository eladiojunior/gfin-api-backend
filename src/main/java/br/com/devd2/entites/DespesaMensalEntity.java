package br.com.devd2.entites;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_DESPESA_MENSAL")
public class DespesaMensalEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESPESA_MENSAL")
    public long id;

    @Column(name = "ID_ENTIDADE_CONTROLE")
    public long idEntidade;

    @Column(name = "ID_DESPESA_FIXA")
    public long idDespesaFixa;

    @Column(name = "ID_NATUREZA_DESPESA")
    public int idNaturezaContaDespesa;

    @Column(name = "IN_DESPESA_PARCELADA")
    public boolean hasDespesaParcelada;

    /// <summary>
    /// Identificador, gerado pelo sistema, para controlar as despesas parceladas
    /// de forma a manter um vínculo entre elas, para futura atualização ou remoção.
    /// </summary>
    @Column(name = "CD_VINCULO_DESPESA_PARCELADA")
    public int codigoDespesaParcelada;

    @Column(name = "TX_DESCRICAO_DESPESA")
    public String descricaoDespesa;

    @Column(name = "DT_VENCIMENTO_DESPESA")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    public LocalDateTime dataVencimentoDespesa;

    @Column(name = "VL_DESPESA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorDespesa;

    @Column(name = "CD_FORMA_LIQUIDACAO_DESPESA")
    public int codigoTipoFormaLiquidacao;

    /// <summary>
    /// Identificador que relaciona a forma de liquidação com o registro de origem.
    /// Ex.: Para a forma de liquidação seja "Cheque" aqui terá o Id do Cheque
    /// utilizado,
    /// caso seja "Cartão de Crédito" aqui terá o Id do cartão utilizado.
    /// Esse atributo pode ser NULL, caso o usuário não queira vincular nada;
    /// </summary>
    @Column(name = "CD_VINCULO_FORMA_LIQUIDACAO")
    public int codigoVinculoFormaLiquidacao;

    @Column(name = "IN_DESPESA_LIQUIDADA")
    public boolean hasDespesaLiquidada;

    @Column(name = "TX_OBSERVACAO_DESPESA")
    public String textoObservacaoDespesa;

    @Column(name = "DH_LIQUIDACAO_DESPESA")
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dataHoraLiquidacaoDespesa;

    @Column(name = "VL_DESCONTO_LIQUIDACAO_DESPESA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorDescontoLiquidacaoDespesa;

    @Column(name = "VL_MULTA_JUROS_LIQUIDACAO_DESPESA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorMultaJurosLiquidacaoDespesa;

    @Column(name = "VL_TOTAL_LIQUIDACAO_DESPESA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorTotalLiquidacaoDespesa;

    @Column(name = "DH_REGISTRO_DESPESA")
    @CreationTimestamp
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dataHoraRegistroDespesa;

    @ManyToOne
    public DespesaFixaEntity despesaFixa;

    @ManyToOne
    @JoinColumn(name = "ID_NATUREZA_DESPESA", insertable = false, updatable = false, referencedColumnName = "ID_NATUREZA_CONTA")
    public NaturezaContaEntity naturezaContaDespesa;

}