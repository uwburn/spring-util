package it.mgt.util.spring.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
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
                throw new IllegalArgumentException("Unable to load params");
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

            if (methods.size() > 1)
                throw new IllegalArgumentException();

            Method method = methods.stream()
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);

            if (!method.getReturnType().isAssignableFrom(type))
                throw new IllegalArgumentException();

            Object[] arguments = Arrays.stream(method.getParameters())
                    .map(p -> readValue(params.get(p.getName()), p.getType()))
                    .toArray();

            result = method.invoke(bean, arguments);
        }
        catch(Exception e) {
            throw new BeanMethodDeserializerException(e);
        }
        
        if (single && result == null)
            throw new BeanMethodDeserializerException("Could not deserialize reference entity");
        
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
