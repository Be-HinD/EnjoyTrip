package com.example.trip.member.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.trip.member.model.MemberDto;
import com.example.trip.member.model.MemberListDto;

@Mapper
public interface MemberMapper {
	public MemberDto login(MemberDto dto) throws SQLException;
	public int idCheck(String userId) throws SQLException;
	public void join(MemberDto dto) throws SQLException;
	public MemberDto getMember(String userId) throws SQLException;
	public void deleteMember(String userId) throws SQLException;
	public void updateMember(MemberDto dto) throws Exception;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	MemberDto userInfo(String userId) throws SQLException;
	public List<MemberListDto> listfollow(String userId) throws Exception;
	public List<MemberListDto> listfollower(String userId) throws Exception;
	public void unFollow(String toId, String fromId) throws Exception;
	public void following(String toId, String fromId) throws Exception;
	public List<MemberListDto> listSearchUser(String userId) throws Exception;
}
