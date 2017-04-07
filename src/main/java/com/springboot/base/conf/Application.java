package com.springboot.base.conf;

import com.springboot.base.data.entity.UserInfo;
import com.springboot.base.interceptor.AuthenticationInterceptor;
import com.springboot.base.mapper.UserInfoMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.activation.DataSource;
import javax.annotation.Resource;
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
public class Application extends WebMvcConfigurerAdapter {

	@Inject
    private AuthenticationInterceptor authenticationInterceptor;

	@Inject
	private UserInfoMapper userInfoMapper;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	@RequestMapping("/home")
	String home() {
        UserInfo userInfo = userInfoMapper.getUserInfo();
        List<UserInfo> all = userInfoMapper.getAll();
        return "Hello World!" + userInfo.toString() + " ------" + all.toString() ;
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
		registry.addInterceptor(authenticationInterceptor).addPathPatterns("/user/**");
	}

/*	@Bean
	public RequestMappingHandlerAdapter handerMVC(){
		RequestMappingHandlerAdapter adapte = new RequestMappingHandlerAdapter();
		return adapte;
	}*/
}
