package assgiment.ass;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MesageConfig {
    @Bean("messageSource")
    public MessageSource getMessageSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:i18n/layout");
        ms.setDefaultEncoding("utf-8");
        return ms;
    }
}
