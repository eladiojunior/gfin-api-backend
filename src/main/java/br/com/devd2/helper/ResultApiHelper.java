package br.com.devd2.helper;

import br.com.devd2.infra.ServiceException;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class ResultApiHelper {

    /**
     * Converte uma exception de erro (não identificada) em response da API para o usuário.
     * @param erro - Exception para extração da mensagem e envio para o usuário.
     * @return
     */
    public static String toResponseErro(Exception erro) {
        JsonObject json = Json.createObjectBuilder()
            .add("message", erro.getLocalizedMessage()).build();
        return json.toString();
    }

    /**
     * Converte uma exception de negócio (ServiceException) em respose da API para o usuário.
     * @param regra - Exception para extração da mensagem de negócio e envio para o usuário.
     * @return
     */
    public static Object toResponseRegra(ServiceException regra) {
        JsonObject json = Json.createObjectBuilder()
            .add("message", regra.getMessage()).build();
        return json.toString();
    }
    
}
