package todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 標明是配置類
@EnableSwagger2 //開啟swagger功能
public class SwaggerConfig {
 @Bean
 public Docket createRestApi() {
  return new Docket(DocumentationType.SWAGGER_2) // DocumentationType.SWAGGER_2 固定的，代表swagger2
//    .groupName("分散式任務系統") // 如果配置多個文件的時候，那麼需要配置groupName來分組標識
    .apiInfo(apiInfo()) // 用於生成API資訊
    .select() // select()函式返回一個ApiSelectorBuilder例項,用來控制介面被swagger做成文件
    .apis(RequestHandlerSelectors.basePackage("todolist.controller")) // 用於指定掃描哪個包下的介面
    .paths(PathSelectors.any())// 選擇所有的API,如果你想只為部分API生成文件，可以配置這裡
    .build();
 }

 /**
  * 用於定義API主介面的資訊，比如可以宣告所有的API的總標題、描述、版本
  * @return
  */
 private ApiInfo apiInfo() {
  return new ApiInfoBuilder()
    .title("XX專案API") // 可以用來自定義API的主標題
    .description("XX專案SwaggerAPI管理") // 可以用來描述整體的API
    .termsOfServiceUrl("") // 用於定義服務的域名
    .version("1.0") // 可以用來定義版本。
    .build(); //
 }

}
