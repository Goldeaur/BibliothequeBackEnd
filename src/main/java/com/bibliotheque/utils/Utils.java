package com.bibliotheque.utils;

import com.bibliotheque.model.Role;
import com.bibliotheque.model.RequestHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Base64;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class Utils {
    public static ZoneId getZoneId(){
        return ZoneId.of("Europe/Paris");
    }

    public static String toEncodedAdminAccountId(RequestHeader header) {
        return toEncoded(header.getNames(), header.getAdminId());
    }

    public static String toEncodedReaderId(RequestHeader header, String readerId) {
        return toEncoded(header.getNames(), readerId);
    }

    public static String toEncoded(RequestHeader.Names names, String id) {
        return  toEncodedForPost(names.getFirstname(), names.getLastname(),id);
    }

    private static String toEncodedForPost(String firstname,String lastname, String id) {
        String toBase64 = firstname + ":" + lastname + ":" + id;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] data = toBase64.getBytes(UTF_8);
        return encoder.encodeToString(data);
    }

    public static RequestHeader getHeader(String encodedString, ObjectMapper mapper) throws JsonProcessingException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(encodedString);
        String decodedString = new String(bytes, UTF_8);
        JsonNode decodedJson = mapper.readTree(decodedString);
        RequestHeader reqHeader = mapper.treeToValue(decodedJson, RequestHeader.class);
        return reqHeader;
    }

    public static String toRole(Role profile) {
        if (profile.equals(Role.ADMIN)) return "admin";
        return "member";
    }

    public static Role toProfile(String role) {
        if (role == null) return null;
        else if (role.toLowerCase(Locale.ROOT).equals("admin")) return Role.ADMIN;
        return Role.READER;
    }


}
