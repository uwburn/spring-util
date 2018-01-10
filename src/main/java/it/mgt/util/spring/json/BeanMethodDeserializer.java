package it.mgt.util.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BeanMethodDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeanMethodDeserializer.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;
    
    private Class<?> type;
    private boolean single;
    private BeanMethod ann;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext dc, BeanProperty bp) throws JsonMappingException {
        ann = bp.getAnnotation(BeanMethod.class);
        type = bp.getType().getRawClass();
        if (Collection.class.isAssignableFrom(bp.getType().getRawClass())) {
            single = false;
        }
        else {
            single = true;
        }
        
        return this;
    }
    
    @Override
    public Object deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        Object result;
        
        try {
            if (!parser.getCurrentToken().equals(JsonToken.START_OBJECT)) {
                LOGGER.warn("An object containing method parameters was expected");
                throw new BeanMethodDeserializerException("Unable to load params");
            }

            JsonNode params = parser.readValueAsTree();

            Object bean = applicationContext.getBean(ann.bean());

            List<Method> methods = Arrays.stream(ann.bean().getMethods())
                    .filter(m -> m.getName().equals(ann.method()))
                    .filter(m -> m.getParameters().length == params.size())
                    .filter(m -> {
                        for (Parameter p : m.getParameters()) {
                            if (params.get(p.getName()) == null)
                                return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());

            if (methods.size() == 0) {
                LOGGER.warn("No method found with name " + ann.method() + " and given parameters");
                throw new BeanMethodDeserializerException("No method found");
            }
            if (methods.size() > 1) {
                LOGGER.warn("Multiple methods found with name " + ann.method() + " and given parameters");
                throw new BeanMethodDeserializerException("Multiple methods found");
            }

            Method method = methods.stream()
                    .findFirst()
                    .get();

            if (!method.getReturnType().isAssignableFrom(type)) {
                LOGGER.warn("Method return type (" + method.getReturnType() + ") is incompatible with parameter type (" + type + ")");
                throw new BeanMethodDeserializerException("Incompatible method return type");
            }

            Object[] arguments = Arrays.stream(method.getParameters())
                    .map(p -> readValue(params.get(p.getName()), p.getType()))
                    .toArray();

            result = method.invoke(bean, arguments);
        }
        catch (BeanMethodDeserializerException e) {
            throw e;
        }
        catch(Exception e) {
            LOGGER.warn("Deserialization failed", e);
            throw new BeanMethodDeserializerException(e);
        }
        
        if (single && result == null) {
            LOGGER.warn("Single non-null result was expected, but null value was resolved");
            throw new BeanMethodDeserializerException("Could not deserialize reference entity");
        }
        
        return result;
    }

    private Object readValue(JsonNode jsonNode, Class<?> type) {
        try {
            return objectMapper.treeToValue(jsonNode, type);
        }
        catch (IOException e) {
            return jsonNode.asText();
        }
    }

}
