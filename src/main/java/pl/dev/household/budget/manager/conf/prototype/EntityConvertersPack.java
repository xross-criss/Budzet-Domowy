package pl.dev.household.budget.manager.conf.prototype;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public interface EntityConvertersPack {

    List<AbstractConverter> getFullEntityConvertersPack();

    List<PropertyMap> getFullEntityConverterPropertyMapPack();

    ModelMapper getPreparedModelMapper();

}
