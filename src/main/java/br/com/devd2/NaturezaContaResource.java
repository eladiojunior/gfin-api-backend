package br.com.devd2;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.devd2.helper.ResultApiHelper;
import br.com.devd2.infra.ServiceException;
import br.com.devd2.models.NaturezaContaEdicaoModel;
import br.com.devd2.services.NaturezaContaService;
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

@Path("/natureza-conta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Natureza de conta", description = "Operações de naturezas de contas vinculadas as despesas e receitas.")
public class NaturezaContaResource {
    private final Logger log = LoggerFactory.getLogger(NaturezaContaResource.class);

    @Inject
    NaturezaContaService naturezaContaService;

    @GET
    @APIResponse(responseCode = "200", description = "Lista as naturezas de contas (ativas) existentes.")
    @APIResponse(responseCode = "500", description = "Erro ao listar as naturezas de contas (ativas) existentes.")
    @Operation(summary = "Listar Naturezas de Conta", description = "Lista as naturezas de contas ativas, caso não exista será retornado uma lista vazia.")
    public Response listar_natureza() {
        
        try {

            var lista = naturezaContaService.listarNaturezasContas(true);
            return Response.ok(lista).build();
                
        } catch (Exception erro) {
            log.error("Erro ao listar as naturezas de conta ativas.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }
        
    }

    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Natureza de conta, pelo ID, recuperada com sucesso.")
    @APIResponse(responseCode = "500", description = "Erro ao recuperar uma natureza de conta pelo ID.")
    @Operation(summary = "Recuperar Natureza de Conta", description = "Recupera uma natureza de conta pelo seu ID, caso não exista será retornado um objeto vazio.")
    public Response obter_natureza(@PathParam("id") long id) {
        
        try {

            var result = naturezaContaService.obterNaturezaConta(id);
            return Response.ok(result).build();
                
        } catch (Exception erro) {
            log.error("Erro ao recuperar uma natureza de conta pelo ID.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }

        
    }
    
    @POST
    @Transactional
    @APIResponse(responseCode = "200", description = "Despesa fixa reistrada com sucesso.")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para registro de nova despesa fixa.")
    @APIResponse(responseCode = "500", description = "Erro ao registrar nova despesa fixa.")
    @Operation(summary = "Registar Natureza de Conta", description = "Registra uma nova natureza de conta.")
    public Response nova_natureza(NaturezaContaEdicaoModel model) {

        try {

            var result = naturezaContaService.registrarNaturezaConta(model);
            return Response.ok(result).build();
                
        } catch (ServiceException regra) {

            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();

        } catch (Exception erro) {
            log.error("Erro ao registrar uma natureza de conta.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

    @PUT
    @Path("/{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "Natureza de conta alterada com sucesso.")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para alterar natureza de conta.")
    @APIResponse(responseCode = "500", description = "Erro ao alterar natureza de conta.")
    @Operation(summary = "Alterar Natureza de Conta", description = "Edita uma natureza de conta pelo ID.")
    public Response editar_natureza(@PathParam("id") long id, NaturezaContaEdicaoModel model) {

        try {

            var result = naturezaContaService.alterarNaturezaConta(id, model);
            return Response.ok(result).build();
                
        } catch (ServiceException regra) {
            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();
        } catch (Exception erro) {
            log.error("Erro ao editar as informações da natureza de conta.", erro);
            return Response.serverError().entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @APIResponse(responseCode = "200", description = "Natureza de conta excluída com sucesso (logicamente ou física).")
    @APIResponse(responseCode = "400", description = "Regra de negócio não atendida para excluir natureza de conta.")
    @APIResponse(responseCode = "500", description = "Erro ao excluir natureza de conta.")
    @Operation(summary = "Excluir Natureza de Conta", description = "Remove uma natureza de conta pelo seu ID, logicamente (se tiver vínculos) ou fisicamente.")
    public Response excluir_natureza(@PathParam("id") long id) {

        try {

            naturezaContaService.excluirNaturezaConta(id);
            return Response.ok().build();
                
        } catch (ServiceException regra) {
            return Response.status(Status.BAD_REQUEST.getStatusCode())
                .entity(ResultApiHelper.toResponseRegra(regra)).build();
        } catch (Exception erro) {
            log.error("Erro ao excluir as informações da natureza de conta.", erro);
            return Response.serverError()
                .entity(ResultApiHelper.toResponseErro(erro)).build();
        }    

    }

}