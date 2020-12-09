package pl.dev.household.budget.manager.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuxConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
