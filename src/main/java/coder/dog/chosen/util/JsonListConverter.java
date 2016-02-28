package coder.dog.chosen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by megrez on 16/2/27.
 */
@Converter
public class JsonListConverter implements AttributeConverter<List<String>,String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String emptyString = "";

    @Override
    @NotNull
    public String convertToDatabaseColumn(@NotNull List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException ex) {
            return emptyString;
        }
    }

    @Override
    @NotNull
    public List<String> convertToEntityAttribute(@NotNull String dbData) {
        try {
            return (List<String>) objectMapper.readValue(dbData,List.class);
        } catch (Exception ex) {
            return new ArrayList<String>();
        }
    }
}
