package com.ssafy.enjoytrip.map.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlanListDto {
	private String planId;
	private String isPublic;
	private String createTime;
	private String startDate;
	private String endDate;
	private String creatorId;
	private List<PlanDetailDto> planDetailDtoList;
}