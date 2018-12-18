package com.springboot.base.conf;

import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.vo.OrderVO;
import com.springboot.base.interceptor.AuthenticationInterceptor;
import com.springboot.base.mapper.RoleMapper;
import com.springboot.base.mapper.TOrderMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.inject.Inject;
import java.util.List;
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
public class Application extends WebMvcConfigurationSupport {

	@Inject
    private AuthenticationInterceptor authenticationInterceptor;

	@Inject
	private TOrderMapper tOrderMapper;

	@Inject
    private RoleMapper roleMapper;

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
/*		OrderInfo order = new OrderInfo();
		order.setPostageId(7L);
		order.setEmail("11111");
		tOrderMapper.save(order);
		order.setPostageId(8L);
		tOrderMapper.save(order);*/
        roleMapper.listAllByUserId(1L);
        List<OrderVO> select = tOrderMapper.select();
        select.forEach(x -> System.out.println(x.getPostageId()));
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
