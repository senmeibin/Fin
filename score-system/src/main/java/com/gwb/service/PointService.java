package com.gwb.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gwb.dto.PointDto;
import com.gwb.entity.Match;
import com.gwb.entity.Member;
import com.gwb.entity.Point;
import com.gwb.entity.Syspara;
import com.gwb.entity.WinPer;
import com.gwb.mapper.MatchMapper;
import com.gwb.mapper.MemberMapper;
import com.gwb.mapper.PointMapper;
import com.gwb.mapper.SysparaMapper;


@Service
public class PointService {
	@Autowired
	private PointMapper pointMapper;
	
	@Autowired
	private SysparaMapper sysparaMapper;
	
	@Autowired
	private MatchMapper matchMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	private final Logger logger = LoggerFactory.getLogger(PointService.class);	
	private static final String DATE_FORMAT="yyyyMMdd" ;
	private static final int MAX_POINT=60 ;
	private static final int MIN_POINT=5 ;
	
	/**
	 * 指定した日付の会員ポイント一覧（画面表示用）を取得
	 * @param match_date　試合日付
	 * @return　会員ポイント一覧
	 */
	public List<PointDto> getPointDtoList(String match_date) {
		List<PointDto> ret = new ArrayList<PointDto>();
		
		List<Point> points = pointMapper.getByMatchDateSortRank(match_date);
		List<Member> members = memberMapper.getAll();
		
		for(Point point : points) {
			PointDto pointDto = new PointDto();
			BeanUtils.copyProperties(point, pointDto);
			Member member = members.stream().filter(x -> x.getId() == point.getId()).findFirst().get();
			pointDto.setName(member.getName());
			ret.add(pointDto);
		}
		return ret;
	}
	
	public List<WinPer> getWinPerRank(String match_date){
		return pointMapper.getWinPerByMatchDateSortRank(match_date);
	}
	public List<String> getAllMatchDate() {
		return pointMapper.getAllMatchDate();
	}
	
	public List<Member> getRecentMember() {
		// 今日の日付
		LocalDate nowLocalDt = LocalDate.now();
		// 3っか月前の日付
		LocalDate before3Month_locatDt = nowLocalDt.minusMonths(3); 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String before3MonthString = dtf.format(before3Month_locatDt);
		return pointMapper.getRecentMember(before3MonthString);
	}
	/**
	 * 前回ポイント計算日から本日までの試合を処理し、ポイントを計算する。
	 * １．前回ポイント計算日を取得し、日付は本日の場合処理終了
	 * ２．前回ポイント計算日＋１して、その日の試合を取得し、試合がない場合処理終了
	 * ３．上記試合に出た会員を会員テーブルにてアクティブする
	 * ４．前回会員のポイント一覧を取得
	 * ５．前回会員のトータルポイントに基づいて、今回試合の取得ポイントを計算し、DBへ反映
	 * ６．取得したポイントを前回トータルポイントに加算し、ランキング算出し、DBへ反映
	 */
	//@Scheduled(initialDelay=3000, fixedDelay=3600000)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void processMatchResults() {
		// dateフォーマット
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		// 今日の日付
		LocalDate nowLocalDt = LocalDate.now();
		String localDateStr = dtf.format(nowLocalDt);
		logger.info("batch start date:"+localDateStr);
		
