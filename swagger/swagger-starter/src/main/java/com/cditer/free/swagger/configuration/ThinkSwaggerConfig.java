package com.cditer.free.swagger.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-20 08:53
 * @comment
 */
@Configurable
@Data
public class ThinkSwaggerConfig {

    @Value("${swagger.enable:true}")
    boolean enable;

    @Value("${swagger.title:API查看器}")
    private String title;

    @Value("${swagger.description:API服务的说明，请在配置文件中说明服务的作用}")
    private String description;

    @Value("${swagger.contact.name:chfree}")
    private String contactName;

    @Value("${swagger.contact.url:www.tennetcn.cn}")
    private String contactUrl;

    @Value("${swagger.contact.mail:chfree001@gmail.com}")
    private String contactMail;

    @Value("${swagger.version:0.0.1}")
    private  String version;

    public ThinkSwaggerConfig() {
    }

    @Bean
    public Docket allApi() {
        Docket docket = null;
        if (!this.enable) {
            docket = (new Docket(DocumentationType.SWAGGER_2)).select().apis(RequestHandlerSelectors.none()).paths(PathSelectors.none()).build();
        } else {
            ApiInfo apiInfo = (new ApiInfoBuilder()).title(this.title).description(this.description).contact(new Contact(this.contactName, this.contactUrl, this.contactMail)).version(this.version).build();
            ApiSelectorBuilder builder = (new Docket(DocumentationType.SWAGGER_2)).useDefaultResponseMessages(false).apiInfo(apiInfo).select();
            docket = builder.build();
        }
        docket.enable(enable);
        return docket;
    }
}
