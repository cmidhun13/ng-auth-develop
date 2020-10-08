package com.szells.gce.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateHashRequest {
    @JsonProperty("member_id")
    public Long memberid;
    @JsonProperty("hash_cd")
    public String hashCd;
    public Boolean delete_fl;
}
