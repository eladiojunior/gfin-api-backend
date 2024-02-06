package br.com.devd2.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.devd2.entites.DespesaFixaEntity;
import br.com.devd2.entites.NaturezaContaEntity;
import br.com.devd2.enums.TipoFormaLiquidacaoEnum;
import br.com.devd2.enums.TipoLancamentoEnum;
import br.com.devd2.models.DespesaFixaModel;
import br.com.devd2.models.NaturezaContaModel;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class MapperHelperTest {

    @Test
    public void mapDespesaFixaTest() {

        //given
        DespesaFixaEntity entity = new DespesaFixaEntity();
        entity.id = 1;
        entity.descricaoDespesaFixa = "Despesa Fixa 01";
        entity.codigoTipoFormaLiquidacaoDespesaFixa = TipoFormaLiquidacaoEnum.Dinheiro.getCodigo();
        entity.valorDespesaFixa = BigDecimal.valueOf(999.01);
        //when
        DespesaFixaModel model = MapperHelper.INSTANCE.toDespesaFixaModel(entity);
        //then
        assertNotNull(model);
        assertEquals(model.idDespesaFixa, entity.id);
        assertEquals(model.codigoTipoFormaLiquidacaoDespesaFixa, entity.codigoTipoFormaLiquidacaoDespesaFixa);
        assertEquals(model.descricaoDespesaFixa, entity.descricaoDespesaFixa);
        assertEquals(model.valorDespesaFixa, entity.valorDespesaFixa.doubleValue());

    }

    @Test
    public void mapNaturezaContaTest() {

        //given
        NaturezaContaEntity entity = new NaturezaContaEntity();
        entity.id = 1;
        entity.descricaoNaturezaConta = "Natureza de Conta 01";
        entity.codigoTipoLancamentoConta = TipoLancamentoEnum.Despesa.getCodigo();
        //when
        NaturezaContaModel model = MapperHelper.INSTANCE.toNaturezaContaModel(entity);
        //then
        assertNotNull(model);
        assertEquals(model.idNaturezaConta, entity.id);
        assertEquals(model.codigoTipoLancamentoConta, entity.codigoTipoLancamentoConta);
        assertEquals(model.descricaoNaturezaConta, entity.descricaoNaturezaConta);

    }

}