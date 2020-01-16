package com.jit.aquaculture;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<springfox.documentation.service.Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization")
                .description("登录校验")//name表示名称，description表示描述
                .modelRef(new ModelRef("string"))
                .parameterType("header").required(false)
                .build();//required表示是否必填，defaultvalue表示默认值
        pars.add(ticketPar.build());//添加完此处一定要把下边的带***的也加上否则不生效

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jit.aquaculture"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
//                .globalOperationParameters(pars)
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
                .apiInfo(apiInfo());
    }
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> contextList = new ArrayList<>();
        contextList.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return contextList;
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> referenceList = new ArrayList<>();
        referenceList.add(new SecurityReference("Authorization", authorizationScopes));
        return referenceList;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("水产养殖管理系统的Restful APIs")
                .description("水产养殖管理系统前后端交互接口文档")
                .termsOfServiceUrl("http://192.168.100.56:8009/")
                .version("1.0")
                .build();
    }

}