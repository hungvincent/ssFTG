package com.ftg.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ftg.dao.entity.ProConstruction;
import com.ftg.dao.entity.ProDonation;
import com.ftg.dao.entity.ProGodStar;
import com.ftg.dao.entity.ProGodStarType;
import com.ftg.dao.entity.ProLight;
import com.ftg.dao.entity.ProLightType;
import com.ftg.dao.entity.ProPilgrimage;
import com.ftg.dao.entity.ProPilgrimageType;
import com.ftg.dao.entity.ProStopResolve;
import com.ftg.dao.entity.ProStopResolvePrice;
import com.ftg.dao.entity.ProWenchan;
import com.ftg.dao.entity.ProWenchanType;
import com.ftg.repository.ProConstructionRepository;
import com.ftg.repository.ProDonationRepository;
import com.ftg.repository.ProGodStarRepository;
import com.ftg.repository.ProGodStarTypeRepository;
import com.ftg.repository.ProLightRepository;
import com.ftg.repository.ProLightTypeRepository;
import com.ftg.repository.ProPilgrimageRepository;
import com.ftg.repository.ProPilgrimageTypeRepository;
import com.ftg.repository.ProStopResolvePriceRepository;
import com.ftg.repository.ProStopResolveRepository;
import com.ftg.repository.ProWenchanRepository;
import com.ftg.repository.ProWenchanTypeRepository;

@Controller
public class ParamsController {

	@Autowired
	private ProDonationRepository donationRepository;
	
	@Autowired
	private ProConstructionRepository constructionRepository;
	
	@Autowired
	private ProLightRepository lightRepository;
	
	@Autowired
	private ProLightTypeRepository lightTypeRepository;
	
	@Autowired
	private ProGodStarRepository godStarRepository;
	
	@Autowired
	private ProGodStarTypeRepository godStarTypeRepository;
	
	@Autowired
	private ProStopResolveRepository stopResolveRepository;
	
	@Autowired
	private ProStopResolvePriceRepository stopResolvePriceRepository;
	
	@Autowired
	private ProPilgrimageRepository pilgrimageRepository;
	
	@Autowired
	private ProPilgrimageTypeRepository pilgrimageTypeRepository;
	
	@Autowired
	private ProWenchanRepository wenchanRepository;
	
	@Autowired
	private ProWenchanTypeRepository wenchanTypeRepository;
	
	
	
	@RequestMapping(value = "/params", method=RequestMethod.GET)
	public ModelAndView params(){
		ModelAndView model = new ModelAndView();
		model.setViewName("params");
	    return model;
	}
	

