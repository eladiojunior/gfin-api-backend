package br.com.devd2;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(
    title = "API do GFin (Gerenciador Financeiro) em Quarkus",
    description = "Concentra todas as operações de backend do gerenciador financeiro pessoal.",
    version = "1.0.0")
)
@ApplicationPath("/api/v1")
public class GfinAplication extends Application {   
}