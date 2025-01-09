package br.com.devd2.entites;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_NATUREZA_CONTA")
public class NaturezaContaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NATUREZA_CONTA")
    public int id;
 
    @Column(name="CD_LANCAMENTO_CONTA")
    public int codigoTipoLancamentoConta;

    @Column(name="TX_DESCRICAO_NATUREZA_CONTA")
    public String descricaoNaturezaConta;

    @Column(name="CD_SITUACAO_NATUREZA_CONTA")
    public int codigoTipoSituacaoNaturezaConta;

    @Column(name="DH_REGISTRO_NATUREZA_CONTA")
    @CreationTimestamp
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dataHoraRegistroNaturezaConta;

}