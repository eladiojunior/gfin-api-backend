package br.com.devd2;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import br.com.devd2.models.DespesaFixaEdicaoModel;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class DespesaResourceTest {
    private final String path_version = "/api/v1";
    @Test
    void testListaDespesasFixasApi() {
        var result = given()
            .when()
                .get(path_version+"/despesas/fixas")
            .then()
                .statusCode(200)
                .extract().asString();

        Assert.assertNotNull(result);
    }

    @Test
    void testObterDespesaFixaApi() {
        var idObter = 2;
        var result = given()
            .when()
                .get(path_version+"/despesas/fixas/" + idObter)
            .then()
                .statusCode(200)
                .extract().asString();

        Assert.assertNotNull(result);
    }

    @Test
    void testRegistrarDespesaFixaApi() {

        DespesaFixaEdicaoModel model = new DespesaFixaEdicaoModel();
        model.codigoTipoFormaLiquidacaoDespesaFixa = 1;
        model.diaVencimentoDespesaFixa = 5;
        model.idNaturezaContaDespesaFixa = 2;
        model.descricaoDespesaFixa = "Nova despesa fixa.";
        model.valorDespesaFixa = 123.91;
        
        var result = given()
                .body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                    .post(path_version+"/despesas/fixas")
                .then()
                    .statusCode(Status.OK.getStatusCode());

        Assert.assertNotNull(result);

    }

    @Test
    void testAlterarDespesaFixaApi() {

        var idEdicao = 2;
        DespesaFixaEdicaoModel model = new DespesaFixaEdicaoModel();
        model.codigoTipoFormaLiquidacaoDespesaFixa = 1;
        model.diaVencimentoDespesaFixa = 5;
        model.idNaturezaContaDespesaFixa = 2;
        model.valorDespesaFixa = 1.99;
        model.descricaoDespesaFixa = "Alterar despesa fixa 3.";

        var result = given()
                .body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                    .put(path_version+"/despesas/fixas/" + idEdicao)
                .then()
                    .statusCode(Status.OK.getStatusCode());
                    
        Assert.assertNotNull(result);

    }

    @Test
    void testAlterarDespesaFixa_NaoExistente_Api() {

        var idEdicao = 9999;
        DespesaFixaEdicaoModel model = new DespesaFixaEdicaoModel();
        model.codigoTipoFormaLiquidacaoDespesaFixa = 1;
        model.diaVencimentoDespesaFixa = 5;
        model.idNaturezaContaDespesaFixa = 2;
        model.descricaoDespesaFixa = "Alterar despesa fixa 3.";

        var result = given()
                .body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                    .put(path_version+"/despesas/fixas/" + idEdicao)
                .then()
                    .statusCode(Status.BAD_REQUEST.getStatusCode());
                    
        Assert.assertNotNull(result);

    }

    @Test
    void testExcluirDespesaFixaApi() {

        var idRemover = 2;
        var result = given()
                .when()
                    .delete(path_version+"/despesas/fixas/" + idRemover)
                .then()
                    .statusCode(Status.OK.getStatusCode());

        Assert.assertNotNull(result);

    }

    @Test
    void testExcluirDespesaFixa_NaoExistente_Api() {

        var idRemover = 9999;
        var result = given()
                .when()
                    .delete(path_version+"/despesas/fixas/" + idRemover)
                .then()
                    .statusCode(Status.BAD_REQUEST.getStatusCode());

        Assert.assertNotNull(result);

    }

}