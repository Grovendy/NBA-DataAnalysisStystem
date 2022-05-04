package dao.impl;

import java.io.File;
import java.util.*;

import util.Utility;
import dao.RawTeamDao;
import entity.OpponentStatsPerGame;
import entity.OpponentStatsTotal;
import entity.TeamInfo;
import entity.TeamStatsAdvanced;
import entity.TeamStatsPerGame;
import entity.TeamStatsTotal;

/**
 * RawTeamDao的具体实现类
 * 
 */
public class RawTeamDaoImpl implements RawTeamDao {

	private static final String TEAM_PATH = FileManager.DATA_PATH + "/teams/text/";
	
	//存放各类数据
	private final List<TeamInfo> infoList;
	private final List<TeamStatsTotal> totalList;
	private final List<TeamStatsPerGame> perGameList;
	private final List<TeamStatsAdvanced> advancedList;
	private final List<OpponentStatsPerGame> oppPerGameList;
	private final List<OpponentStatsTotal> oppTotalList;
	//辅助哈希表
	private final Map<String, TeamStatsTotal> totalMap;
	
	public RawTeamDaoImpl(){
		System.out.println("Team Data：" + TEAM_PATH);
		
		infoList = new ArrayList<>();
		totalList = new ArrayList<>();
		perGameList = new ArrayList<>();
		advancedList = new ArrayList<>();
		oppPerGameList = new ArrayList<>();
		oppTotalList = new ArrayList<>();
		totalMap = new HashMap<>();
		
		this.getAllData();
	}
	
	public void getAllData(){
		File folder = new File(TEAM_PATH);
		String[] teams = folder.list();
		if (teams==null) return;
		for(String team: teams){
			TeamInfo info = new TeamInfo();
			List<String> lines = FileManager.read(TEAM_PATH + team);
			// 1 = team_t; 2 = team_p_g; 3 = opp_t; 4 = opp_p_g; 5 = team_adv
			int datatype = 0;
			String season = "";
			for(int i=0; i<lines.size();++i){		
				//从第0行获得球队的基本信息	
				if(i == 0){
					String[] data = lines.get(i).split(";",-1);
					info.setName(data[0]);
					info.setAbbr(data[1]);
					info.setBuildup_time(data[2]);
					info.setLocation(data[3]);
					info.setRecord(data[4]);
					info.setPlayeroff_appearance(Utility.stringToInt(data[5]));
					info.setChampionships(Utility.stringToInt(data[6]));
					info.setLeague(data[7]);
					info.setDivision(data[8]);
					infoList.add(info);
				}else{
					if(lines.get(i).equals("Totals")){
						continue;
					}
					String[] data = lines.get(i).split(";");
					if(data.length==7){
						TeamStatsTotal tst = new TeamStatsTotal();
						tst.setAbbr(team);
						tst.setSeason(data[0]);
						tst.setWins(Utility.stringToInt(data[1]));
						tst.setLosses(Utility.stringToInt(data[2]));
						tst.setFinish(Utility.stringToInt(data[3]));
						tst.setAge(Utility.stringToDouble(data[4]));
						tst.setHeight(data[5]);
						tst.setWeight(Utility.stringToDouble(data[6]));
						totalMap.put(team+data[0], tst);
					}else if(data.length==1){
						datatype = 1;
						season = data[0];
					}else{
						switch(datatype){
						case 1:
							totalList.add(getTeamTotal(team,season,lines.get(i)));
							datatype++;
							break;
						case 2:
							perGameList.add(getTeamPerGame(team,season,lines.get(i)));
							datatype++;
							break;
						case 3:
							oppTotalList.add(getTeamOppTotal(team,season,lines.get(i)));
							datatype++;
							break;
						case 4:
							oppPerGameList.add(getTeamOppPerGame(team,season,lines.get(i)));
							datatype++;
							break;
						case 5:
							advancedList.add(getTeamAdvanced(team,season,lines.get(i)));
							datatype++;
							break;
						default:
							break;
						}
					}
				}
			}// end of lines
		}// end of each team.txt
	}
	
	private TeamStatsAdvanced getTeamAdvanced(String team, String season, String string) {
		TeamStatsAdvanced tsadv = new TeamStatsAdvanced();
		tsadv.setAbbr(team);
		tsadv.setSeason(season);
		String[] data = string.split(";",-1);
		tsadv.setPw(Utility.stringToDouble(data[0]));
		tsadv.setPl(Utility.stringToDouble(data[1]));
		tsadv.setMov(Utility.stringToDouble(data[2]));
		tsadv.setSos(Utility.stringToDouble(data[3]));
		tsadv.setSrs(Utility.stringToDouble(data[4]));
		tsadv.setOff_rtg(Utility.stringToDouble(data[5]));
		tsadv.setDef_rtg(Utility.stringToDouble(data[6]));
		tsadv.setPace(Utility.stringToDouble(data[7]));
		tsadv.setFta_per_fga_pct(Utility.stringToDouble(data[8]));
		tsadv.setFg3a_per_fga_pct(Utility.stringToDouble(data[9]));
		tsadv.setOff_efg_pct(Utility.stringToDouble(data[10]));
		tsadv.setOff_tov_pct(Utility.stringToDouble(data[11]));
		tsadv.setOrb_pct(Utility.stringToDouble(data[12]));
		tsadv.setOff_ft_rate(Utility.stringToDouble(data[13]));
		tsadv.setOpp_efg_pct(Utility.stringToDouble(data[14]));
		tsadv.setOpp_tov_pct(Utility.stringToDouble(data[15]));
		tsadv.setDrb_pct(Utility.stringToDouble(data[16]));
		tsadv.setOpp_ft_rate(Utility.stringToDouble(data[17]));
		tsadv.setArena(data[18]);
		tsadv.setAttendance(Utility.stringToInt(data[19]));
		return tsadv;
	}

