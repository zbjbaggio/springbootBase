package com.springboot.base.conf;

import com.springboot.base.interceptor.AuthenticationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author eason
 *
 */
@RestController
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.springboot.base")
@MapperScan("com.springboot.base.mapper")
@Configuration
public class Application extends WebMvcConfigurerAdapter {

	@Autowired
    private AuthenticationInterceptor authenticationInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			// 跨域解决方案
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(), HttpMethod.OPTIONS.name());
			}
		};
	}

	@RequestMapping("/home")
	String home() {
        return "Hello World!";
	}

    @Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.CHINA);
		return slr;
	}

	@Bean
	public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
		LocalValidatorFactoryBean lfb = new LocalValidatorFactoryBean();
		lfb.setValidationMessageSource(messageSource);
		return lfb;

	}

	//拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor).addPathPatterns("/manage/user/**");
	}

/*	@Bean
	public RequestMappingHandlerAdapter handerMVC(){
		RequestMappingHandlerAdapter adapte = new RequestMappingHandlerAdapter();
		return adapte;
	}*/
}