	/**
	 * 油香
	 * @return
	 */
	@RequestMapping(value = "/donation", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> donation(){
		List<ProDonation> list = donationRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editDonation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editDonation(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProDonation pd = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pd = donationRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pd.setUpdateDate(new Date());
				pd.setUpdateUserId(11111);
			}else{
				pd = new ProDonation();
				pd.setCreateDate(new Date());
				pd.setCreateUserId(11111);

				ProDonation don = donationRepository.findBySummary(obj.get("summary").toString());
				if(don != null){
					status = "error";
					message = "摘要重覆";
				}
			}
			
			if(!"error".equals(status)){
				pd.setSummary(obj.get("summary").toString());
				pd.setPrice(!StringUtils.isEmpty(obj.get("price"))?Integer.parseInt(obj.get("price").toString()):null);
				pd.setInventory(!StringUtils.isEmpty(obj.get("inventory"))?Integer.parseInt(obj.get("inventory").toString()):null);
				pd.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pd.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				donationRepository.save(pd);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProDonation> list = donationRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 建設
	 * @return
	 */
	@RequestMapping(value = "/construction", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> construction(){
		List<ProConstruction> list = constructionRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editConstruction", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editConstruction(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProConstruction pc = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pc = constructionRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pc.setUpdateDate(new Date());
				pc.setUpdateUserId(11111);
			}else{
				pc = new ProConstruction();
				pc.setCreateDate(new Date());
				pc.setCreateUserId(11111);
				
				ProConstruction con = constructionRepository.findBySummary(obj.get("summary").toString());
				if(con != null){
					status = "error";
					message = "摘要重覆";
				}
			}
			
			if(!"error".equals(status)){
				pc.setSummary(obj.get("summary").toString());
				pc.setPrice(!StringUtils.isEmpty(obj.get("price"))?Integer.parseInt(obj.get("price").toString()):null);
				pc.setInventory(!StringUtils.isEmpty(obj.get("inventory"))?Integer.parseInt(obj.get("inventory").toString()):null);
				pc.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pc.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				constructionRepository.save(pc);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProConstruction> list = constructionRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", list);
	    return map;
	}
	
	
	/**
	 * 點燈
	 * @return
	 */
	@RequestMapping(value = "/light", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> light(){
		List<ProLight> list = lightRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProLight pl = list.get(0);
			List<ProLightType> ts = lightTypeRepository.findByLightId(pl.getId());
			if(ts != null && ts.size() > 0){
				pl.setLightType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editLight", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editLight(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProLight pl = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pl = lightRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pl.setUpdateDate(new Date());
				pl.setUpdateUserId(11111);
			}else{
				pl = new ProLight();
				pl.setCreateDate(new Date());
				pl.setCreateUserId(11111);
				
				ProLight li = lightRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = lightRepository.findByMaxId();
					if(maxId != null){
						List<ProLightType> lts = lightTypeRepository.findByLightId(maxId);
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
				lightRepository.save(pl);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProLight> list = lightRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProLight p = list.get(0);
				List<ProLightType> ts = lightTypeRepository.findByLightId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setLightType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("lightId", pl.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editLightType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editLightType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int lightId = 0;
		ProLightType plt = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lightId = Integer.parseInt(obj.get("lightId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				plt = lightTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				plt.setUpdateDate(new Date());
				plt.setUpdateUserId(11111);
			}else{
				plt = new ProLightType();
				plt.setQuantity(0);
				plt.setCreateDate(new Date());
				plt.setCreateUserId(11111);
				
				List<ProLightType> lit = lightTypeRepository.findByCodeName(lightId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				plt.setLightId(lightId);
				plt.setCode(obj.get("code").toString());
				plt.setName(obj.get("name").toString());
				plt.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				plt.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				lightTypeRepository.save(plt);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProLightType> list = lightTypeRepository.findByLightId(lightId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByLightId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByLightId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProLightType> ts = lightTypeRepository.findByLightId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importLightType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importLightType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = lightTypeRepository.findByMaxLightId();
		if(maxId != null){
			List<ProLightType> lts = lightTypeRepository.findByLightId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProLightType p : lts) {
					ProLightType type = new ProLightType();
					type.setLightId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					lightTypeRepository.save(type);
				}
			}
		}
		
		List<ProLight> list = lightRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProLight pl = list.get(0);
			List<ProLightType> ts = lightTypeRepository.findByLightId(pl.getId());
			if(ts != null && ts.size() > 0){
				pl.setLightType(ts);
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
	 * 玉皇上帝禮斗
	 * @return
	 */
	@RequestMapping(value = "/godStar", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> godStar(){
		List<ProGodStar> list = godStarRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProGodStar pg = list.get(0);
			List<ProGodStarType> ts = godStarTypeRepository.findByGodStarId(pg.getId());
			if(ts != null && ts.size() > 0){
				pg.setGodStarType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editGodStar", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editGodStar(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProGodStar pg = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pg = godStarRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pg.setUpdateDate(new Date());
				pg.setUpdateUserId(11111);
			}else{
				pg = new ProGodStar();
				pg.setCreateDate(new Date());
				pg.setCreateUserId(11111);
				
				ProGodStar li = godStarRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = godStarRepository.findByMaxId();
					if(maxId != null){
						List<ProGodStarType> gss = godStarTypeRepository.findByGodStarId(maxId);
						if(gss != null && gss.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pg.setSessions(obj.get("sessions").toString());
				pg.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pg.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pg.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pg.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				godStarRepository.save(pg);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProGodStar> list = godStarRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProGodStar p = list.get(0);
				List<ProGodStarType> ts = godStarTypeRepository.findByGodStarId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setGodStarType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("godStarId", pg.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editGodStarType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editGodStarType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int godStarId = 0;
		ProGodStarType pgst = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			godStarId = Integer.parseInt(obj.get("godStarId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pgst = godStarTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pgst.setUpdateDate(new Date());
				pgst.setUpdateUserId(11111);
			}else{
				pgst = new ProGodStarType();
				pgst.setQuantity(0);
				pgst.setCreateDate(new Date());
				pgst.setCreateUserId(11111);
				
				List<ProGodStarType> lit = godStarTypeRepository.findByCodeName(godStarId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pgst.setGodStarId(godStarId);
				pgst.setCode(obj.get("code").toString());
				pgst.setName(obj.get("name").toString());
				pgst.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				pgst.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				godStarTypeRepository.save(pgst);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProGodStarType> list = godStarTypeRepository.findByGodStarId(godStarId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByGodStarId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByGodStarId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProGodStarType> ts = godStarTypeRepository.findByGodStarId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importGodStarType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importGodStarType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = godStarTypeRepository.findByMaxGodStarId();
		if(maxId != null){
			List<ProGodStarType> lts = godStarTypeRepository.findByGodStarId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProGodStarType p : lts) {
					ProGodStarType type = new ProGodStarType();
					type.setGodStarId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					type.setCreateDate(new Date());
					type.setCreateUserId(11111);
					godStarTypeRepository.save(type);
				}
			}
		}
		
		List<ProGodStar> list = godStarRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProGodStar pg = list.get(0);
			List<ProGodStarType> ts = godStarTypeRepository.findByGodStarId(pg.getId());
			if(ts != null && ts.size() > 0){
				pg.setGodStarType(ts);
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
	 * 制解
	 * @return
	 */
	@RequestMapping(value = "/stopResolve", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> stopResolve(){
		List<ProStopResolve> list = stopResolveRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProStopResolve psr = list.get(0);
			List<ProStopResolvePrice> ts = stopResolvePriceRepository.findByStopResolveId(psr.getId());
			if(ts != null && ts.size() > 0){
				psr.setStopResolvePrice(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editStopResolve", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editStopResolve(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProStopResolve psr = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				psr = stopResolveRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				psr.setUpdateDate(new Date());
				psr.setUpdateUserId(11111);
			}else{
				psr = new ProStopResolve();
				psr.setGroupQuantity(0);
				psr.setCreateDate(new Date());
				psr.setCreateUserId(11111);
				
				ProStopResolve li = stopResolveRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = stopResolveRepository.findByMaxId();
					if(maxId != null){
						List<ProStopResolvePrice> psrs = stopResolvePriceRepository.findByStopResolveId(maxId);
						if(psrs != null && psrs.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				psr.setSessions(!StringUtils.isEmpty(obj.get("sessions"))?obj.get("sessions").toString().replaceAll("/", "."):null);
				psr.setGroupInventory(!StringUtils.isEmpty(obj.get("groupQty"))?Integer.parseInt(obj.get("groupQty").toString()):null);
				psr.setGroupPrice(!StringUtils.isEmpty(obj.get("groupPrice"))?Integer.parseInt(obj.get("groupPrice").toString()):null);
				psr.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				psr.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				psr.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				psr.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				stopResolveRepository.save(psr);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProStopResolve> list = stopResolveRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProStopResolve p = list.get(0);
				List<ProStopResolvePrice> ts = stopResolvePriceRepository.findByStopResolveId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setStopResolvePrice(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("stopResolveId", psr.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editStopResolvePrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editStopResolvePrice(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int stopResolveId = 0;
		ProStopResolvePrice psrp = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			stopResolveId = Integer.parseInt(obj.get("stopResolveId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				psrp = stopResolvePriceRepository.findOne(Integer.parseInt(obj.get("id").toString()));
			}else{
				psrp = new ProStopResolvePrice();
				
				List<ProStopResolvePrice> lit = stopResolvePriceRepository.findByCodeName(stopResolveId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				psrp.setStopResolveId(stopResolveId);
				psrp.setCode(obj.get("code").toString());
				psrp.setName(obj.get("name").toString());
				psrp.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				stopResolvePriceRepository.save(psrp);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProStopResolvePrice> list = stopResolvePriceRepository.findByStopResolveId(stopResolveId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByStopResolveId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByStopResolveId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProStopResolvePrice> ts = stopResolvePriceRepository.findByStopResolveId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importStopResolvePrice", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importStopResolvePrice(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = stopResolvePriceRepository.findByMaxStopResolveId();
		if(maxId != null){
			List<ProStopResolvePrice> lts = stopResolvePriceRepository.findByStopResolveId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProStopResolvePrice p : lts) {
					ProStopResolvePrice type = new ProStopResolvePrice();
					type.setStopResolveId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setPrice(p.getPrice());
					stopResolvePriceRepository.save(type);
				}
			}
		}
		
		List<ProStopResolve> list = stopResolveRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProStopResolve p = list.get(0);
			List<ProStopResolvePrice> ts = stopResolvePriceRepository.findByStopResolveId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setStopResolvePrice(ts);
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
	 * 進香
	 * @return
	 */
	@RequestMapping(value = "/pilgrimage", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> pilgrimage(){
		List<ProPilgrimage> list = pilgrimageRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPilgrimage pp = list.get(0);
			List<ProPilgrimageType> ts = pilgrimageTypeRepository.findByPilgrimageId(pp.getId());
			if(ts != null && ts.size() > 0){
				pp.setPilgrimageType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editPilgrimage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPilgrimage(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProPilgrimage pp = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pp = pilgrimageRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pp.setUpdateDate(new Date());
				pp.setUpdateUserId(11111);
			}else{
				pp = new ProPilgrimage();
				pp.setCreateDate(new Date());
				pp.setCreateUserId(11111);
				
				ProPilgrimage li = pilgrimageRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = pilgrimageRepository.findByMaxId();
					if(maxId != null){
						List<ProPilgrimageType> ppts = pilgrimageTypeRepository.findByPilgrimageId(maxId);
						if(ppts != null && ppts.size() > 0){
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
				pilgrimageRepository.save(pp);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProPilgrimage> list = pilgrimageRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProPilgrimage p = list.get(0);
				List<ProPilgrimageType> ts = pilgrimageTypeRepository.findByPilgrimageId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setPilgrimageType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("pilgrimageId", pp.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editPilgrimageType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editPilgrimageType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int pilgrimageId = 0;
		ProPilgrimageType ppt = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			pilgrimageId = Integer.parseInt(obj.get("pilgrimageId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				ppt = pilgrimageTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				ppt.setUpdateDate(new Date());
				ppt.setUpdateUserId(11111);
			}else{
				ppt = new ProPilgrimageType();
				ppt.setQuantity(0);
				ppt.setCreateDate(new Date());
				ppt.setCreateUserId(11111);
				
				List<ProPilgrimageType> lit = pilgrimageTypeRepository.findByCodeName(pilgrimageId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				ppt.setPilgrimageId(pilgrimageId);
				ppt.setCode(obj.get("code").toString());
				ppt.setName(obj.get("name").toString());
				ppt.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				ppt.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				pilgrimageTypeRepository.save(ppt);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProPilgrimageType> list = pilgrimageTypeRepository.findByPilgrimageId(pilgrimageId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByPilgrimageId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByPilgrimageId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProPilgrimageType> ts = pilgrimageTypeRepository.findByPilgrimageId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importPilgrimageType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importPilgrimageType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = pilgrimageTypeRepository.findByMaxPilgrimageId();
		if(maxId != null){
			List<ProPilgrimageType> lts = pilgrimageTypeRepository.findByPilgrimageId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProPilgrimageType p : lts) {
					ProPilgrimageType type = new ProPilgrimageType();
					type.setPilgrimageId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					pilgrimageTypeRepository.save(type);
				}
			}
		}
		
		List<ProPilgrimage> list = pilgrimageRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProPilgrimage p = list.get(0);
			List<ProPilgrimageType> ts = pilgrimageTypeRepository.findByPilgrimageId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setPilgrimageType(ts);
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
	 * 文昌法會
	 * @return
	 */
	@RequestMapping(value = "/wenchan", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> wenchan(){
		List<ProWenchan> list = wenchanRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProWenchan pw = list.get(0);
			List<ProWenchanType> ts = wenchanTypeRepository.findByWenchanId(pw.getId());
			if(ts != null && ts.size() > 0){
				pw.setWenchanType(ts);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/editWenchan", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editWenchan(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		ProWenchan pw = null;
		String types = "N";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			if(!StringUtils.isEmpty(obj.get("id"))){
				pw = wenchanRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				pw.setUpdateDate(new Date());
				pw.setUpdateUserId(11111);
			}else{
				pw = new ProWenchan();
				pw.setCreateDate(new Date());
				pw.setCreateUserId(11111);
				
				ProWenchan li = wenchanRepository.findBySessions(obj.get("sessions").toString());
				if(li != null){
					status = "error";
					message = "場次重覆";
				}else{
					Integer maxId = wenchanRepository.findByMaxId();
					if(maxId != null){
						List<ProWenchanType> ppts = wenchanTypeRepository.findByWenchanId(maxId);
						if(ppts != null && ppts.size() > 0){
							message = "是否匯入前期種類";
							types = "Y";
						}
					}
				}
			}
			
			if(!"error".equals(status)){
				pw.setSessions(obj.get("sessions").toString());
				pw.setStartRegDate(!StringUtils.isEmpty(obj.get("startDate"))?obj.get("startDate").toString().replaceAll("/", "."):null);
				pw.setEndRegDate(!StringUtils.isEmpty(obj.get("endDate"))?obj.get("endDate").toString().replaceAll("/", "."):null);
				pw.setEventStart(!StringUtils.isEmpty(obj.get("eventStart"))?obj.get("eventStart").toString().replaceAll("/", "."):null);
				pw.setEventEnd(!StringUtils.isEmpty(obj.get("eventEnd"))?obj.get("eventEnd").toString().replaceAll("/", "."):null);
				wenchanRepository.save(pw);
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if("success".equals(status) && "N".equals(types)){
			message = "設定成功";
			
			List<ProWenchan> list = wenchanRepository.findByEndRegDate(this.getToday());
			if(list != null && list.size() > 0){
				ProWenchan p = list.get(0);
				List<ProWenchanType> ts = wenchanTypeRepository.findByWenchanId(p.getId());
				if(ts != null && ts.size() > 0){
					p.setWenchanType(ts);
				}
			}
			map.put("list", list);
		}
		map.put("status", status);
		map.put("message", message);
		map.put("types", types);
		map.put("wenchanId", pw.getId());
	    return map;
	}
	
	@RequestMapping(value = "/editWenchanType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editWenchanType(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int wenchanId = 0;
		ProWenchanType pw = null;
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			wenchanId = Integer.parseInt(obj.get("wenchanId").toString());
			
			if(!StringUtils.isEmpty(obj.get("id"))){
				pw = wenchanTypeRepository.findOne(Integer.parseInt(obj.get("id").toString()));
			}else{
				pw = new ProWenchanType();
				pw.setQuantity(0);
				
				List<ProWenchanType> lit = wenchanTypeRepository.findByCodeName(wenchanId, obj.get("code").toString(), obj.get("name").toString());
				if(lit != null && lit.size() > 0){
					status = "error";
					message = "代碼or種類重覆";
				}
			}
			
			if(!"error".equals(status)){
				pw.setWenchanId(wenchanId);
				pw.setCode(obj.get("code").toString());
				pw.setName(obj.get("name").toString());
				pw.setInventory(!StringUtils.isEmpty(obj.get("qty").toString())?Integer.parseInt(obj.get("qty").toString()):0);
				pw.setPrice(!StringUtils.isEmpty(obj.get("price").toString())?Integer.parseInt(obj.get("price").toString()):0);
				wenchanTypeRepository.save(pw);
				message = "設定成功";
			}
		}else{
			status = "error";
			message = "傳入的值為空白";
		}
		List<ProWenchanType> list = wenchanTypeRepository.findByWenchanId(wenchanId);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("types", list);
	    return map;
	}
	
	@RequestMapping(value = "/queryTypeByWenchanId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryTypeByWenchanId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<ProWenchanType> ts = wenchanTypeRepository.findByWenchanId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("list", ts);
	    return map;
	}
	
	@RequestMapping(value = "/importWenchanType", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> importWenchanType(@RequestParam("id")int id, HttpServletRequest request) throws Exception {
		String status = "success", message = "";

		Integer maxId = wenchanTypeRepository.findByMaxWenchanId();
		if(maxId != null){
			List<ProWenchanType> lts = wenchanTypeRepository.findByWenchanId(maxId);
			if(lts != null && lts.size() > 0){
				for (ProWenchanType p : lts) {
					ProWenchanType type = new ProWenchanType();
					type.setWenchanId(id);
					type.setCode(p.getCode());
					type.setName(p.getName());
					type.setInventory(p.getInventory());
					type.setQuantity(0);
					type.setPrice(p.getPrice());
					wenchanTypeRepository.save(type);
				}
			}
		}
		
		List<ProWenchan> list = wenchanRepository.findByEndRegDate(this.getToday());
		if(list != null && list.size() > 0){
			ProWenchan p = list.get(0);
			List<ProWenchanType> ts = wenchanTypeRepository.findByWenchanId(p.getId());
			if(ts != null && ts.size() > 0){
				p.setWenchanType(ts);
			}
		}
		message = "匯入完成";
		
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