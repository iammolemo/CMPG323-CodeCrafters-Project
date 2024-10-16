package com.S2T.Share_2_Teach.dto;

import lombok.Builder;

@Builder
public record Mailbody(String to, String subject, String text) {

}
