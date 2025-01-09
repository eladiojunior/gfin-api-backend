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
@Table(name="TB_DESPESA_FIXA")
public class DespesaFixaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DESPESA_FIXA")
    public int id;

    @Column(name="ID_NATUREZA_CONTA_DESPESA_FIXA")
    public int idNaturezaContaDespesaFixa;

    @Column(name="TX_DESCRICAO_DESPESA_FIXA")
    public String descricaoDespesaFixa;

    @Column(name="DD_VENCIMENTO_DESPESA_FIXA")
    public int diaVencimentoDespesaFixa;

    @Column(name="VL_DESPESA_FIXA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorDespesaFixa;

    @Column(name="CD_TIPO_SITUACAO_DESPESA_FIXA")
    public int codigoTipoSituacaoDespesaFixa;

    @Column(name="CD_FORMA_LIQUIDACAO_DESPESA_FIXA")
    public int codigoTipoFormaLiquidacaoDespesaFixa;

    @Column(name="DH_REGISTRO_DESPESA_FIXA")
    @CreationTimestamp
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dataHoraRegistroDespesaFixa;

    @ManyToOne
    @JoinColumn(name = "ID_NATUREZA_CONTA_DESPESA_FIXA", insertable = false, updatable = false, referencedColumnName = "ID_NATUREZA_CONTA")
    public NaturezaContaEntity naturezaContaDespesaFixa;

}