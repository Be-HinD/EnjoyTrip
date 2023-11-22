package com.example.trip.member.service;

import java.sql.SQLException;
import java.util.List;

import com.example.trip.member.model.MemberDto;
import com.example.trip.member.model.MemberListDto;

public interface MemberService {

	public MemberDto login(MemberDto dto) throws Exception;
	public void join(MemberDto dto) throws Exception;
	public int idCheck(String userId) throws Exception;
	public MemberDto getMember(String userId) throws Exception;
	public void deleteMember(String userId) throws Exception;
	public void updateMember(MemberDto dto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	public List<MemberListDto> listfollow(String userId) throws Exception;
	public List<MemberListDto> listfollower(String userId) throws Exception;
	public void unFollow(String toId, String fromId) throws Exception;
	public void following(String toId, String fromId) throws Exception;
	public List<MemberListDto> listSearchUser(String userId) throws Exception;
//	public MemberDto (MemberDto dto);
}
