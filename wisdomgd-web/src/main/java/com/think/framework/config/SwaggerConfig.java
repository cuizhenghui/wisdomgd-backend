package com.think.framework.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2的接口配置
 *
 * @author Gary
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {
    /**
     * 系统基础配置
     */
    @Autowired
    private ThinkConfig ruoyiConfig;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.system.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
                /* 设置安全模式，swagger可以设置访问token */
                //.securitySchemes(securitySchemes())
                //.securityContexts(securityContexts());
    }

    @Bean
    public Docket about_wx_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(wxInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.system.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/wechat/**"))
                //.paths(PathSelectors.ant("/wx/**"))
                .build()
                .groupName("微信相关模块");
                /* 设置安全模式，swagger可以设置访问token */
                //.securitySchemes(securitySchemes())
                //.securityContexts(securityContexts());
    }

    @Bean
    public Docket about_user_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.system.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/system/user/**"))
                //.paths(PathSelectors.ant("/system/role/**"))
                .build()
                .groupName("用户管理模块");
                /* 设置安全模式，swagger可以设置访问token */
                //.securitySchemes(securitySchemes())
                //.securityContexts(securityContexts());
    }

    @Bean
    public Docket business_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(wxInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.basePackage("com.think.business.controller"))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.system.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/wx/**"))
                .build()
                .groupName("业务相关模块");
                /* 设置安全模式，swagger可以设置访问token */
        //.securitySchemes(securitySchemes())
        //.securityContexts(securityContexts());
    }

    @Bean
    public Docket system_api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(wxInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.basePackage("com.think.system.system.controller"))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.system.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/wx/**"))
                .build()
                .groupName("后台系统相关模块");
                /* 设置安全模式，swagger可以设置访问token */
        //.securitySchemes(securitySchemes())
        //.securityContexts(securityContexts());
    }
    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo wxInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("微信管理系统")
                // 描述
                .description("用于管理微信模块的相关接口说明")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                //组织链接
                // .termsOfServiceUrl("http://terms.service.url")
                // 许可
                //.license("Apach 2.0 许可")
                // 许可连接
                //.licenseUrl()
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：若依管理系统_接口文档")
                // 描述
                .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                // 作者信息
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // 版本
                .version("版本号:" + ruoyiConfig.getVersion())
                //组织链接
                .termsOfServiceUrl("http://terms.service.url")
                // 许可
                .license("Apach 2.0 许可")
                // 许可连接
                //.licenseUrl()
                .build();
    }
}
