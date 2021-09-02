package com.ftg.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftg.dao.entity.ProAnniversary;
import com.ftg.dao.entity.ProAnniversaryType;
import com.ftg.dao.entity.ProBucaiku;
import com.ftg.dao.entity.ProBucaikuPrice;
import com.ftg.dao.entity.ProChaodue;
import com.ftg.dao.entity.ProChaodueType;
import com.ftg.dao.entity.ProDonateItems;
import com.ftg.dao.entity.ProJieyuan;
import com.ftg.dao.entity.ProJieyuanPrice;
import com.ftg.dao.entity.ProLianghuang;
import com.ftg.dao.entity.ProLianghuangType;
import com.ftg.dao.entity.ProPrinceStar;
import com.ftg.dao.entity.ProPrinceStarType;
import com.ftg.dao.entity.ProPurdue;
import com.ftg.dao.entity.ProPurdueType;
import com.ftg.repository.ProAnniversaryRepository;
import com.ftg.repository.ProAnniversaryTypeRepository;
import com.ftg.repository.ProBucaikuPriceRepository;
import com.ftg.repository.ProBucaikuRepository;
import com.ftg.repository.ProChaodueRepository;
import com.ftg.repository.ProChaodueTypeRepository;
import com.ftg.repository.ProDonateItemsRepository;
import com.ftg.repository.ProJieyuanPriceRepository;
import com.ftg.repository.ProJieyuanRepository;
import com.ftg.repository.ProLianghuangRepository;
import com.ftg.repository.ProLianghuangTypeRepository;
import com.ftg.repository.ProPrinceStarRepository;
import com.ftg.repository.ProPrinceStarTypeRepository;
import com.ftg.repository.ProPurdueRepository;
import com.ftg.repository.ProPurdueTypeRepository;

@Controller
public class Params2Controller {

	@Autowired
	private ProPrinceStarRepository princeStarRepository;
	
	@Autowired
	private ProPrinceStarTypeRepository princeStarTypeRepository;
	
	@Autowired
	private ProPurdueRepository purdueRepository;
	
	@Autowired
	private ProPurdueTypeRepository purdueTypeRepository;
	
	@Autowired
	private ProChaodueRepository chaodueRepository;
	
	@Autowired
	private ProChaodueTypeRepository chaodueTypeRepository;
	
	@Autowired
	private ProAnniversaryRepository anniversaryRepository;
	
	@Autowired
	private ProAnniversaryTypeRepository anniversaryTypeRepository;
	
	@Autowired
	private ProLianghuangRepository lianghuangRepository;
	
	@Autowired
	private ProLianghuangTypeRepository lianghuangTypeRepository;
	
	@Autowired
	private ProJieyuanRepository jieyuanRepository;
	
	@Autowired
	private ProJieyuanPriceRepository jieyuanPriceRepository;
	
	@Autowired
	private ProBucaikuRepository bucaikuRepository;
	
	@Autowired
	private ProBucaikuPriceRepository bucaikuPriceRepository;
	
	@Autowired
	private ProDonateItemsRepository donateItemsRepository;
	
	
	
