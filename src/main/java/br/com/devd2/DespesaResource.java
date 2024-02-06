package br.com.devd2;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devd2.helper.ResultApiHelper;
import br.com.devd2.infra.ServiceException;
import br.com.devd2.models.DespesaFixaEdicaoModel;
import br.com.devd2.services.DespesaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/despesas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Despesas", description = "Operações de despesas fixas e mensal para o gerenciador financeiro.")
public class DespesaResource {
    private final Logger log = LoggerFactory.getLogger(DespesaResource.class);

    @Inject
    DespesaService despesaService;

    @GET
    @Path("/fixas")
    @APIResponse(responseCode = "200", description = "Lista as despesas fixas (ativas) existentes.")
    @APIResponse(responseCode = "500", description = "Erro ao listar as despesas fixas (ativas) existentes.")
    @Operation(summary = "Listar despesas fixas", description = "Listar as despesas fixas ativas, caso não exista será retornado uma lista vazia.")
    public Response listar_despesas_fixas() {
        
        try {

            var lista = despesaService.listarDespesasFixas(true);
            return Response.ok(lista).build();
            
        } catch (Exception erro) {
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }
        
    }

    @GET
    @Path("/fixas/{id}")
    @APIResponse(responseCode = "200", description = "Despesas fixas, pelo ID, recuperada com sucesso.")
    @APIResponse(responseCode = "500", description = "Erro ao recuperar uma despesa fixa pelo ID.")
    @Operation(summary = "Recupera despesa fixa", description = "Recupera despesa fixa pelo seu ID, caso não exista será retornado objeto vazio.")
    public Response obter_despesa_fixa(@PathParam("id") long id) {
        
        try {

            var despesa = despesaService.obterDespesaFixa(id);
            return Response.ok(despesa).build();
            
        } catch (Exception erro) {
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }
        
    }
    
    @POST
    @Path("/fixas")
    @Transactional
    @APIResponse(responseCode = "200", description = "Despesa fixa reistrada com sucesso.")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para registro de nova despesa fixa.")
    @APIResponse(responseCode = "500", description = "Erro ao registrar nova despesa fixa.")
    @Operation(summary = "Registrar despesa fixa", description = "Registra uma nova despesa fixa.")
    public Response nova_despesa_fixa(DespesaFixaEdicaoModel despesaFixa) {

        try {

            var despesaRegistrada = despesaService.registrarDespesaFixa(despesaFixa);
            return Response.ok(despesaRegistrada).build();
                
        } catch (ServiceException regra) {
            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();
        } catch (Exception erro) {
            log.error("Erro ao registrar uma despesa fixa.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

    @PUT
    @Path("/fixas/{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "Despesa fixa alterada com sucesso.")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para alterar despesa fixa.")
    @APIResponse(responseCode = "500", description = "Erro ao alterar despesa fixa.")
    @Operation(summary = "Alterar despesa fixa", description = "Editar uma despesa fixa pelo seu ID.")
    public Response editar_despesa_fixa(@PathParam("id") long id, DespesaFixaEdicaoModel despesaFixa) {

        try {

            var despesaAlterada = despesaService.alterarDespesaFixa(id, despesaFixa, false);
            return Response.ok(despesaAlterada).build();
                
        } catch (ServiceException regra) {
            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();
        } catch (Exception erro) {
            log.error("Erro ao editar as informações da despesa fixa.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

    @DELETE
    @Path("/fixas/{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "Despesa fixa excluída com sucesso (logicamente ou física).")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para excluir despesa fixa.")
    @APIResponse(responseCode = "500", description = "Erro ao excluir despesa fixa.")
    @Operation(summary = "Excluir despesa fixa", description = "Remove uma despesa fixa pelo seu ID, logicamente (se tiver vínculos) ou fisicamente.")
    public Response excluir_despesa_fixa(@PathParam("id") long id) {

        try {

            despesaService.excluirDespesaFixa(id);
            return Response.ok().build();
                
        } catch (ServiceException regra) {
            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();
        } catch (Exception erro) {
            log.error("Erro ao excluir as informações da despesa fixa.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

}