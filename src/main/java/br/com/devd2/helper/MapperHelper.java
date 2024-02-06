package br.com.devd2.helper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.devd2.entites.DespesaFixaEntity;
import br.com.devd2.entites.NaturezaContaEntity;
import br.com.devd2.models.DespesaFixaEdicaoModel;
import br.com.devd2.models.DespesaFixaModel;
import br.com.devd2.models.NaturezaContaEdicaoModel;
import br.com.devd2.models.NaturezaContaModel;

@Mapper
public interface MapperHelper {

    MapperHelper INSTANCE = Mappers.getMapper(MapperHelper.class);

    @Mapping(target = "dataHoraRegistroDespesaFixa", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "idDespesaFixa", source = "id")
    DespesaFixaModel toDespesaFixaModel(DespesaFixaEntity entity);

    DespesaFixaEntity toDespesaFixaEntity(DespesaFixaEdicaoModel model);

    @Mapping(target = "dataHoraRegistroNaturezaConta", dateFormat = "dd/MM/yyyy HH:mm:ss")
    @Mapping(target = "idNaturezaConta", source = "id")
    NaturezaContaModel toNaturezaContaModel(NaturezaContaEntity entity);

    NaturezaContaEntity toNaturezaContaEntity(NaturezaContaEdicaoModel model);

}
