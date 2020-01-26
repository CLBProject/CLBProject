package business.configuration;

import static org.mockito.Mockito.mock;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import clb.business.integration.FtpGateway;
import clb.business.integration.FtpGatewayMget;
import clb.business.integration.FtpGatewayPut;
import clb.business.integration.FtpGatewayRm;
import clb.database.ClbDao;

@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@EnableMongoRepositories("clb.database.repository")
@ComponentScan({ // where the components are
		"clb.business",
		"clb.database"})
public class ConfigurationBusiness {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ModelMapper modelMapper() {
		return mock(ModelMapper.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ClbDao clbDao() {
		return mock(ClbDao.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public FtpGateway ftpGateWay() {
		return mock(FtpGateway.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public FtpGatewayPut ftpGateWayPut() {
		return mock(FtpGatewayPut.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public FtpGatewayRm ftpGateWayRm() {
		return mock(FtpGatewayRm.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public FtpGatewayMget ftpGateWayMget() {
		return mock(FtpGatewayMget.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public ThreadPoolTaskExecutor taskExecutor() {
		return mock(ThreadPoolTaskExecutor.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public MongoTemplate mongoTemplate() {
		MongoDbFactory factory = mock(MongoDbFactory.class);
		return new MongoTemplate(factory);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return mock(BCryptPasswordEncoder.class);
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JavaMailSender javaMailSender() {
		return mock(JavaMailSender.class);
	}
}
