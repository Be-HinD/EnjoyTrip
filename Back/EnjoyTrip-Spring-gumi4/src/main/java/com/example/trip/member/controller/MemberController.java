package com.example.trip.member.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trip.member.model.MemberDto;
import com.example.trip.member.model.MemberListDto;
import com.example.trip.member.service.MemberService;
import com.example.trip.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	private JWTUtil jwtUtil;


	@PostMapping(value = "/join")
	public ResponseEntity<MemberDto> join(@RequestBody MemberDto dto) {
		try {
			System.out.println(dto);
			memberService.join(dto);

			MemberDto resultDto = memberService.getMember(dto.getUserId());
			if(resultDto != null) {
				System.out.println("ㄱㅊㄱㅊ");
				System.out.println(HttpStatus.OK);
				return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
			}
			resultDto = new MemberDto();
			return new ResponseEntity<MemberDto>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인을 위한 부가정보.", required = true) MemberDto dto) {

		log.debug("login user : {}", dto);
		System.out.println(dto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			MemberDto loginUser = memberService.login(dto);
			//			resultDto = memberService.login(dto);
			System.out.println(loginUser);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);

				//				발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);

				//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);

				//Cookie로 변경
				resultMap.put("refresh-token", refreshToken);

				status = HttpStatus.CREATED;
			}
			else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
		//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
				//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
				System.out.println(memberDto);
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


	@GetMapping("/idCheck/{userId}")
	public ResponseEntity<?> idCheck(@PathVariable("userId") String userId) {
		int value;
		try {
			value = memberService.idCheck(userId);
			if(value == 0) {
				return new ResponseEntity<String>("사용할 수 있는 아이디입니다.", HttpStatus.OK);
			}

			return new ResponseEntity<String>("중복되는 아이디가 존재합니다.", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	}

	@GetMapping("/followlist/{userId}")
	public ResponseEntity<?> followGetList(@PathVariable("userId") String userId, HttpServletRequest request) {
		System.out.println(userId);
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				List<MemberListDto> memberListDto = memberService.listfollow(userId);
				System.out.println(userId + "에 대한 여기서부터 겟리스트 : " + memberListDto);
				HttpHeaders header = new HttpHeaders();
				header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
				return ResponseEntity.ok().headers(header).body(memberListDto);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return null;
	}
	
	@GetMapping("/followerlist/{userId}")
	public ResponseEntity<?> followerGetList(@PathVariable("userId") String userId, HttpServletRequest request) {
		System.out.println(userId);
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				List<MemberListDto> memberListDto = memberService.listfollower(userId);
				HttpHeaders header = new HttpHeaders();
				header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
				return ResponseEntity.ok().headers(header).body(memberListDto);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return null;
	}
	
	@GetMapping("/searchUser/{userId}")
	public ResponseEntity<?> friendsGetList(@PathVariable("userId") String userId, HttpServletRequest request) {
		System.out.println(userId);
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				List<MemberListDto> memberListDto = memberService.listSearchUser(userId);
				System.out.println(memberListDto);
				HttpHeaders header = new HttpHeaders();
				header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
				return ResponseEntity.ok().headers(header).body(memberListDto);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
		}
		return null;
	}
	
	
	@GetMapping("/unfollow/{toId}/{fromId}")
	public ResponseEntity<?> unFollow(@PathVariable("toId") String toId, @PathVariable("fromId") String fromId) {
		try {
			memberService.unFollow(toId, fromId);
			return new ResponseEntity<String>("팔로우 취소 되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	
	}
	
	@GetMapping("/following/{toId}/{fromId}")
	public ResponseEntity<?> following(@PathVariable("toId") String toId, @PathVariable("fromId") String fromId) {
		try {
			memberService.following(toId, fromId);
			return new ResponseEntity<String>("팔로우 완료.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	
	}

	@GetMapping("/deleteMember/{userId}")
	public ResponseEntity<?> deleteMember(@PathVariable("userId") String userId) {
		int value;
		try {
			value = memberService.idCheck(userId);
			if(value == 0) {
				return new ResponseEntity<String>("탈퇴불가! : 존재하지 않는 아이디", HttpStatus.OK);
			}
			memberService.deleteMember(userId);
			return new ResponseEntity<String>("탈퇴되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	}

	@PostMapping("/updateMember")
	public ResponseEntity<?> updateMember(@RequestBody MemberDto dto, HttpServletRequest request) {
		log.info("사용 가능한 토큰!!!");
		try {
			//				로그인 사용자 정보.
			memberService.updateMember(dto); //업데이트 시킨 후
			return new ResponseEntity<String>("수정하였습니다.", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("에러 발생!", HttpStatus.OK);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
