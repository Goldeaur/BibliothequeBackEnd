package com.bibliotheque.utils;

import com.bibliotheque.model.HumanStatus;
import com.bibliotheque.model.Role;
import com.bibliotheque.model.RequestHeader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Utils {


    public static String toEncodedAdminAccountId(RequestHeader header) {
        return toEncoded(header.getNames(), header.getAdminId());
    }

    public static String toEncodedReaderId(RequestHeader header, String readerId) {
        return toEncoded(header.getNames(), readerId);
    }

    public static String toEncoded(RequestHeader.Names names, String id) {
        return  toEncodedForPost(names.getFirstName(), names.getLastName(),id);
    }

    public static String toEncodedForPost(String firstName,String lastName, String id) {
        String toBase64 = firstName + ":" + lastName + ":" + id;
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

    public static HumanStatus toStatus(String status) {
        if (status == null) return null;
        else if (status.toLowerCase(Locale.ROOT).equals("activated")) return HumanStatus.ACTIVATED;
        else if (status.toLowerCase(Locale.ROOT).equals("inactivated")) return HumanStatus.INACTIVATED;
        else if (status.toLowerCase(Locale.ROOT).equals("deleted")) return HumanStatus.DELETED;
        else if (status.toLowerCase(Locale.ROOT).equals("created")) return HumanStatus.CREATED;
        return HumanStatus.INACTIVATED;
    }


}
