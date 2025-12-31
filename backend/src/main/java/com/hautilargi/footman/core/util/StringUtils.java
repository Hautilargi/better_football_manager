package com.hautilargi.footman.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class StringUtils {

        public static String escapeHtml(String s) {
                if (s == null)
                        return "";
                return s.replace("&", "&amp;")
                                .replace("<", "&lt;")
                                .replace(">", "&gt;")
                                .replace("\"", "&quot;")
                                .replace("'", "&#39;");
        }

        public static String escapeJson(String s) {
                if (s == null)
                        return "";
                return s.replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", "\\n")
                                .replace("\r", "\\r")
                                .replace("\t", "\\t");
        }

        public static String toJson(Object object, Class view) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                try {
                        return mapper.writerWithView(view).writeValueAsString(object);
                } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to serialize Match to JSON", e);
                }
        }

        public static String toJson(Object object) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                try {
                        return mapper.writeValueAsString(object);
                } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to serialize Match to JSON", e);
                }
        }
}
