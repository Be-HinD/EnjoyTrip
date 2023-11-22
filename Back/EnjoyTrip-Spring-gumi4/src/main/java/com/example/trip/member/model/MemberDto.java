package com.example.trip.member.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
	public String Id;
	public String userId;
	public String userName;
	public String userPwd;
	public String userNickname;
	public String userGender;
	public String userBirthday;
	public String joinDate;
}
