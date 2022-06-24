package fel.cvut.order.config;

//import fel.cvut.order.interceptors.GeneralInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("http://localhost:3000").
                allowedMethods("GET").allowedMethods("POST").allowedMethods("DELETE");
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new GeneralInterceptor());
//    }
}