	private OpponentStatsPerGame getTeamOppPerGame(String team, String season, String string) {
		OpponentStatsPerGame osp = new OpponentStatsPerGame();
		osp.setAbbr(team);
		osp.setSeason(season);
		String[] data = string.split(";",-1);
		if (data.length<23) return osp;
		osp.setMinute(Utility.stringToDouble(data[1]));
		osp.setFg(Utility.stringToDouble(data[2]));
		osp.setFga(Utility.stringToDouble(data[3]));
		osp.setFga_pct(Utility.stringToDouble(data[4]));
		osp.setFg3(Utility.stringToDouble(data[5]));
		osp.setFg3a(Utility.stringToDouble(data[6]));
		osp.setFg3_pct(Utility.stringToDouble(data[7]));
		osp.setFg2(Utility.stringToDouble(data[8]));
		osp.setFg2a(Utility.stringToDouble(data[9]));
		osp.setFg2_pct(Utility.stringToDouble(data[10]));
		osp.setFt(Utility.stringToDouble(data[11]));
		osp.setFta(Utility.stringToDouble(data[12]));
		osp.setFt_pct(Utility.stringToDouble(data[13]));
		osp.setOrb(Utility.stringToDouble(data[14]));
		osp.setDrb(Utility.stringToDouble(data[15]));
		osp.setTrb(Utility.stringToDouble(data[16]));
		osp.setAst(Utility.stringToDouble(data[17]));
		osp.setStl(Utility.stringToDouble(data[18]));
		osp.setBlk(Utility.stringToDouble(data[19]));
		osp.setTov(Utility.stringToDouble(data[20]));
		osp.setPf(Utility.stringToDouble(data[21]));
		osp.setPts(Utility.stringToDouble(data[22]));
		return osp;
	}

	private OpponentStatsTotal getTeamOppTotal(String team, String season, String string) {
		String[] data = string.split(";",-1);
		OpponentStatsTotal total = OpponentStatsTotal.of(data);
		total.setAbbr(team);
		total.setSeason(season);
		return total;
	}

	private TeamStatsPerGame getTeamPerGame(String team, String season, String string) {
		TeamStatsPerGame tsp = new TeamStatsPerGame();
		tsp.setAbbr(team);
		tsp.setSeason(season);
		String[] data = string.split(";",-1);
		if (data.length <=23) return tsp;
		tsp.setMinute(Utility.stringToDouble(data[1]));
		tsp.setFg(Utility.stringToDouble(data[2]));
		tsp.setFga(Utility.stringToDouble(data[3]));
		tsp.setFga_pct(Utility.stringToDouble(data[4]));
		tsp.setFg3(Utility.stringToDouble(data[5]));
		tsp.setFg3a(Utility.stringToDouble(data[6]));
		tsp.setFg3_pct(Utility.stringToDouble(data[7]));
		tsp.setFg2(Utility.stringToDouble(data[8]));
		tsp.setFg2a(Utility.stringToDouble(data[9]));
		tsp.setFg2_pct(Utility.stringToDouble(data[10]));
		tsp.setFt(Utility.stringToDouble(data[11]));
		tsp.setFta(Utility.stringToDouble(data[12]));
		tsp.setFt_pct(Utility.stringToDouble(data[13]));
		tsp.setOrb(Utility.stringToDouble(data[14]));
		tsp.setDrb(Utility.stringToDouble(data[15]));
		tsp.setTrb(Utility.stringToDouble(data[16]));
		tsp.setAst(Utility.stringToDouble(data[17]));
		tsp.setStl(Utility.stringToDouble(data[18]));
		tsp.setBlk(Utility.stringToDouble(data[19]));
		tsp.setTov(Utility.stringToDouble(data[20]));
		tsp.setPf(Utility.stringToDouble(data[21]));
		tsp.setPts(Utility.stringToDouble(data[22]));
		return tsp;
	}

	private TeamStatsTotal getTeamTotal(String team, String season, String string) {
		String[] data = string.split(";",-1);
		TeamStatsTotal total = new TeamStatsTotal();
		try {
			total = TeamStatsTotal.of(data);
		} catch (Exception e) {
			System.out.println(Arrays.toString(data));
			e.printStackTrace();
		}
		total.setAbbr(team);
		total.setSeason(season);
		total.setHeight("");
		totalMap.put(team+season, total);
		return total;
	}

	@Override
	public List<TeamInfo> getAllTeamInfo() {
		return infoList;
	}

	@Override
	public List<TeamStatsPerGame> getAllTeamPerGame() {
		return perGameList;
	}

	@Override
	public List<TeamStatsTotal> getAllTeamTotal() {
		return totalList;
	}

	@Override
	public List<TeamStatsAdvanced> getAllTeamAdvanced() {
		return advancedList;
	}

	@Override
	public List<OpponentStatsPerGame> getAllTeamOppPerGame() {
		return oppPerGameList;
	}

	@Override
	public List<OpponentStatsTotal> getAllTeamOppTotal() {
		return oppTotalList;
	}

}
