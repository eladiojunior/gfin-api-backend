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
import jakarta.persistence.Table;

@Entity
@Table(name="TB_HISTORICO_DESPESA_FIXA")
public class HistoricoDespesaFixaEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HISTORICO_DESPESA_FIXA")
    public long id;

    @Column(name="ID_DESPESA_FIXA")
    public long idDespesaFixa;

    @Column(name="VL_HISTORICO_DESPESA_FIXA", length = 17, precision = 15, scale = 2)
    public BigDecimal valorHistoricoDespesaFixa;

    @Column(name="DH_REGISTRO_HISTORICO_DESPESA_FIXA")
    @CreationTimestamp
    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime dataHoraRegistroHistoricoDespesaFixa;

}