package com.jizhi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.jizhi.dao.RacePersonApplyDao;
import com.jizhi.dao.TeamDao;
import com.jizhi.dao.TeamMemberDao;
import com.jizhi.dao.TeamRaceApplyDao;
import com.jizhi.dao.TeamRaceApplyRejectDao;
import com.jizhi.dao.UserDao;
import com.jizhi.model.RacePersonApply;
import com.jizhi.model.RaceScheduleTeam;
import com.jizhi.model.Team;
import com.jizhi.model.TeamMembers;
import com.jizhi.model.TeamRaceApply;
import com.jizhi.model.TeamRaceApplyReject;
import com.jizhi.model.User;
import com.simple.common.util.PageResult;
import com.simple.common.util.PrimaryKeyUtil;
@Service
public class TeamApplyService {

	@Autowired
	private TeamRaceApplyDao teamRaceApplyDao;
	@Autowired
	private RacePersonApplyDao racePersonApplyDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private TeamMemberDao teamMembersDao;
	@Autowired
	private TeamRaceApplyRejectDao teamRaceApplyRejectDao;
	@Autowired
	private UserDao userDao;
	
	public TeamRaceApply queryTeamApply(String teamName,int raceId) {
		return teamRaceApplyDao.getByRaceAndTeam(raceId, teamName);
	}
	
	public List<TeamRaceApply> queryTeamRaceApplyList(Integer raceId,String raceName,int status,int type,String phone,int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		return teamRaceApplyDao.getTeamRaceApplyList(raceId,raceName, status, type, phone, pageIndex, pageSize);
	}
	
	public int getTeamRaceApplyCount(Integer raceId,String raceName,int status,int type,String phone) {
		return teamRaceApplyDao.getTeamRaceApplyCount(raceId,raceName, status, type, phone);
	}
	
	public PageResult getTeamRaceApplyPageResult(Integer raceId,String raceName,int status,int type,String phone,int pageIndex,int pageSize) {
		List<TeamRaceApply> yards = queryTeamRaceApplyList(raceId,raceName,status,type,phone,pageIndex,pageSize);
		int count = getTeamRaceApplyCount(raceId,raceName, status, type, phone);
		return new PageResult(count,pageSize,pageIndex,yards);
	}
	
	
	public void addTeamApply(TeamRaceApply teamapply) {
		teamRaceApplyDao.addTeamRaceApply(teamapply);
	}
	
	public TeamRaceApply queryTeamApplyById(String id) {
		return teamRaceApplyDao.getTeamRaceApplyById(id);
	}
	
	public void updateSuccess(TeamRaceApply teamapply) {
		teamapply.setStatus(2);
		teamRaceApplyDao.updateStatus(teamapply);
		
		//添加球队，添加球队成员
		Team t = teamDao.queryByName(teamapply.getTeamName());
		if ( null == t) {
			t = new Team();
			t.setCreateTime(new Date());
			t.setId(PrimaryKeyUtil.getUUID());
			t.setImage(teamapply.getTeamImage());
			t.setLeaderPhone(teamapply.getLeaderPhone());
			t.setLeaderName(teamapply.getLeaderName());
			t.setName(teamapply.getTeamName());
			t.setStatus(1);
			t.setType(teamapply.getType());
			t.setRemark(teamapply.getRemark());
			teamDao.addTeam(t);
		}else {
			t.setImage(teamapply.getTeamImage());
			t.setLeaderPhone(teamapply.getLeaderPhone());
			t.setLeaderName(teamapply.getLeaderName());
			t.setRemark(teamapply.getRemark());
			teamDao.updateTeam(t);
		}
		//领队
		TeamMembers ld = teamMembersDao.queryByPhone(t.getId(), t.getLeaderPhone());
		if ( null == ld) {
			ld = new TeamMembers();
			ld.setLeader(1);
			ld.setName(t.getLeaderName());
			ld.setPhone(t.getLeaderPhone());
			ld.setTeamId(t.getId());
			ld.setRemark(t.getRemark());
			teamMembersDao.addTeamMembers(ld);
		}else {
			ld.setLeader(1);
			ld.setName(t.getLeaderName());
			teamMembersDao.updateTeamMembers(ld);
		}
		//查询申请的成员
		List<RacePersonApply> rapplys = racePersonApplyDao.queryList(teamapply.getRaceId(), teamapply.getId(), 0, 10000000);
		if ( null != rapplys) {
			for ( int i = 0 ; i < rapplys.size() ; i ++) {
				RacePersonApply ra = rapplys.get(i);
				TeamMembers tm = teamMembersDao.queryByPhone(t.getId(), ra.getPhone());
				if ( null == tm ) {
					tm = new TeamMembers();
					if (t.getLeaderPhone().equals(ra.getPhone())) {
						tm.setLeader(1);
					}
					tm.setName(ra.getName());
					tm.setPhone(ra.getPhone());
					tm.setTeamId(t.getId());
					teamMembersDao.addTeamMembers(tm);
				}else {
					tm.setName(ra.getName());
					if (t.getLeaderPhone().equals(ra.getPhone())) {
						tm.setLeader(1);
					}
					teamMembersDao.updateTeamMembers(tm);
				}
				//设置用户的最佳位置
				User user = userDao.getUserByPhone(ra.getPhone());
				String positions = user.getPositions();
				if (null != ra.getPositions()) {
					List<String> plist = new ArrayList<String>();
					if ( null != positions) {
						plist = (List<String>) JSONArray.parse(positions);
					}
					if (!plist.contains(ra.getPositions())) {
						plist.add(ra.getPositions());
					}
					user.setPositions(JSONArray.toJSONString(plist));
					userDao.update(user);
				}
			}
		}
	}
	
