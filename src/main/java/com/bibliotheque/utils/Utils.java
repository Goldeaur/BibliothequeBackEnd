package com.muvraline.usermanager.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muvraline.commonlibrary.entities.usermanager.model.Status;
import com.muvraline.commonlibrary.entities.usermanager.model.header.ReqHeader;
import com.muvraline.commonlibrary.entities.usermanager.model.header.TenantHeader;
import com.muvraline.usermanager.exception.MalformedRequestException;
import com.muvraline.usermanager.model.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class Utils {


    public static String toEncodedAccountId(ReqHeader header) {
        return toEncoded(header.getTenant(), header.getOpCoAccountId());
    }

    public static String toEncodedAgentId(ReqHeader header, String opCoAgentId) {
        return toEncoded(header.getTenant(), opCoAgentId);
    }

    public static String toEncoded(TenantHeader tenant, String id) {
        return  toEncodedForPost(tenant.getOpCo(),tenant.getBrand(),id);
    }

    public static String toEncodedForPost(String opCo,String brand, String id) {
        String toBase64 = opCo + ":" + brand + ":" + id;
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] data = toBase64.getBytes(UTF_8);
        return encoder.encodeToString(data);
    }

    public static ReqHeader getHeader(String encodedString, ObjectMapper mapper) throws JsonProcessingException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(encodedString);
        String decodedString = new String(bytes, UTF_8);
        JsonNode decodedJson = mapper.readTree(decodedString);
        ReqHeader reqHeader = mapper.treeToValue(decodedJson, ReqHeader.class);
        return reqHeader;
    }

    public static Map<String,String> getTenantFromInternalAccountId(String encodedString) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(encodedString);
        String decodedString = new String(bytes, UTF_8);
        List<String> entries = Arrays.asList(decodedString.split("\\s*:\\s*"));
        if(entries.size()!=3)
            new MalformedRequestException("encoded entry is corrupted");
        Map<String, String> result = new HashMap<>();
        result.put("opco", entries.get(0));
        result.put("brand", entries.get(1));
        result.put("accountId", entries.get(2));
        return result;
    }


    public static String toRole(Profile profile) {
        if (profile.equals(Profile.OWNER)) return "owner";
        return "member";
    }

    public static Profile toProfile(String role) {
        if (role == null) return null;
        else if (role.toLowerCase(Locale.ROOT).equals("owner")) return Profile.OWNER;
        return Profile.MEMBER;
    }

    public static Status toStatus(String status) {
        if (status == null) return null;
        else if (status.toLowerCase(Locale.ROOT).equals("activated")) return Status.ACTIVATED;
        else if (status.toLowerCase(Locale.ROOT).equals("inactivated")) return Status.INACTIVATED;
        else if (status.toLowerCase(Locale.ROOT).equals("deleted")) return Status.DELETED;
        else if (status.toLowerCase(Locale.ROOT).equals("created")) return Status.CREATED;
        return Status.INACTIVATED;
    }


}
