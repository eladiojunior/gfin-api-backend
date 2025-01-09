package br.com.devd2.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.devd2.entites.NaturezaContaEntity;
import br.com.devd2.entites.DespesaFixaEntity;
import br.com.devd2.entites.DespesaMensalEntity;
import br.com.devd2.enums.TipoLancamentoEnum;
import br.com.devd2.enums.TipoSituacaoEnum;
import br.com.devd2.helper.MapperHelper;
import br.com.devd2.infra.ServiceException;
import br.com.devd2.models.NaturezaContaEdicaoModel;
import br.com.devd2.models.NaturezaContaModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NaturezaContaService {

    /**
     * Lista totas as naturezas de contas ativas.
     * @param hasAtivas - Flag que indica listar apenas ATIVAS.
     * @return
     */
    public List<NaturezaContaModel> listarNaturezasContas(boolean hasAtivas) 
    {

        Stream<NaturezaContaEntity> lista = NaturezaContaEntity
            .find("codigoTipoSituacaoNaturezaConta = ?1", TipoSituacaoEnum.Ativo.getCodigo()).stream();
        return lista.map(m -> MapperHelper.INSTANCE.toNaturezaContaModel(m)).collect(Collectors.toList());

    }

    /**
     * Recuperar natureza da conta pelo ID.
     * @param id - Identificador da natureza da conta.
     * @return
     */
    public NaturezaContaModel obterNaturezaConta(long id) {

        NaturezaContaEntity entity = NaturezaContaEntity.findById(id);
        return MapperHelper.INSTANCE.toNaturezaContaModel(entity);
        
    }

    /**
     * Registrar uma nova natureza de conta.
     * @param naturezaConta - Natureza da conta para registro.
     * @return
     * @throws ServiceException 
     */
    public NaturezaContaModel registrarNaturezaConta(NaturezaContaEdicaoModel naturezaConta) throws ServiceException {

        verificarRegrasNegocio(naturezaConta);

        var entity = MapperHelper.INSTANCE.toNaturezaContaEntity(naturezaConta);
        entity.codigoTipoSituacaoNaturezaConta = TipoSituacaoEnum.Ativo.getCodigo(); //Ativa
        entity.persist();

        var model = MapperHelper.INSTANCE.toNaturezaContaModel(entity);
        return model;
        
    }

    /**
     * Alteração das informações de uma natureza de conta pelo seu ID.
     * @param id - identificador da natureza de conta.
     * @param naturezaConta - informações para alteração da natureza.
     * @return
     * @throws ServiceException 
     */
    public NaturezaContaModel alterarNaturezaConta(long id, NaturezaContaEdicaoModel naturezaConta) throws ServiceException {

        NaturezaContaEntity entity = NaturezaContaEntity.findById(id);
        if (entity == null) {
            throw new ServiceException("Natureza da conta não identificada pelo ID %s.".formatted(id));
        }
        
        //Atualizar a entity com base nas informações enviadas para atualização...

        if (naturezaConta.codigoTipoLancamentoConta != 0) {
            if (!TipoLancamentoEnum.hasTipoLancamento(naturezaConta.codigoTipoLancamentoConta)) {
                throw new ServiceException("Tipo lançamento (%s) não existente.".formatted(naturezaConta.codigoTipoLancamentoConta));
            }
            entity.codigoTipoLancamentoConta = naturezaConta.codigoTipoLancamentoConta;
        }

        if (naturezaConta.descricaoNaturezaConta != null && !naturezaConta.descricaoNaturezaConta.trim().isEmpty()) {
            entity.descricaoNaturezaConta = naturezaConta.descricaoNaturezaConta;
        }
        
        entity.persist();

        var model = MapperHelper.INSTANCE.toNaturezaContaModel(entity);
        return model;
        
    }

    /**
     * Verifica se para registro e alteração da natureza de conta, todas as regras foram atendidas.
     * @param natureza - Objeto para verificação;
     * @throws ServiceException 
     */
    private void verificarRegrasNegocio(NaturezaContaEdicaoModel natureza) throws ServiceException {
        
        if (natureza == null) {
            throw new ServiceException("Natureza de conta informada nula.");
        }
        if (natureza.descricaoNaturezaConta == null || natureza.descricaoNaturezaConta.trim().isEmpty()) {
            throw new ServiceException("Descrição da natureza de conta não informada");
        }
        if (!TipoLancamentoEnum.hasTipoLancamento(natureza.codigoTipoLancamentoConta)) {
            throw new ServiceException("Tipo de lançamento inválido ou não informado.");
        }

    }

    /**
     * Exclusão de uma natureza de conta pelo seu ID, pode ser lógica, ser existir algum vínculo com a natureza, 
     * caso não existe vínculo será uma exclusão física mesmo.
     * @param id - identificador da natureza de conta.
     * @return
     * @throws ServiceException 
     */
    public void excluirNaturezaConta(long id) throws ServiceException {

        NaturezaContaEntity entity = NaturezaContaEntity.findById(id);
        if (entity == null)
            throw new ServiceException("Natureza de conta não identificada pelo ID %s.".formatted(id));

        //Verificar se a despesas possuí vínculos para evitar exclusão física.
        var hasExclusaoFisica = verificarVinculosNaturezaConta(entity);
        if (hasExclusaoFisica) {
            entity.codigoTipoSituacaoNaturezaConta = TipoSituacaoEnum.Inativo.getCodigo();
            entity.persist();
        } else {
            entity.delete();
        }

    }

    /**
     * Verifica se existem vínculos com a natureza de conta.
     * @param naturezaConta - Natureza de conta carregada.
     * @return
     * @throws ServiceException 
     */
    private boolean verificarVinculosNaturezaConta(NaturezaContaEntity naturezaConta) throws ServiceException {
        
        if (naturezaConta == null) {
            throw new ServiceException("Natureza de conta nula, não será possível verificar seus vínculos.");
        }
        
        //Verificar se existe vinculo com as despesas...
        var countDespesas = DespesaFixaEntity.count("idNaturezaContaDespesaFixa = ?1", naturezaConta.id);
        if (countDespesas > 0) {
            return true;
        }
        countDespesas = DespesaMensalEntity.count("idNaturezaContaDespesa = ?1", naturezaConta.id);
        if (countDespesas > 0) {
            return true;
        }
        
        return false;

    }

}
