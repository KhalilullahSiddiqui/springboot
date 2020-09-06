package com.rest.api.jpa.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }
    private static final springfox.documentation.service.Contact DEFAULT_CONTACT = new Contact("Khalilullah","www.khalil.com","khalil@gmail.com");
    private static final ApiInfo DEFAULT_API_INFO =new ApiInfo("API: Users with Posts",
            "It is an API where user can be created and to its corresponding a post, with basic AUth Security (Username=username,Password=password)",
            "1.0","urn:tos",DEFAULT_CONTACT,"Apache 2.0"
            ,"http://www.apache.org/licences/LICENCE-2.0") ;
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =new HashSet<String>(Arrays.asList("application/json",
            "application/xml"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }


}
