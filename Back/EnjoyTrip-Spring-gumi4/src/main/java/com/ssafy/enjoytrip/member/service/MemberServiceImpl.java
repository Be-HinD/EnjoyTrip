package com.ssafy.enjoytrip.member.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.mapper.MemberMapper;
import com.ssafy.enjoytrip.member.model.MemberLetterDto;
import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.MemberListDto;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;
	
	@Override
	public MemberDto login(MemberDto dto) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("??");
		return memberMapper.login(dto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}


	@Override
	public void join(MemberDto dto) throws Exception{
		// TODO Auto-generated method stub
		memberMapper.join(dto);
		return;
	}


	@Override
	public int idCheck(String userId) throws Exception{
		return memberMapper.idCheck(userId);
	}


	@Override
	public MemberDto getMember(String userId) throws Exception {
		return memberMapper.getMember(userId);
	}


	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
		return;
		
	}

	
	@Override
	public void updateMember(MemberDto dto) throws Exception {
		System.out.println("받아온 데이터 :: " + dto);
		memberMapper.updateMember(dto);
		return;
	}
	
	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}
	
	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}
	
	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

	@Override
	public List<MemberListDto> listfollow(String userId) throws Exception {
		return memberMapper.listfollow(userId);
	}
	
	@Override
	public List<MemberListDto> listfollower(String userId) throws Exception {
		return memberMapper.listfollower(userId);
	}
	
	@Override
	public void unFollow(String toId, String fromId) throws Exception {
		// TODO Auto-generated method stub
		memberMapper.unFollow(toId, fromId);
	}

	@Override
	public List<MemberListDto> listSearchUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.listSearchUser(userId);
	}

	@Override
	public void following(String toId, String fromId) throws Exception {
		memberMapper.following(toId, fromId);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MemberLetterDto> receiveGetList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.receiveGetList(userId);
	}

	@Override
	public MemberLetterDto getDetailLetter(String userId) throws Exception {
		return memberMapper.getDetailLetter(userId);


	}

	@Override
	public void sendLetter(MemberLetterDto dto) throws Exception {
		memberMapper.sendLetter(dto);
	}

	@Override
	public void isReadCheck(String contentId) throws Exception {
		// TODO Auto-generated method stub
		memberMapper.isReadCheck(contentId);
	}

	@Override
	public void deleteLetter(String Id) throws Exception {
		memberMapper.deleteLetter(Id);
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getReceiveCount(String userId) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.getReceiveCount(userId);
	}

	@Override
	public List<MemberLetterDto> sendGetList(String userId) throws Exception {
		// TODO Auto-generated method stub
		return memberMapper.sendGetList(userId);
	}
}
