package pl.dev.household.budget.manager.conf;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.dev.household.budget.manager.conf.prototype.EntityConverter;
import pl.dev.household.budget.manager.conf.prototype.EntityConverterPropertyMap;
import pl.dev.household.budget.manager.conf.prototype.EntityConvertersPack;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class BaseEntityConvertersPack implements EntityConvertersPack {

    private final List<AbstractConverter> abstractConverterList;
    private final List<PropertyMap> propertyMapList;
    private final ModelMapper modelMapper;

    @Autowired
    public BaseEntityConvertersPack(ApplicationContext applicationContext) {
        abstractConverterList = new ArrayList<>();
        propertyMapList = new ArrayList<>();

        addToList(applicationContext, EntityConverter.class, abstractConverterList, AbstractConverter.class);
        addToList(applicationContext, EntityConverterPropertyMap.class, propertyMapList, PropertyMap.class);

        modelMapper = new ModelMapper();
        getFullEntityConvertersPack().forEach(
                modelMapper::addConverter
        );
        getFullEntityConverterPropertyMapPack().forEach(
                modelMapper::addMappings
        );
    }

    private static <T> void addToList(ApplicationContext applicationContext, Class<? extends Annotation> aClass, List<T> list, Class<T> clazz) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(aClass);
        for (Object o : beans.values()) {
            if (clazz.isInstance(o)) {
                list.add(clazz.cast(o));
            } else {
                throw new ClassCastException("Unable to cast " + o.getClass() + " to " + clazz);
            }
        }
    }

    @Override
    public ModelMapper getPreparedModelMapper() {
        return modelMapper;
    }

    @Bean
    public ModelMapper getModelMapper() {
        return getPreparedModelMapper();
    }

    @Override
    public List<AbstractConverter> getFullEntityConvertersPack() {
        return abstractConverterList;
    }

    @Override
    public List<PropertyMap> getFullEntityConverterPropertyMapPack() {
        return propertyMapList;
    }

}
