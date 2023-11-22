package com.example.trip.member.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
public class MemberListDto {
	public String Id;
	public String userId;
	public String userNickname;
}
