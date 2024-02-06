package br.com.devd2.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.devd2.entites.DespesaFixaEntity;
import br.com.devd2.entites.HistoricoDespesaFixaEntity;
import br.com.devd2.entites.NaturezaContaEntity;
import br.com.devd2.enums.TipoFormaLiquidacaoEnum;
import br.com.devd2.enums.TipoSituacaoEnum;
import br.com.devd2.helper.MapperHelper;
import br.com.devd2.infra.EntidadeConst;
import br.com.devd2.infra.ServiceException;
import br.com.devd2.models.DespesaFixaEdicaoModel;
import br.com.devd2.models.DespesaFixaModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DespesaService {

    /**
     * Lista totas as despesas fixas ativas.
     * @param hasAtivas - Flag que indica listar apenas ATIVAS.
     * @return
     */
    public List<DespesaFixaModel> listarDespesasFixas(boolean hasAtivas) 
    {

        Stream<DespesaFixaEntity> despesasFixas = DespesaFixaEntity
            .find("codigoTipoSituacaoDespesaFixa = ?1", TipoSituacaoEnum.Ativo.getCodigo()).stream();
        return despesasFixas.map(m -> MapperHelper.INSTANCE.toDespesaFixaModel(m)).collect(Collectors.toList());

    }

    /**
     * Recuperar despesa fixa pelo ID.
     * @param id - Identificador da despesa fixa.
     * @return
     */
    public DespesaFixaModel obterDespesaFixa(long id) {

        DespesaFixaEntity entity = DespesaFixaEntity.findById(id);
        return MapperHelper.INSTANCE.toDespesaFixaModel(entity);
        
    }

    /**
     * Registrar uma nova despesa fixa.
     * @param despesaFixa - Despesa fixa para registro.
     * @return
     * @throws ServiceException 
     */
    public DespesaFixaModel registrarDespesaFixa(DespesaFixaEdicaoModel despesaFixa) throws ServiceException {

        verificarRegras_DespesaFixa(despesaFixa);

        var entity = MapperHelper.INSTANCE.toDespesaFixaEntity(despesaFixa);
        entity.codigoTipoSituacaoDespesaFixa = TipoSituacaoEnum.Ativo.getCodigo(); //Ativa
        entity.idEntidade = EntidadeConst.IdEntidade; //Entidade fixa
        entity.persist();

        //Registrar histórico de despesa fixa...
        var historico = new HistoricoDespesaFixaEntity();
        historico.idDespesaFixa = entity.id;
        historico.valorHistoricoDespesaFixa = entity.valorDespesaFixa;
        historico.persist();

        var model = MapperHelper.INSTANCE.toDespesaFixaModel(entity);
        return model;
        
    }

    /**
     * Alteração das informações de uma despesa fixa pelo seu ID.
     * @param id - identificador da despesa fixa.
     * @param despesaFixa - informações para alteração da despesa fixa.
     * @param hasGravarHistorico - flag (indicador) que diz se precisa gravar histórico de alteração da despesa fixa.
     * @return
     * @throws ServiceException 
     */
    public DespesaFixaModel alterarDespesaFixa(long id, DespesaFixaEdicaoModel despesaFixa, boolean hasGravarHistorico) throws ServiceException {

        DespesaFixaEntity entity = DespesaFixaEntity.findById(id);
        if (entity == null) {
            throw new ServiceException("Despesa fixa não identificada pelo ID %s.".formatted(id));
        }
        
        //Atualizar a entity com base nas informações enviadas para atualização...

        if (despesaFixa.codigoTipoFormaLiquidacaoDespesaFixa != 0) {
            if (!TipoFormaLiquidacaoEnum.hasTipoFormaLiquidacao(despesaFixa.codigoTipoFormaLiquidacaoDespesaFixa)) {
                throw new ServiceException("Tipo Forma Liquidação (%s) não existente.".formatted(despesaFixa.codigoTipoFormaLiquidacaoDespesaFixa));
            }
            entity.codigoTipoFormaLiquidacaoDespesaFixa = despesaFixa.codigoTipoFormaLiquidacaoDespesaFixa;
        }

        if (despesaFixa.diaVencimentoDespesaFixa != 0) {
            if (despesaFixa.diaVencimentoDespesaFixa < 1 && despesaFixa.diaVencimentoDespesaFixa > 31) {
                throw new ServiceException("Dia de vencimento (%s) da despesa fixa inválido.".formatted(despesaFixa.diaVencimentoDespesaFixa));
            }
            entity.diaVencimentoDespesaFixa = despesaFixa.diaVencimentoDespesaFixa;
        }
            
        if (despesaFixa.idNaturezaContaDespesaFixa != 0) {
            //Verificar se a naturera informada na despesa fixa existe.
            verificarRegras_NaturezaConta(despesaFixa.idNaturezaContaDespesaFixa);
            entity.idNaturezaContaDespesaFixa = despesaFixa.idNaturezaContaDespesaFixa;
        }

        if (despesaFixa.descricaoDespesaFixa != null && !despesaFixa.descricaoDespesaFixa.trim().isEmpty()) {
            entity.descricaoDespesaFixa = despesaFixa.descricaoDespesaFixa;
        }
        
        if (despesaFixa.valorDespesaFixa != 0) {
            entity.valorDespesaFixa = BigDecimal.valueOf(despesaFixa.valorDespesaFixa);
        }
    
        if (hasGravarHistorico) {
            //Registrar histórico de despesa fixa...
            var historico = new HistoricoDespesaFixaEntity();
            historico.idDespesaFixa = entity.id;
            historico.valorHistoricoDespesaFixa = entity.valorDespesaFixa;
            historico.persist();
        }

        entity.persist();

        var model = MapperHelper.INSTANCE.toDespesaFixaModel(entity);
        return model;
        
    }

    /**
     * Verifica se para registro e alteração da despesa fixa, todas as regras foram atendidas.
     * @param despesa - Objeto para verificação;
     * @throws ServiceException 
     */
    private void verificarRegras_DespesaFixa(DespesaFixaEdicaoModel despesa) throws ServiceException {
        
        if (despesa == null) {
            throw new ServiceException("Despesa Fixa informada nula.");
        }
        if (despesa.descricaoDespesaFixa == null || despesa.descricaoDespesaFixa.trim().isEmpty()) {
            throw new ServiceException("Descrição da despesa fixa mensal não informada.");
        }
        if (despesa.diaVencimentoDespesaFixa == 0) {
            throw new ServiceException("Dia de vencimento da despesa fixa não informado.");
        }
        if (despesa.diaVencimentoDespesaFixa < 1 && despesa.diaVencimentoDespesaFixa > 31) {
            throw new ServiceException("Dia de vencimento (%s) da despesa fixa inválido.".formatted(despesa.diaVencimentoDespesaFixa));
        }
        if (despesa.idNaturezaContaDespesaFixa == 0) {
            throw new ServiceException("Natureza da despesa fixa não informada.");
        }
        if (despesa.valorDespesaFixa <= 0) {
            throw new ServiceException("Valor da despesa fixa não informado ou negativo.");
        }

        //Verificar se a naturera informada na despesa fixa existe.
        verificarRegras_NaturezaConta(despesa.idNaturezaContaDespesaFixa);

    }

    /**
     * Verificar a regra de Natureza de conta existente ou ativa.
     * @param idNaturezaConta - Identificação da natureza de conta.
     * @throws ServiceException
     */
    private void verificarRegras_NaturezaConta(int idNaturezaConta) throws ServiceException {

        NaturezaContaEntity naturezaEntity = NaturezaContaEntity.findById(idNaturezaConta);
        if (naturezaEntity == null) {
            throw new ServiceException("Natureza da Despesa Fixa (Id: %s) não existe ou está inativa.".formatted(idNaturezaConta));
        } else if (naturezaEntity.codigoTipoSituacaoNaturezaConta == TipoSituacaoEnum.Inativo.getCodigo()) {
            throw new ServiceException("Natureza de conta (Id: %s) está inativa não pode ser utilizada.".formatted(idNaturezaConta));
        }

    }

    /**
     * Exclusão de uma despesa fixa pelo seu ID, pode ser lógica, ser existir algum vínculo com as despesas mensais, 
     * caso não existe vínculo será uma exclusão física mesmo.
     * @param id - identificador da despesa fixa.
     * @return
     * @throws ServiceException 
     */
    public void excluirDespesaFixa(long id) throws ServiceException {

        DespesaFixaEntity entity = DespesaFixaEntity.findById(id);
        if (entity == null)
            throw new ServiceException("Despesa fixa não identificada pelo ID %s.".formatted(id));

        //Verificar se a despesas possuí vínculos para evitar exclusão física.
        var hasExclusaoFisica = verificarVinculosDespesaFixa(entity);
        if (hasExclusaoFisica) {
            HistoricoDespesaFixaEntity.delete("idDespesaFixa = ?1", id);
            entity.delete();
        } else {
            entity.codigoTipoSituacaoDespesaFixa = TipoSituacaoEnum.Inativo.getCodigo();
            entity.persist();
        }

    }

    /**
     * Verifica se existem vínculos com a despesa fixa.
     * @param despesaFixa - Despesa fixa carregada.
     * @return
     * @throws ServiceException 
     */
    private boolean verificarVinculosDespesaFixa(DespesaFixaEntity despesaFixa) throws ServiceException {
        
        if (despesaFixa == null) {
            throw new ServiceException("Despesa fixa nula, não será possível verificar seus vínculos.");
        }
        
        return false;
    }

}