	/**
	 * 五年千歲禮斗
	 * @return
	 */
	@RequestMapping(value = "/princeStar", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> princeStar(){
		List<ProPrinceStar> list = princeStarRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPrinceStar pps = list.get(0);
			List<ProPrinceStarType> ts = princeStarTypeRepository.findByPrinceStarId(pps.getId());
			if(ts != null && ts.size() > 0){
				pps.setPrinceStarType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editPrinceStar", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPrinceStar(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProPrinceStar pps = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pps = princeStarRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pps.setUpdateDate(new Date());
				pps.setUpdateUserId(11111);
			}else{
				pps = new ProPrinceStar();
				pps.setCreateDate(new Date());
				pps.setCreateUserId(11111);
				
				
				ProPrinceStar li = princeStarRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = princeStarRepository.findByMaxId();
					if(maxId != null){
						List<ProPrinceStarType> lts = princeStarTypeRepository.findByPrinceStarId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pps.setSessions(obj.get("sessions").toString());
				pps.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pps.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pps.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pps.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				princeStarRepository.save(pps);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProPrinceStar> list = princeStarRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProPrinceStar p = list.get(0);
				List<ProPrinceStarType> ts = princeStarTypeRepository.findByPrinceStarId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setPrinceStarType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("princeStarId", pps.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editPrinceStarType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPrinceStarType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int princeStarId = 0;
		ProPrinceStarType ppt = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			princeStarId = Integer.parseInt(obj.get("princeStarId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				ppt = princeStarTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				ppt.setUpdateDate(new Date());
				ppt.setUpdateUserId(11111);
			}else{
				ppt = new ProPrinceStarType();
				ppt.setQuantity(0);
				ppt.setCreateDate(new Date());
				ppt.setCreateUserId(11111);
				
				List<ProPrinceStarType> lit = princeStarTypeRepository.findByCodeName(princeStarId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				ppt.setPrinceStarId(princeStarId);
				ppt.setCode(obj.get("code").toString());
				ppt.setName(obj.get("name").toString());
				ppt.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				ppt.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				princeStarTypeRepository.save(ppt);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProPrinceStarType> list = princeStarTypeRepository.findByPrinceStarId(princeStarId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByPrinceStarId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByPrinceStarId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProPrinceStarType> ts = princeStarTypeRepository.findByPrinceStarId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importPrinceStarType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importPrinceStarType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = princeStarTypeRepository.findByMaxPrinceStarId();
		if(maxId != null){
			List<ProPrinceStarType> lts = princeStarTypeRepository.findByPrinceStarId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProPrinceStarType p : lts) {
					ProPrinceStarType type = new ProPrinceStarType();
					type.setPrinceStarId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					princeStarTypeRepository.save(type);
				}
			}
		}
		
		List<ProPrinceStar> list = princeStarRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPrinceStar p = list.get(0);
			List<ProPrinceStarType> ts = princeStarTypeRepository.findByPrinceStarId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setPrinceStarType(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 普渡
	 * @return
	 */
	@RequestMapping(value = "/purdue", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> purdue(){
		List<ProPurdue> list = purdueRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPurdue pp = list.get(0);
			List<ProPurdueType> ts = purdueTypeRepository.findByPurdueId(pp.getId());
			if(ts != null && ts.size() > 0){
				pp.setPurdueType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editPurdue", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPurdue(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProPurdue pp = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pp = purdueRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pp.setUpdateDate(new Date());
				pp.setUpdateUserId(11111);
			}else{
				pp = new ProPurdue();
				pp.setCreateDate(new Date());
				pp.setCreateUserId(11111);
				
				ProPurdue li = purdueRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = purdueRepository.findByMaxId();
					if(maxId != null){
						List<ProPurdueType> lts = purdueTypeRepository.findByPurdueId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pp.setSessions(obj.get("sessions").toString());
				pp.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pp.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pp.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pp.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				purdueRepository.save(pp);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProPurdue> list = purdueRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProPurdue p = list.get(0);
				List<ProPurdueType> ts = purdueTypeRepository.findByPurdueId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setPurdueType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("purdueId", pp.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editPurdueType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPurdueType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int purdueId = 0;
		ProPurdueType ppt = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			purdueId = Integer.parseInt(obj.get("purdueId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				ppt = purdueTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				ppt.setUpdateDate(new Date());
				ppt.setUpdateUserId(11111);
			}else{
				ppt = new ProPurdueType();
				ppt.setQuantity(0);
				ppt.setCreateDate(new Date());
				ppt.setCreateUserId(11111);
				
				List<ProPurdueType> lit = purdueTypeRepository.findByCodeName(purdueId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				ppt.setPurdueId(purdueId);
				ppt.setCode(obj.get("code").toString());
				ppt.setName(obj.get("name").toString());
				ppt.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				ppt.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				purdueTypeRepository.save(ppt);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProPurdueType> list = purdueTypeRepository.findByPurdueId(purdueId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByPurdueId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByPurdueId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProPurdueType> ts = purdueTypeRepository.findByPurdueId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importPurdueType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importPurdueType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = purdueTypeRepository.findByMaxPurdueId();
		if(maxId != null){
			List<ProPurdueType> lts = purdueTypeRepository.findByPurdueId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProPurdueType p : lts) {
					ProPurdueType type = new ProPurdueType();
					type.setPurdueId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					purdueTypeRepository.save(type);
				}
			}
		}
		
		List<ProPurdue> list = purdueRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPurdue p = list.get(0);
			List<ProPurdueType> ts = purdueTypeRepository.findByPurdueId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setPurdueType(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 超渡
	 * @return
	 */
	@RequestMapping(value = "/chaodue", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> chaodue(){
		List<ProChaodue> list = chaodueRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProChaodue pc = list.get(0);
			List<ProChaodueType> ts = chaodueTypeRepository.findByChaodueId(pc.getId());
			if(ts != null && ts.size() > 0){
				pc.setChaodueType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editChaodue", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editChaodue(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProChaodue pc = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pc = chaodueRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pc.setUpdateDate(new Date());
				pc.setUpdateUserId(11111);
			}else{
				pc = new ProChaodue();
				pc.setCreateDate(new Date());
				pc.setCreateUserId(11111);
				
				ProChaodue li = chaodueRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = chaodueRepository.findByMaxId();
					if(maxId != null){
						List<ProChaodueType> lts = chaodueTypeRepository.findByChaodueId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pc.setSessions(obj.get("sessions").toString());
				pc.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pc.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pc.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pc.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				chaodueRepository.save(pc);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProChaodue> list = chaodueRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProChaodue p = list.get(0);
				List<ProChaodueType> ts = chaodueTypeRepository.findByChaodueId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setChaodueType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("chaodueId", pc.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editChaodueType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editChaodueType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int chaodueId = 0;
		ProChaodueType pct = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			chaodueId = Integer.parseInt(obj.get("chaodueId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pct = chaodueTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pct.setUpdateDate(new Date());
				pct.setUpdateUserId(11111);
			}else{
				pct = new ProChaodueType();
				pct.setQuantity(0);
				pct.setCreateDate(new Date());
				pct.setCreateUserId(11111);
				
				List<ProChaodueType> lit = chaodueTypeRepository.findByCodeName(chaodueId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pct.setChaodueId(chaodueId);
				pct.setCode(obj.get("code").toString());
				pct.setName(obj.get("name").toString());
				pct.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				pct.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				chaodueTypeRepository.save(pct);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProChaodueType> list = chaodueTypeRepository.findByChaodueId(chaodueId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByChaodueId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByChaodueId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProChaodueType> ts = chaodueTypeRepository.findByChaodueId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importChaodueType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importChaodueType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = chaodueTypeRepository.findByMaxChaodueId();
		if(maxId != null){
			List<ProChaodueType> lts = chaodueTypeRepository.findByChaodueId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProChaodueType p : lts) {
					ProChaodueType type = new ProChaodueType();
					type.setChaodueId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					chaodueTypeRepository.save(type);
				}
			}
		}
		
		List<ProChaodue> list = chaodueRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProChaodue p = list.get(0);
			List<ProChaodueType> ts = chaodueTypeRepository.findByChaodueId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setChaodueType(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 建廟慶典
	 * @return
	 */
	@RequestMapping(value = "/anniversary", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> anniversary(){
		List<ProAnniversary> list = anniversaryRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProAnniversary pa = list.get(0);
			List<ProAnniversaryType> ts = anniversaryTypeRepository.findByAnniversaryId(pa.getId());
			if(ts != null && ts.size() > 0){
				pa.setAnniversaryType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editAnniversary", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editAnniversary(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProAnniversary pa = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pa = anniversaryRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pa.setUpdateDate(new Date());
				pa.setUpdateUserId(11111);
			}else{
				pa = new ProAnniversary();
				pa.setCreateDate(new Date());
				pa.setCreateUserId(11111);
				
				ProAnniversary li = anniversaryRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = anniversaryRepository.findByMaxId();
					if(maxId != null){
						List<ProAnniversaryType> lts = anniversaryTypeRepository.findByAnniversaryId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pa.setSessions(obj.get("sessions").toString());
				pa.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pa.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pa.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pa.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				anniversaryRepository.save(pa);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProAnniversary> list = anniversaryRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProAnniversary p = list.get(0);
				List<ProAnniversaryType> ts = anniversaryTypeRepository.findByAnniversaryId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setAnniversaryType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("anniversaryId", pa.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editAnniversaryType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editAnniversaryType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int anniversaryId = 0;
		ProAnniversaryType pat = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			anniversaryId = Integer.parseInt(obj.get("anniversaryId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pat = anniversaryTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pat.setUpdateDate(new Date());
				pat.setUpdateUserId(11111);
			}else{
				pat = new ProAnniversaryType();
				pat.setQuantity(0);
				pat.setCreateDate(new Date());
				pat.setCreateUserId(11111);
				
				List<ProAnniversaryType> lit = anniversaryTypeRepository.findByCodeName(anniversaryId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pat.setAnniversaryId(anniversaryId);
				pat.setCode(obj.get("code").toString());
				pat.setName(obj.get("name").toString());
				pat.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				pat.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				anniversaryTypeRepository.save(pat);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProAnniversaryType> list = anniversaryTypeRepository.findByAnniversaryId(anniversaryId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByAnniversaryId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByAnniversaryId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProAnniversaryType> ts = anniversaryTypeRepository.findByAnniversaryId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importAnniversaryType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importAnniversaryType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = anniversaryTypeRepository.findByMaxAnniversaryId();
		if(maxId != null){
			List<ProAnniversaryType> lts = anniversaryTypeRepository.findByAnniversaryId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProAnniversaryType p : lts) {
					ProAnniversaryType type = new ProAnniversaryType();
					type.setAnniversaryId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					anniversaryTypeRepository.save(type);
				}
			}
		}
		
		List<ProAnniversary> list = anniversaryRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProAnniversary p = list.get(0);
			List<ProAnniversaryType> ts = anniversaryTypeRepository.findByAnniversaryId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setAnniversaryType(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 梁皇寶懺
	 * @return
	 */
	@RequestMapping(value = "/lianghuang", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> lianghuang(){
		List<ProLianghuang> list = lianghuangRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProLianghuang pl = list.get(0);
			List<ProLianghuangType> ts = lianghuangTypeRepository.findByLianghuangId(pl.getId());
			if(ts != null && ts.size() > 0){
				pl.setLianghuangType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editLianghuang", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editLianghuang(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProLianghuang pl = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pl = lianghuangRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pl.setUpdateDate(new Date());
				pl.setUpdateUserId(11111);
			}else{
				pl = new ProLianghuang();
				pl.setCreateDate(new Date());
				pl.setCreateUserId(11111);
				
				ProLianghuang li = lianghuangRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = lianghuangRepository.findByMaxId();
					if(maxId != null){
						List<ProLianghuangType> lts = lianghuangTypeRepository.findByLianghuangId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pl.setSessions(obj.get("sessions").toString());
				pl.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pl.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pl.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pl.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				lianghuangRepository.save(pl);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProLianghuang> list = lianghuangRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProLianghuang p = list.get(0);
				List<ProLianghuangType> ts = lianghuangTypeRepository.findByLianghuangId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setLianghuangType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("lianghuangId", pl.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editLianghuangType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editLianghuangType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int lianghuangId = 0;
		ProLianghuangType plt = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lianghuangId = Integer.parseInt(obj.get("lianghuangId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				plt = lianghuangTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				plt.setUpdateDate(new Date());
				plt.setUpdateUserId(11111);
			}else{
				plt = new ProLianghuangType();
				plt.setQuantity(0);
				plt.setCreateDate(new Date());
				plt.setCreateUserId(11111);
				
				List<ProLianghuangType> lit = lianghuangTypeRepository.findByCodeName(lianghuangId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				plt.setLianghuangId(lianghuangId);
				plt.setCode(obj.get("code").toString());
				plt.setName(obj.get("name").toString());
				plt.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				plt.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				lianghuangTypeRepository.save(plt);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProLianghuangType> list = lianghuangTypeRepository.findByLianghuangId(lianghuangId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByLianghuangId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByLianghuangId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProLianghuangType> ts = lianghuangTypeRepository.findByLianghuangId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importLianghuangType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importLianghuangType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = lianghuangTypeRepository.findByMaxLianghuangId();
		if(maxId != null){
			List<ProLianghuangType> lts = lianghuangTypeRepository.findByLianghuangId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProLianghuangType p : lts) {
					ProLianghuangType type = new ProLianghuangType();
					type.setLianghuangId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					lianghuangTypeRepository.save(type);
				}
			}
		}
		
		List<ProLianghuang> list = lianghuangRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProLianghuang p = list.get(0);
			List<ProLianghuangType> ts = lianghuangTypeRepository.findByLianghuangId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setLianghuangType(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 解冤法會
	 * @return
	 */
	@RequestMapping(value = "/jieyuan", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> jieyuan(){
		List<ProJieyuan> list = jieyuanRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProJieyuan pj = list.get(0);
			List<ProJieyuanPrice> ts = jieyuanPriceRepository.findByJieyuanId(pj.getId());
			if(ts != null && ts.size() > 0){
				pj.setJieyuanPrice(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editJieyuan", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editJieyuan(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProJieyuan pj = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pj = jieyuanRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pj.setUpdateDate(new Date());
				pj.setUpdateUserId(11111);
			}else{
				pj = new ProJieyuan();
				pj.setQuantity(0);
				pj.setCreateDate(new Date());
				pj.setCreateUserId(11111);
				
				ProJieyuan li = jieyuanRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = jieyuanRepository.findByMaxId();
					if(maxId != null){
						List<ProJieyuanPrice> lts = jieyuanPriceRepository.findByJieyuanId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pj.setSessions(!StringUtils.isEmpty(obj.get("sessions"))?obj.get("sessions").toString().replaceAll("/", "."):null);
				pj.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pj.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pj.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				pj.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pj.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				jieyuanRepository.save(pj);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProJieyuan> list = jieyuanRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProJieyuan p = list.get(0);
				List<ProJieyuanPrice> ts = jieyuanPriceRepository.findByJieyuanId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setJieyuanPrice(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("jieyuanId", pj.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editJieyuanPrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editJieyuanPrice(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int jieyuanId = 0;
		ProJieyuanPrice pjp = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			jieyuanId = Integer.parseInt(obj.get("jieyuanId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pjp = jieyuanPriceRepository.findOne(Integer.parseInt(obj.get("id").toString()));
			}else{
				pjp = new ProJieyuanPrice();
				
				List<ProJieyuanPrice> lit = jieyuanPriceRepository.findByCodeName(jieyuanId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pjp.setJieyuanId(jieyuanId);
				pjp.setCode(obj.get("code").toString());
				pjp.setName(obj.get("name").toString());
				pjp.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				jieyuanPriceRepository.save(pjp);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProJieyuanPrice> list = jieyuanPriceRepository.findByJieyuanId(jieyuanId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByJieyuanId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByJieyuanId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProJieyuanPrice> ts = jieyuanPriceRepository.findByJieyuanId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importJieyuanPrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importJieyuanPrice(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = jieyuanPriceRepository.findByMaxJieyuanId();
		if(maxId != null){
			List<ProJieyuanPrice> lts = jieyuanPriceRepository.findByJieyuanId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProJieyuanPrice p : lts) {
					ProJieyuanPrice type = new ProJieyuanPrice();
					type.setJieyuanId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setPrice(p.getPrice());
					jieyuanPriceRepository.save(type);
				}
			}
		}
		
		List<ProJieyuan> list = jieyuanRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProJieyuan p = list.get(0);
			List<ProJieyuanPrice> ts = jieyuanPriceRepository.findByJieyuanId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setJieyuanPrice(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 補財庫
	 * @return
	 */
	@RequestMapping(value = "/bucaiku", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> bucaiku(){
		List<ProBucaiku> list = bucaikuRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProBucaiku pb = list.get(0);
			List<ProBucaikuPrice> ts = bucaikuPriceRepository.findByBucaikuId(pb.getId());
			if(ts != null && ts.size() > 0){
				pb.setBucaikuPrice(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editBucaiku", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editBucaiku(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProBucaiku pb = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pb = bucaikuRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pb.setUpdateDate(new Date());
				pb.setUpdateUserId(11111);
			}else{
				pb = new ProBucaiku();
				pb.setQuantity(0);
				pb.setCreateDate(new Date());
				pb.setCreateUserId(11111);
				
				ProBucaiku li = bucaikuRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = bucaikuRepository.findByMaxId();
					if(maxId != null){
						List<ProBucaikuPrice> lts = bucaikuPriceRepository.findByBucaikuId(maxId);
						if(lts != null && lts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pb.setSessions(!StringUtils.isEmpty(obj.get("sessions"))?obj.get("sessions").toString().replaceAll("/", "."):null);
				pb.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pb.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pb.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				pb.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pb.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				bucaikuRepository.save(pb);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProBucaiku> list = bucaikuRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProBucaiku p = list.get(0);
				List<ProBucaikuPrice> ts = bucaikuPriceRepository.findByBucaikuId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setBucaikuPrice(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("bucaikuId", pb.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editBucaikuPrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editBucaikuPrice(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int bucaikuId = 0;
		ProBucaikuPrice pbp = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			bucaikuId = Integer.parseInt(obj.get("bucaikuId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pbp = bucaikuPriceRepository.findOne(Integer.parseInt(obj.get("id").toString()));
			}else{
				pbp = new ProBucaikuPrice();
				
				List<ProBucaikuPrice> lit = bucaikuPriceRepository.findByCodeName(bucaikuId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pbp.setBucaikuId(bucaikuId);
				pbp.setCode(obj.get("code").toString());
				pbp.setName(obj.get("name").toString());
				pbp.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				bucaikuPriceRepository.save(pbp);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProBucaikuPrice> list = bucaikuPriceRepository.findByBucaikuId(bucaikuId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByBucaikuId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByBucaikuId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProBucaikuPrice> ts = bucaikuPriceRepository.findByBucaikuId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importBucaikuPrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importBucaikuPrice(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = bucaikuPriceRepository.findByMaxBucaikuId();
		if(maxId != null){
			List<ProBucaikuPrice> lts = bucaikuPriceRepository.findByBucaikuId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProBucaikuPrice p : lts) {
					ProBucaikuPrice type = new ProBucaikuPrice();
					type.setBucaikuId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setPrice(p.getPrice());
					bucaikuPriceRepository.save(type);
				}
			}
		}
		
		List<ProBucaiku> list = bucaikuRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProBucaiku p = list.get(0);
			List<ProBucaikuPrice> ts = bucaikuPriceRepository.findByBucaikuId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setBucaikuPrice(ts);
			}
		}
		message = "匯入完成";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 物品捐獻
	 * @return
	 */
	@RequestMapping(value = "/donateItems", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> donateItems(){
		Sort sort = new Sort(Direction.DESC, "id");
		List<ProDonateItems> list = donateItemsRepository.findAll(sort);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editDonateItems", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editDonateItems(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProDonateItems pdi = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pdi = donateItemsRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pdi.setUpdateDate(new Date());
				pdi.setUpdateUserId(11111);
			}else{
				pdi = new ProDonateItems();
				pdi.setCreateDate(new Date());
				pdi.setCreateUserId(11111);
				
				ProDonateItems don = donateItemsRepository.findBySummary(obj.get("summary").toString());
				if(don != null){
					status = "error";
					message = "摘要重覆";
				}
			}
			
			if(!"error".equals(status)){
				pdi.setSummary(obj.get("summary").toString());
				donateItemsRepository.save(pdi);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Sort sort = new Sort(Direction.DESC, "id");
		List<ProDonateItems> list = donateItemsRepository.findAll(sort);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	private String getToday(){
		Calendar cal = Calendar.getInstance();
		String yy = (cal.get(Calendar.YEAR)-1911)+"";
		String mm = String.format("%02d", cal.get(Calendar.MONTH)+1);
		String dd = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
		return yy+mm+dd;
	}
}