	public void updateReject(TeamRaceApply teamapply) {
		TeamRaceApplyReject tar = new TeamRaceApplyReject();
		if ( null != teamapply.getCreateTime()) {
			tar.setCreateTime(teamapply.getCreateTime());
		}
		tar.setId(teamapply.getId());
		tar.setLeaderName(teamapply.getLeaderName());
		tar.setLeaderPhone(teamapply.getLeaderPhone());
		tar.setRaceId(teamapply.getRaceId());
		tar.setRaceName(teamapply.getRaceName());
		tar.setRemark(teamapply.getRemark());
		tar.setTeamImage(teamapply.getTeamImage());
		tar.setTeamName(teamapply.getTeamName());
		tar.setType(teamapply.getType());
		tar.setMemberList(racePersonApplyDao.queryList(teamapply.getRaceId(), teamapply.getId(), 0, 10000000));
		tar.setStudentNo(teamapply.getStudentNo());
		tar.setClassName(teamapply.getClassName());
		teamRaceApplyRejectDao.addTeamRaceApply(tar);
		teamRaceApplyDao.deleteById(teamapply.getId());
		racePersonApplyDao.deleteByTeamApplyId(teamapply.getRaceId(), teamapply.getId());
		
	}
	
	
	public RacePersonApply queryPersonApplyByPhone(int raceId,String phone) {
		return racePersonApplyDao.getByRaceAndPhone(raceId, phone);
	}
	
	public void addPersonApply(RacePersonApply racePersonApply) {
		racePersonApplyDao.addRacePersonApply(racePersonApply);
	}
	
	public List<RacePersonApply> queryPersonApplysByTeamApply(int raceId,String teamApplyId) {
		return racePersonApplyDao.queryList(raceId, teamApplyId, 0, 10000000);
	}
	
	public List<RacePersonApply> queryPersonApplysByTeamName(int raceId,List<String> teamNames) {
		return racePersonApplyDao.queryListByTeamNames(raceId, teamNames, 0, 10000000);
	}
	
	public List<TeamRaceApply> queryTeamApplyList(String phone) {
		//查询所有的leader
		List<TeamRaceApply> alls = new ArrayList<TeamRaceApply>();
		List<TeamRaceApply> ts = queryTeamRaceApplyList(0,null,0,0,phone,1,10000000);
		if ( null != ts && ts.size() > 0 ) {
			alls.addAll(ts);
		}
		
		//查询所有成员
		List<String> alltemids = new ArrayList<String>();
		
		for (int i = 0 ; i < 10; i ++) {
			List<String> temids = racePersonApplyDao.queryTeamApplyIds(i, phone);
			if ( null != temids && temids.size() > 0 ) {
				alltemids.addAll(temids);
			}
		}
		if (alltemids.size() > 0 ) {
			List<TeamRaceApply> tss = teamRaceApplyDao.queryListByIds(alltemids);
			if ( null != tss && tss.size() > 0 ) {
				alls.removeAll(tss);
				alls.addAll(tss);
			}
		}
		return alls;
	}
	
	public TeamRaceApply queryTeamApply(Integer raceId,String phone) {
		//查询所有的leader
		List<TeamRaceApply> ts = queryTeamRaceApplyList(raceId,null,0,0,phone,1,10);
		if ( null != ts && ts.size() > 0 ) {
			return ts.get(0);
		}
		
		//查询所有成员
		
		RacePersonApply ra = racePersonApplyDao.getByRaceAndPhone(raceId, phone);
		if (null != ra) {
			return teamRaceApplyDao.getTeamRaceApplyById(ra.getTeamApplyId());
		}
		return null;
	}
	
	public PageResult getTeamRaceApplyRejectPageResult(Integer raceId,String raceName,int status,int type,String phone,int pageIndex,int pageSize) {
		if (pageIndex <=0 ) {
			pageIndex = 1;
		}
		List<TeamRaceApplyReject> rejectlist = teamRaceApplyRejectDao.getTeamRaceApplyList(raceId,raceName, status, type, phone, (pageIndex-1)*pageSize, pageSize);
		int count = teamRaceApplyRejectDao.getTeamRaceApplyCount(raceId,raceName, status, type, phone);
		return new PageResult(count,pageSize,pageIndex,rejectlist);
	}
	
	public void deleteTeamApplyPerson(int raceId,int id) {
		racePersonApplyDao.delete(raceId, id);
	}
	
	public void deleteTeamApplyPerson(int raceId,String phone) {
		racePersonApplyDao.deleteByPhone(raceId, phone);
	}
	
}
