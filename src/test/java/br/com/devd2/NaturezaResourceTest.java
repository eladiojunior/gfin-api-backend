package br.com.devd2;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import br.com.devd2.enums.TipoLancamentoEnum;
import br.com.devd2.models.NaturezaContaEdicaoModel;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class NaturezaResourceTest {
    private final String path_version = "/api/v1";

    @Test
    @Order(1)
    void testListaNaturezasApi() {
        var result = given()
            .when()
                .get(path_version+"/natureza-conta")
            .then()
                .statusCode(200)
                .extract().asString();
        Log.info(result);
        Assert.assertNotNull(result);
    }

    @Test
    @Order(2)
    void testObterNaturezaApi() {
        var idObter = 1;
        var result = given()
            .when()
                .get(path_version+"/natureza-conta/" + idObter)
            .then()
                .statusCode(200)
                .extract().asString();

        Log.info(result);
        Assert.assertNotNull(result);
    }

    @Test 
    @Order(3)
    void testRegistrarNaturezaApi() {

        NaturezaContaEdicaoModel model = new NaturezaContaEdicaoModel();
        model.codigoTipoLancamentoConta = TipoLancamentoEnum.Despesa.getCodigo();
        model.descricaoNaturezaConta = "Nova natureza de conta - despesa";
        
        var result = given()
                .body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                    .post(path_version+"/natureza-conta")
                .then()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().asString();
        
        Log.info(result);
        Assert.assertNotNull(result);

    }

    @Test
    void testAlterarNaturezaApi() {

        var idEdicao = 1;
        NaturezaContaEdicaoModel model = new NaturezaContaEdicaoModel();
        model.codigoTipoLancamentoConta = TipoLancamentoEnum.Receita.getCodigo();
        model.descricaoNaturezaConta = "Alterar natureza de conta - receita";

        var result = given().body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                    .put(path_version+"/natureza-conta/" + idEdicao)
                .then()
                    .statusCode(Status.OK.getStatusCode())
                    .extract().asString();

        Log.info(result);
        Assert.assertNotNull(result);

    }

    @Test
    void testAlterarNatureza_NaoExistente_Api() {

        var idEdicao = 9999;
        NaturezaContaEdicaoModel model = new NaturezaContaEdicaoModel();
        model.codigoTipoLancamentoConta = TipoLancamentoEnum.Receita.getCodigo();
        model.descricaoNaturezaConta = "Alterar natureza de conta - receita";

        var result = given().body(model)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().put(path_version+"/natureza-conta/" + idEdicao)
                .then().statusCode(Status.BAD_REQUEST.getStatusCode())
                .extract().asString();
        
        Log.info(result);
        Assert.assertNotNull(result);

    }

    @Test
    void testExcluirNatureza_ComVinculo_Api() {

        var idRemover = 2;
        var result = given()
                .when().delete(path_version+"/natureza-conta/" + idRemover)
                .then().statusCode(Status.OK.getStatusCode())
                .extract().asString();
        
        Log.info(result);
        Assert.assertNotNull(result);

    }

    @Test
    void testExcluirNatureza_SemVinculo_Api() {

        var idRemover = 3;
        var result = given()
                .when().delete(path_version+"/natureza-conta/" + idRemover)
                .then().statusCode(Status.OK.getStatusCode())
                .extract().asString();
        
        Log.info(result);
        Assert.assertNotNull(result);

    }

    @Test
    void testExcluirNatureza_NaoExistente_Api() {

        var idRemover = 9999;
        var result = given()
                .when()
                    .delete(path_version+"/natureza-conta/" + idRemover)
                .then()
                    .statusCode(Status.BAD_REQUEST.getStatusCode())
                    .extract().asString();
        
        Log.info(result);
        Assert.assertNotNull(result);

    }

}