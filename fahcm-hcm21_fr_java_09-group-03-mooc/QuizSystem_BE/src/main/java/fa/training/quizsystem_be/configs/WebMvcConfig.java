package fa.training.quizsystem_be.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fa.training.quizsystem_be.paging.PagingAndSortingArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowCredentials(false)
               .allowedMethods("POST","GET","DELETE","PUT","OPTIONS")
               .allowedOrigins("*");
    }
    
    @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PagingAndSortingArgumentResolver());
	}
}