		while(true) {
			String last_batch_date = sysparaMapper.getOneForUpdate("last_batch_date");
			logger.info("last_batch_date:"+last_batch_date);
			if(last_batch_date == null) {
				logger.error("last_batch_date is null.");
				return;
			}
			
			LocalDate last_batch_date_locatDt = LocalDate.parse(last_batch_date,dtf); 
			if(last_batch_date_locatDt.isEqual(nowLocalDt) || last_batch_date_locatDt.isAfter(nowLocalDt)){
				// ポイント計算日が今日以降の場合に処理終了
				return;
			}
			
			// ポイント計算日次の日の文字列
			String nextDayStr = dtf.format(last_batch_date_locatDt.plusDays(1));
			
			// 次の日の試合一覧取得
			List<Match> matches = matchMapper.getByMatchDate(nextDayStr);
			
			if(matches != null && !matches.isEmpty()) {
				logger.info("processing date:"+nextDayStr);
				// 試合メンバーをアクティブにする
				for(Match match : matches) {
					activatedMatchMember(match);
				}
				
				// 前回会員のポイント一覧　
				List<Point> lastPoints = pointMapper.getLastPointByMatchDateSortId(nextDayStr);

				// 各試合取得ポイント計算
				Map<Integer,Point> newPoints = caculatePoints(lastPoints,matches);
			
				// 今回会員ポイント実績を削除してから新規作成
				createPointResult(lastPoints,newPoints,nextDayStr);
			}
			
			// ポイント計算日更新
			sysparaMapper.update(new Syspara("last_batch_date",nextDayStr));
		}
	}
	
	public int caculateOneMatchPoint(Match match) {
		// 試合メンバーをアクティブにする
		activatedMatchMember(match);
		
		// 前回会員のポイント一覧　
		List<Point> lastPoints = pointMapper.getLastPointByMatchDateSortId(match.getMatch_date());
		
		// 試合取得ポイント計算
		caculatePoints(lastPoints,Arrays.asList(match));
		
		return match.getPoints();
	}
	
	public Map<Integer,Point> caculatePoints(List<Point> lastPoints ,List<Match> matches) {
		return caculatePoints(lastPoints,matches,true);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Map<Integer,Point> caculatePoints(List<Point> lastPoints ,List<Match> matches, Boolean updateFlag) {
		Map<Integer,Point> newPoints = new HashMap<Integer,Point>(); 
		for(Match match : matches) {
			// 勝ち組メンバー１前回ポイント
			Point winner1Point = lastPoints.stream()
					.filter(x -> x.getId() == match.getWinner1())
					.findAny()
					.orElse(null);
			Integer winner1PointInt = null;
			if(winner1Point==null) {
				// 初回試合した会員のため、初回ポイントが会員表から取得
				winner1PointInt = memberMapper.getOne(match.getWinner1()).getInit_point();
				// ダミーPoint作成
				winner1Point = new Point(match.getWinner1(),"00000000",0,0,0,winner1PointInt,0);
				lastPoints.add(winner1Point);
			}else {
				winner1PointInt = winner1Point.getTotal_points();
			}
			// 勝ち組メンバー１今回ポイント
			Point winner1NewPoint = null;
			if(newPoints.containsKey(match.getWinner1())) {
				winner1NewPoint = newPoints.get(match.getWinner1());
				winner1NewPoint.setWin_match(winner1NewPoint.getWin_match()+1);
			}else {
				winner1NewPoint = new Point(match.getWinner1(),match.getMatch_date(),1,0,0,0,0);
				newPoints.put(match.getWinner1(), winner1NewPoint);
			}
			
			// 勝ち組メンバー２前回ポイント
			Point winner2Point = lastPoints.stream()
					.filter(x -> x.getId() == match.getWinner2())
					.findAny()
					.orElse(null);
			Integer winner2PointInt = null;
			if(winner2Point==null) {
				// 初回試合した会員のため、初回ポイントが会員表から取得
				winner2PointInt = memberMapper.getOne(match.getWinner2()).getInit_point();
				// ダミーPoint作成
				winner2Point = new Point(match.getWinner2(),"00000000",0,0,0,winner2PointInt,0);
				lastPoints.add(winner2Point);
			}else {
				winner2PointInt = winner2Point.getTotal_points();
			}
			// 勝ち組メンバー２今回ポイント
			Point winner2NewPoint = null;
			if(newPoints.containsKey(match.getWinner2())) {
				winner2NewPoint = newPoints.get(match.getWinner2());
				winner2NewPoint.setWin_match(winner2NewPoint.getWin_match()+1);
			}else {
				winner2NewPoint = new Point(match.getWinner2(),match.getMatch_date(),1,0,0,0,0);
				newPoints.put(match.getWinner2(), winner2NewPoint);
			}
			
			// 負け組メンバー１前回ポイント
			Point losser1Point = lastPoints.stream()
					.filter(x -> x.getId() == match.getLosser1())
					.findAny()
					.orElse(null);
			Integer losser1PointInt = null;
			if(losser1Point==null) {
				// 初回試合した会員のため、初回ポイントが会員表から取得
				losser1PointInt = memberMapper.getOne(match.getLosser1()).getInit_point();
				// ダミーPoint作成
				losser1Point = new Point(match.getLosser1(),"00000000",0,0,0,losser1PointInt,0);
				lastPoints.add(losser1Point);
			}else {
				losser1PointInt = losser1Point.getTotal_points();
			}
			// 負け組メンバー１今回ポイント
			Point losser1NewPoint = null;
			if(newPoints.containsKey(match.getLosser1())) {
				losser1NewPoint = newPoints.get(match.getLosser1());
				losser1NewPoint.setLoss_match(losser1NewPoint.getLoss_match()+1);
			}else {
				losser1NewPoint = new Point(match.getLosser1(),match.getMatch_date(),0,1,0,0,0);
				newPoints.put(match.getLosser1(), losser1NewPoint);
			}
			
			// 負け組メンバー２前回ポイント
			Point losser2Point = lastPoints.stream()
					.filter(x -> x.getId() == match.getLosser2())
					.findAny()
					.orElse(null);
			Integer losser2PointInt = null;
			if(losser2Point==null) {
				// 初回試合した会員のため、初回ポイントが会員表から取得
				losser2PointInt = memberMapper.getOne(match.getLosser2()).getInit_point();
				// ダミーPoint作成
				losser2Point = new Point(match.getLosser2(),"00000000",0,0,0,losser2PointInt,0);
				lastPoints.add(losser2Point);
			}else {
				losser2PointInt = losser2Point.getTotal_points();
			}
			// 負け組メンバー２今回ポイント
			Point losser2NewPoint = null;
			if(newPoints.containsKey(match.getLosser2())) {
				losser2NewPoint = newPoints.get(match.getLosser2());
				losser2NewPoint.setLoss_match(losser2NewPoint.getLoss_match()+1);
			}else {
				losser2NewPoint = new Point(match.getLosser2(),match.getMatch_date(),0,1,0,0,0);
				newPoints.put(match.getLosser2(), losser2NewPoint);
			}
			
			// 試合ポイント計算
			double match_point = (losser1PointInt+losser2PointInt)*(losser1PointInt+losser2PointInt)*20.0
					/ (winner1PointInt+winner2PointInt)/(winner1PointInt+winner2PointInt);
			int match_point_int = (int)Math.round(match_point);
			if(match_point_int>MAX_POINT) {
				// 上限80ポイント
				match_point_int=MAX_POINT;
			}
			if(match_point_int<MIN_POINT) {
				// 下限5ポイント
				match_point_int=MIN_POINT;
			}
			
			match.setPoints(match_point_int);
			// 試合更新
			if(updateFlag) {
				matchMapper.update(match);
			}
			
			// ポイン更新
			winner1NewPoint.setGet_points(winner1NewPoint.getGet_points() + match_point_int);
			winner2NewPoint.setGet_points(winner2NewPoint.getGet_points() + match_point_int);
			losser1NewPoint.setGet_points(losser1NewPoint.getGet_points() - match_point_int);
			losser2NewPoint.setGet_points(losser2NewPoint.getGet_points() - match_point_int);
		}
		return newPoints;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void createPointResult(List<Point> lastPoints,Map<Integer,Point> newPoints,String match_date) {
		// 参加メンバー全員＋５ポイント
		newPoints.values().forEach(x -> x.setGet_points(x.getGet_points()+5));
		
		// トータルポイント算出
		Point newPoint = null;
		List<Point> newPointList = new ArrayList<Point>();
		Map<Integer, Member> actAtiveMemberMap = new HashMap<Integer, Member>();
		memberMapper.getAllActived().forEach(x -> actAtiveMemberMap.put(x.getId(), x));
		
		for(Point lastPoint : lastPoints) {
			// 非アクティブ会員はランキングに出力しない
			if(!actAtiveMemberMap.containsKey(lastPoint.getId())) {
				continue;
			}
			
			if(newPoints.containsKey(lastPoint.getId())){
				newPoint = newPoints.get(lastPoint.getId());
				newPoint.setTotal_points(lastPoint.getTotal_points()+newPoint.getGet_points());
			}else {
				newPoint = new Point();
				newPoint.setId(lastPoint.getId());
				newPoint.setMatch_date(match_date);
				newPoint.setWin_match(0);
				newPoint.setLoss_match(0);
				newPoint.setGet_points(0);
				newPoint.setTotal_points(lastPoint.getTotal_points());
			}
			newPointList.add(newPoint);
		}
		
		// ランク算出、DBへ反映
		newPointList.sort((p1,p2) -> p2.getTotal_points()-(p1.getTotal_points()));
		pointMapper.deleteByMatchDate(match_date);
		for(int i= 0; i< newPointList.size(); i++) {
			newPointList.get(i).setRank(i+1);
			pointMapper.insert(newPointList.get(i));
		}
	}
	
	private void activatedMatchMember(Match match) {
		memberMapper.Activate(match.getWinner1());
		memberMapper.Activate(match.getWinner2());
		memberMapper.Activate(match.getLosser1());
		memberMapper.Activate(match.getLosser2());
	}
}
