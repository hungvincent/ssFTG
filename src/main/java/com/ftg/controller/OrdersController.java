package com.ftg.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
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
import org.springframework.web.servlet.ModelAndView;

import com.ftg.dao.Params;
import com.ftg.dao.entity.OfferingBox;
import com.ftg.dao.entity.OfferingBoxDetail;
import com.ftg.dao.entity.Orders;
import com.ftg.dao.entity.OrdersAnniversary;
import com.ftg.dao.entity.OrdersBucaiku;
import com.ftg.dao.entity.OrdersChaodue;
import com.ftg.dao.entity.OrdersConstruction;
import com.ftg.dao.entity.OrdersDonateGold;
import com.ftg.dao.entity.OrdersDonateItems;
import com.ftg.dao.entity.OrdersDonation;
import com.ftg.dao.entity.OrdersGodStar;
import com.ftg.dao.entity.OrdersJieyuan;
import com.ftg.dao.entity.OrdersLianghuang;
import com.ftg.dao.entity.OrdersLight;
import com.ftg.dao.entity.OrdersPilgrimage;
import com.ftg.dao.entity.OrdersPrinceStar;
import com.ftg.dao.entity.OrdersPurdue;
import com.ftg.dao.entity.OrdersStopResolve;
import com.ftg.dao.entity.OrdersWenchan;
import com.ftg.dao.entity.ProAnniversary;
import com.ftg.dao.entity.ProAnniversaryType;
import com.ftg.dao.entity.ProBucaiku;
import com.ftg.dao.entity.ProBucaikuPrice;
import com.ftg.dao.entity.ProChaodue;
import com.ftg.dao.entity.ProChaodueType;
import com.ftg.dao.entity.ProConstruction;
import com.ftg.dao.entity.ProDonateItems;
import com.ftg.dao.entity.ProDonation;
import com.ftg.dao.entity.ProGodStar;
import com.ftg.dao.entity.ProGodStarType;
import com.ftg.dao.entity.ProJieyuan;
import com.ftg.dao.entity.ProJieyuanPrice;
import com.ftg.dao.entity.ProLianghuang;
import com.ftg.dao.entity.ProLianghuangType;
import com.ftg.dao.entity.ProLight;
import com.ftg.dao.entity.ProLightType;
import com.ftg.dao.entity.ProPilgrimage;
import com.ftg.dao.entity.ProPilgrimageType;
import com.ftg.dao.entity.ProPrinceStar;
import com.ftg.dao.entity.ProPrinceStarType;
import com.ftg.dao.entity.ProPurdue;
import com.ftg.dao.entity.ProPurdueType;
import com.ftg.dao.entity.ProStopResolve;
import com.ftg.dao.entity.ProStopResolvePrice;
import com.ftg.dao.entity.ProWenchan;
import com.ftg.dao.entity.ProWenchanType;
import com.ftg.repository.OfferingBoxDetailRepository;
import com.ftg.repository.OfferingBoxRepository;
import com.ftg.repository.OrdersAnniversaryRepository;
import com.ftg.repository.OrdersBucaikuRepository;
import com.ftg.repository.OrdersChaodueRepository;
import com.ftg.repository.OrdersConstructionRepository;
import com.ftg.repository.OrdersDonateGoldRepository;
import com.ftg.repository.OrdersDonateItemsRepository;
import com.ftg.repository.OrdersDonationRepository;
import com.ftg.repository.OrdersGodStarRepository;
import com.ftg.repository.OrdersJieyuanRepository;
import com.ftg.repository.OrdersLianghuangRepository;
import com.ftg.repository.OrdersLightRepository;
import com.ftg.repository.OrdersPilgrimageRepository;
import com.ftg.repository.OrdersPrinceStarRepository;
import com.ftg.repository.OrdersPurdueRepository;
import com.ftg.repository.OrdersRepository;
import com.ftg.repository.OrdersStopResolveRepository;
import com.ftg.repository.OrdersWenchanRepository;
import com.ftg.repository.ProAnniversaryRepository;
import com.ftg.repository.ProAnniversaryTypeRepository;
import com.ftg.repository.ProBucaikuPriceRepository;
import com.ftg.repository.ProBucaikuRepository;
import com.ftg.repository.ProChaodueRepository;
import com.ftg.repository.ProChaodueTypeRepository;
import com.ftg.repository.ProConstructionRepository;
import com.ftg.repository.ProDonateItemsRepository;
import com.ftg.repository.ProDonationRepository;
import com.ftg.repository.ProGodStarRepository;
import com.ftg.repository.ProGodStarTypeRepository;
import com.ftg.repository.ProJieyuanPriceRepository;
import com.ftg.repository.ProJieyuanRepository;
import com.ftg.repository.ProLianghuangRepository;
import com.ftg.repository.ProLianghuangTypeRepository;
import com.ftg.repository.ProLightRepository;
import com.ftg.repository.ProLightTypeRepository;
import com.ftg.repository.ProPilgrimageRepository;
import com.ftg.repository.ProPilgrimageTypeRepository;
import com.ftg.repository.ProPrinceStarRepository;
import com.ftg.repository.ProPrinceStarTypeRepository;
import com.ftg.repository.ProPurdueRepository;
import com.ftg.repository.ProPurdueTypeRepository;
import com.ftg.repository.ProStopResolvePriceRepository;
import com.ftg.repository.ProStopResolveRepository;
import com.ftg.repository.ProWenchanRepository;
import com.ftg.repository.ProWenchanTypeRepository;

@Controller
public class OrdersController {
	
	@Autowired
	private OfferingBoxRepository offeringBoxRepository;
	
	@Autowired
	private OfferingBoxDetailRepository offeringBoxDetailRepository;

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private OrdersDonationRepository orDonationRepository;
	
	@Autowired
	private ProDonationRepository donationRepository;
	
	@Autowired
	private OrdersConstructionRepository orConstructionRepository;
	
	@Autowired
	private ProConstructionRepository constructionRepository;
	
	@Autowired
	private OrdersLightRepository orLightRepository;
	
	@Autowired
	private ProLightRepository lightRepository;
	
	@Autowired
	private ProLightTypeRepository lightTypeRepository;
	
	@Autowired
	private OrdersGodStarRepository orGodStarRepository;
	
	@Autowired
	private ProGodStarRepository godStarRepository;
	
	@Autowired
	private ProGodStarTypeRepository godStarTypeRepository;
	
	@Autowired
	private OrdersStopResolveRepository orStopResolveRepository;
	
	@Autowired
	private ProStopResolveRepository stopResolveRepository;
	
	@Autowired
	private ProStopResolvePriceRepository stopResolvePriceRepository;
	
	@Autowired
	private OrdersPilgrimageRepository orPilgrimageRepository;
	
	@Autowired
	private ProPilgrimageRepository pilgrimageRepository;
	
	@Autowired
	private ProPilgrimageTypeRepository pilgrimageTypeRepository;
	
	@Autowired
	private OrdersWenchanRepository orWenchanRepository;
	
	@Autowired
	private ProWenchanRepository wenchanRepository;
	
	@Autowired
	private ProWenchanTypeRepository wenchanTypeRepository;
	
	@Autowired
	private OrdersPrinceStarRepository orPrinceStarRepository;
	
	@Autowired
	private ProPrinceStarRepository princeStarRepository;
	
	@Autowired
	private ProPrinceStarTypeRepository princeStarTypeRepository;
	
	@Autowired
	private OrdersPurdueRepository orPurdueRepository;
	
	@Autowired
	private ProPurdueRepository purdueRepository;
	
	@Autowired
	private ProPurdueTypeRepository purdueTypeRepository;
	
	@Autowired
	private OrdersChaodueRepository orChaodueRepository;
	
	@Autowired
	private ProChaodueRepository chaodueRepository;
	
	@Autowired
	private ProChaodueTypeRepository chaodueTypeRepository;
	
	@Autowired
	private OrdersAnniversaryRepository orAnniversaryRepository;
	
	@Autowired
	private ProAnniversaryRepository anniversaryRepository;
	
	@Autowired
	private ProAnniversaryTypeRepository anniversaryTypeRepository;
	
	@Autowired
	private OrdersLianghuangRepository orLianghuangRepository;
	
	@Autowired
	private ProLianghuangRepository lianghuangRepository;
	
	@Autowired
	private ProLianghuangTypeRepository lianghuangTypeRepository;
	
	@Autowired
	private OrdersJieyuanRepository orJieyuanRepository;
	
	@Autowired
	private ProJieyuanRepository jieyuanRepository;
	
	@Autowired
	private ProJieyuanPriceRepository jieyuanPriceRepository;
	
	@Autowired
	private OrdersBucaikuRepository orBucaikuRepository;
	
	@Autowired
	private ProBucaikuRepository bucaikuRepository;
	
	@Autowired
	private ProBucaikuPriceRepository bucaikuPriceRepository;
	
	@Autowired
	private OrdersDonateGoldRepository orDonateGoldRepository;
	
	@Autowired
	private OrdersDonateItemsRepository orDonateItemsRepository;
	
	@Autowired
	private ProDonateItemsRepository donateItemsRepository;
	
	
	
	/**
	 * 賽錢箱
	 * @return
	 */
	@RequestMapping(value = "/offeringbox", method=RequestMethod.GET)
	public ModelAndView offeringBox(){
		Sort sort = new Sort(Direction.DESC, "id");
		List<OfferingBox> obs = offeringBoxRepository.findAll(sort);
		
		ModelAndView model = new ModelAndView();
		model.addObject("obs", obs);
		model.setViewName("offeringbox");
	    return model;
	}
	
	
	@RequestMapping(value = "/editOfferingBox", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOfferingBox(@RequestParam("offeringBoxId")int offeringBoxId, @RequestParam("checkDate")String checkDate, 
									@RequestParam("checkName")String checkName, @RequestParam("total")int total, @RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				OfferingBox ob = null;
				if(offeringBoxId == 0){
					//收據編號
					String date = this.getToday().substring(0,5);
					String receiptNo = offeringBoxRepository.findByMaxNo(Params.OFFERING_BOX + date);
					if(StringUtils.isEmpty(receiptNo)){
						receiptNo = Params.OFFERING_BOX + date + "001";
					}else{
						int no = Integer.parseInt(receiptNo.replace(Params.OFFERING_BOX + date, ""))+1;
						receiptNo = Params.OFFERING_BOX + date + String.format("%03d", no);
					}
					
					ob = new OfferingBox();
					ob.setReceiptNo(receiptNo);
					ob.setTotalAmount(total);
					ob.setCheckName(checkName);
					ob.setCheckDate(checkDate);
					ob.setCreateDate(new Date());
					ob.setCreateUserId(11111);
					offeringBoxRepository.save(ob);
				}else{
					ob = offeringBoxRepository.findOne(offeringBoxId);
					
					if(ob != null){
						ob.setTotalAmount(total);
						offeringBoxRepository.save(ob);
						
						List<OfferingBoxDetail> list = offeringBoxDetailRepository.findByOfferingBoxId(ob.getId());
						for (OfferingBoxDetail obd : list) {
							offeringBoxDetailRepository.delete(obd);
						}
					}
				}
				
				JSONArray ary = new JSONArray(jsonStr);
				for(int i=0; i<ary.length(); i++){
					JSONObject obj = ary.getJSONObject(i);
					
					OfferingBoxDetail obd = new OfferingBoxDetail();
					obd.setOfferingBoxId(ob.getId());
					obd.setSummary(obj.getString("summary"));
					obd.setAmount(obj.getInt("amount"));
					offeringBoxDetailRepository.save(obd);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	@RequestMapping(value = "/queryDetailByBoxId", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryDetailByBoxId(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		OfferingBox ob = offeringBoxRepository.findOne(id);
		List<OfferingBoxDetail> list = offeringBoxDetailRepository.findByOfferingBoxId(id);
			
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("box", ob);
		map.put("list", list);
	    return map;
	}
	
	
	
	/**
	 * 油香
	 * @return
	 */
	@RequestMapping(value = "/getProDonation", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProDonation(){
		List<ProDonation> list = donationRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/getProDonationById", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProDonationById(@RequestParam("id")int id){
		ProDonation pd = donationRepository.findOne(id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("don", pd);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderDonation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderDonation(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				JSONArray ary = obj.getJSONArray("list");
				String preNo = Params.DONATION + "-" + this.getToday().substring(0,3) + "-00-";
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					
					String serialNo = orDonationRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersDonation od = new OrdersDonation();
					od.setOrdersId(o.getId());
					od.setSerialNo(serialNo);
					od.setCustomersId(Integer.parseInt(os.getString("custId")));
					od.setAge(Integer.parseInt(os.getString("age")));
					od.setAmount(Integer.parseInt(os.getString("amount")));
					od.setSummary(os.getString("summary"));
					orDonationRepository.save(od);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 建設
	 * @return
	 */
	@RequestMapping(value = "/getProConstruction", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProConstruction(){
		List<ProConstruction> list = constructionRepository.findByEndRegDate(this.getToday());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/getProConstructionById", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProConstructionById(@RequestParam("id")int id){
		ProConstruction pc = constructionRepository.findOne(id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("con", pc);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderConstruction", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderConstruction(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				String preNo = Params.CONSTRUCTION + "-" + this.getToday().substring(0,3) + "-00-";
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					
					String serialNo = orConstructionRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersConstruction oc = new OrdersConstruction();
					oc.setOrdersId(o.getId());
					oc.setSerialNo(serialNo);
					oc.setCustomersId(Integer.parseInt(os.getString("custId")));
					oc.setAge(Integer.parseInt(os.getString("age")));
					oc.setAmount(Integer.parseInt(os.getString("amount")));
					oc.setSummary(os.getString("summary"));
					orConstructionRepository.save(oc);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 點燈
	 * @return
	 */
	@RequestMapping(value = "/getProLight", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProLight(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProLight> list = lightRepository.findByEndRegDate(this.getToday());
		for (ProLight pl : list) {
			List<ProLightType> plt = lightTypeRepository.findByLightId(pl.getId());
			for (ProLightType type : plt) {
				mm.put(pl.getSessions()+"-"+type.getCode(), pl.getSessions() + type.getName() + ",剩餘"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProLightBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProLightBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProLight pl = lightRepository.findBySessions(session);
		ProLightType type = lightTypeRepository.findByCode(pl.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderLight", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderLight(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.LIGHT + "-" + sessions + "-";
					
					String serialNo = orLightRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersLight ol = new OrdersLight();
					ol.setOrdersId(o.getId());
					ol.setSerialNo(serialNo);
					ol.setCustomersId(Integer.parseInt(os.getString("custId")));
					ol.setAge(Integer.parseInt(os.getString("age")));
					ol.setAmount(Integer.parseInt(os.getString("amount")));
					orLightRepository.save(ol);
					
					//已出數量
					ProLight pl = lightRepository.findBySessions(sessions.split("-")[0]);
					ProLightType type = lightTypeRepository.findByCode(pl.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					lightTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 玉皇上帝禮斗
	 * @return
	 */
	@RequestMapping(value = "/getProGodStar", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProGodStar(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProGodStar> list = godStarRepository.findByEndRegDate(this.getToday());
		for (ProGodStar pgs : list) {
			List<ProGodStarType> pgst = godStarTypeRepository.findByGodStarId(pgs.getId());
			for (ProGodStarType type : pgst) {
				mm.put(pgs.getSessions()+"-"+type.getCode(), pgs.getSessions() + type.getName() + ",庫存"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProGodStarBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProGodStarBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProGodStar pgs = godStarRepository.findBySessions(session);
		ProGodStarType type = godStarTypeRepository.findByCode(pgs.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderGodStar", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderGodStar(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.GOD_STAR + "-" + sessions + "-";
					
					String serialNo = orGodStarRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersGodStar ogs = new OrdersGodStar();
					ogs.setOrdersId(o.getId());
					ogs.setSerialNo(serialNo);
					ogs.setCustomersId(Integer.parseInt(os.getString("custId")));
					ogs.setAge(Integer.parseInt(os.getString("age")));
					ogs.setAmount(Integer.parseInt(os.getString("amount")));
					ogs.setSummary(os.getString("summary"));
					orGodStarRepository.save(ogs);
					
					//已出數量
					ProGodStar pgs = godStarRepository.findBySessions(sessions.split("-")[0]);
					ProGodStarType type = godStarTypeRepository.findByCode(pgs.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					godStarTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 制解
	 * TODO
	 * @return
	 */
	@RequestMapping(value = "/getProStopResolve", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProStopResolve(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProStopResolve> list = stopResolveRepository.findByEndRegDate(this.getToday());
		for (ProStopResolve psr : list) {
			mm.put(psr.getSessions(), psr.getSessions() + ",剩"+(psr.getGroupInventory()-psr.getGroupQuantity())+"戶");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProStopResolveBygetSessions", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProStopResolveById(@RequestParam("session")String session){
		ProStopResolve sr = stopResolveRepository.findBySessions(session);
		List<ProStopResolvePrice> price = stopResolvePriceRepository.findByStopResolveId(sr.getId());
		Map<String, Integer> pm = new LinkedHashMap<String, Integer>();
		for (ProStopResolvePrice p : price) {
			pm.put(p.getName(), p.getPrice());
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("sr", sr);
		map.put("pm", pm);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderStopResolve", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderStopResolve(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String n[] = os.getString("sessions").split("\\.");
					String preNo = Params.STOP_RESOLVE + "-" + n[0] + "-" + n[1]+n[2] + "-";
					
					String serialNo = orStopResolveRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersStopResolve osr = new OrdersStopResolve();
					osr.setOrdersId(o.getId());
					osr.setSerialNo(serialNo);
					osr.setCustomersId(Integer.parseInt(os.getString("custId")));
					osr.setAge(Integer.parseInt(os.getString("age")));
					osr.setAmount(Integer.parseInt(os.getString("amount")));
					osr.setStopResolve(os.getString("stopResolve"));
					orStopResolveRepository.save(osr);
					
					//已出數量
					ProStopResolve psr = stopResolveRepository.findBySessions(sessions);
					psr.setGroupQuantity(psr.getGroupQuantity()+1);
					stopResolveRepository.save(psr);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 進香
	 * TODO
	 * @return
	 */
	@RequestMapping(value = "/getProPilgrimage", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProPilgrimage(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProPilgrimage> list = pilgrimageRepository.findByEndRegDate(this.getToday());
		for (ProPilgrimage pl : list) {
			List<ProPilgrimageType> ppt = pilgrimageTypeRepository.findByPilgrimageId(pl.getId());
			for (ProPilgrimageType type : ppt) {
				mm.put(pl.getSessions()+"-"+type.getCode(), type.getName());
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProPilgrimageyBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProPilgrimageyBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProPilgrimage pp = pilgrimageRepository.findBySessions(session);
		ProPilgrimageType type = pilgrimageTypeRepository.findByCode(pp.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("pilgr", pp);
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderPilgrimage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderPilgrimage(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.PILGRIMAGE + "-" + sessions + "-";
					
					String serialNo = orPilgrimageRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersPilgrimage op = new OrdersPilgrimage();
					op.setOrdersId(o.getId());
					op.setSerialNo(serialNo);
					op.setCustomersId(Integer.parseInt(os.getString("custId")));
					op.setAmount(Integer.parseInt(os.getString("amount")));
					op.setSolarBirth(os.getString("solarBirth").replace("/", "."));
					op.setAdNum(os.getString("adNum"));
					op.setVegeFood(os.getString("vegeFood"));
					op.setGodName(os.getString("godName"));
					orPilgrimageRepository.save(op);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 文昌法會
	 * @return
	 */
	@RequestMapping(value = "/getProWenchan", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProWenchan(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProWenchan> list = wenchanRepository.findByEndRegDate(this.getToday());
		for (ProWenchan pw : list) {
			List<ProWenchanType> pwt = wenchanTypeRepository.findByWenchanId(pw.getId());
			for (ProWenchanType type : pwt) {
				mm.put(pw.getSessions()+"-"+type.getCode(), pw.getSessions() + type.getName() + ",剩餘"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProWenchanBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProWenchanBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProWenchan pw = wenchanRepository.findBySessions(session);
		ProWenchanType type = wenchanTypeRepository.findByCode(pw.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderWenchan", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderWenchan(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.WENCHAN + "-" + sessions + "-";
					
					String serialNo = orWenchanRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersWenchan ow = new OrdersWenchan();
					ow.setOrdersId(o.getId());
					ow.setSerialNo(serialNo);
					ow.setCustomersId(Integer.parseInt(os.getString("custId")));
					ow.setAge(Integer.parseInt(os.getString("age")));
					ow.setAmount(Integer.parseInt(os.getString("amount")));
					orWenchanRepository.save(ow);
					
					//已出數量
					ProWenchan pw = wenchanRepository.findBySessions(sessions.split("-")[0]);
					ProWenchanType type = wenchanTypeRepository.findByCode(pw.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					wenchanTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 五年千歲禮斗
	 * @return
	 */
	@RequestMapping(value = "/getProPrinceStar", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProPrinceStar(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProPrinceStar> list = princeStarRepository.findByEndRegDate(this.getToday());
		for (ProPrinceStar pps : list) {
			List<ProPrinceStarType> ppst = princeStarTypeRepository.findByPrinceStarId(pps.getId());
			for (ProPrinceStarType type : ppst) {
				mm.put(pps.getSessions()+"-"+type.getCode(), pps.getSessions() + type.getName() + ",庫存"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getProPrinceStarBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProPrinceStarBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProPrinceStar pps = princeStarRepository.findBySessions(session);
		ProPrinceStarType type = princeStarTypeRepository.findByCode(pps.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderPrinceStar", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderPrinceStar(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.PRINCE_STAR + "-" + sessions + "-";
					
					String serialNo = orPrinceStarRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersPrinceStar ops = new OrdersPrinceStar();
					ops.setOrdersId(o.getId());
					ops.setSerialNo(serialNo);
					ops.setCustomersId(Integer.parseInt(os.getString("custId")));
					ops.setAge(Integer.parseInt(os.getString("age")));
					ops.setAmount(Integer.parseInt(os.getString("amount")));
					orPrinceStarRepository.save(ops);
					
					//已出數量
					ProPrinceStar pps = princeStarRepository.findBySessions(sessions.split("-")[0]);
					ProPrinceStarType type = princeStarTypeRepository.findByCode(pps.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					princeStarTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	

	/**
	 * 普渡
	 * @return
	 */
	@RequestMapping(value = "/getProPurdue", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProPurdue(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProPurdue> list = purdueRepository.findByEndRegDate(this.getToday());
		for (ProPurdue pp : list) {
			List<ProPurdueType> ppt = purdueTypeRepository.findByPurdueId(pp.getId());
			for (ProPurdueType type : ppt) {
				mm.put(pp.getSessions()+"-"+type.getCode(), pp.getSessions() + type.getName() + ",庫存"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getPurdueBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getPurdueBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProPurdue pp = purdueRepository.findBySessions(session);
		ProPurdueType type = purdueTypeRepository.findByCode(pp.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderPurdue", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderPurdue(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.PURDUE + "-" + sessions + "-";
					
					String serialNo = orPurdueRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersPurdue op = new OrdersPurdue();
					op.setOrdersId(o.getId());
					op.setSerialNo(serialNo);
					op.setCustomersId(Integer.parseInt(os.getString("custId")));
					op.setAge(Integer.parseInt(os.getString("age")));
					op.setAmount(Integer.parseInt(os.getString("amount")));
					orPurdueRepository.save(op);
					
					//已出數量
					ProPurdue pp = purdueRepository.findBySessions(sessions.split("-")[0]);
					ProPurdueType type = purdueTypeRepository.findByCode(pp.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					purdueTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 超渡
	 * TODO
	 * @return
	 */
	@RequestMapping(value = "/getProChaodue", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProChaodue(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProChaodue> list = chaodueRepository.findByEndRegDate(this.getToday());
		for (ProChaodue pc : list) {
			List<ProChaodueType> pct = chaodueTypeRepository.findByChaodueId(pc.getId());
			for (ProChaodueType type : pct) {
				mm.put(pc.getSessions()+"-"+type.getCode(), pc.getSessions() + type.getName() + ",庫存"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getChaodueBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getChaodueBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProChaodue pc = chaodueRepository.findBySessions(session);
		ProChaodueType type = chaodueTypeRepository.findByCode(pc.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderChaodue", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderChaodue(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.CHAODUE + "-" + sessions + "-";
					
					String serialNo = orChaodueRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersChaodue oc = new OrdersChaodue();
					oc.setOrdersId(o.getId());
					oc.setSerialNo(serialNo);
					oc.setCustomersId(Integer.parseInt(os.getString("custId")));
					oc.setAge(Integer.parseInt(os.getString("age")));
					oc.setAmount(Integer.parseInt(os.getString("amount")));
					oc.setSummary(os.getString("txt")+os.getString("summary"));
					orChaodueRepository.save(oc);
					
					//已出數量
					ProChaodue pc = chaodueRepository.findBySessions(sessions.split("-")[0]);
					ProChaodueType type = chaodueTypeRepository.findByCode(pc.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					chaodueTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 建廟慶典
	 * @return
	 */
	@RequestMapping(value = "/getProAnniversary", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProAnniversary(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProAnniversary> list = anniversaryRepository.findByEndRegDate(this.getToday());
		for (ProAnniversary pa : list) {
			List<ProAnniversaryType> pat = anniversaryTypeRepository.findByAnniversaryId(pa.getId());
			for (ProAnniversaryType type : pat) {
				mm.put(pa.getSessions()+"-"+type.getCode(), pa.getSessions() + type.getName() + ",剩餘"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getAnniversaryBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getAnniversaryBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProAnniversary pa = anniversaryRepository.findBySessions(session);
		ProAnniversaryType type = anniversaryTypeRepository.findByCode(pa.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderAnniversary", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderAnniversary(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.ANNIVERSARY + "-" + sessions + "-";
					
					String serialNo = orAnniversaryRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersAnniversary oa = new OrdersAnniversary();
					oa.setOrdersId(o.getId());
					oa.setSerialNo(serialNo);
					oa.setCustomersId(Integer.parseInt(os.getString("custId")));
					oa.setAge(Integer.parseInt(os.getString("age")));
					oa.setAmount(Integer.parseInt(os.getString("amount")));
					oa.setVegeFood(os.getString("vegeFood"));
					orAnniversaryRepository.save(oa);
					
					//已出數量
					ProAnniversary pa = anniversaryRepository.findBySessions(sessions.split("-")[0]);
					ProAnniversaryType type = anniversaryTypeRepository.findByCode(pa.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					anniversaryTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 梁皇寶懺
	 * @return
	 */
	@RequestMapping(value = "/getProLianghuang", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProLianghuang(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProLianghuang> list = lianghuangRepository.findByEndRegDate(this.getToday());
		for (ProLianghuang pl : list) {
			List<ProLianghuangType> plt = lianghuangTypeRepository.findByLianghuangId(pl.getId());
			for (ProLianghuangType type : plt) {
				mm.put(pl.getSessions()+"-"+type.getCode(), pl.getSessions() + type.getName() + ",剩餘"+(type.getInventory()-type.getQuantity())+"人");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getLianghuangBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getLianghuangBySession(@RequestParam("session")String session, @RequestParam("code")String code){
		ProLianghuang pl = lianghuangRepository.findBySessions(session);
		ProLianghuangType type = lianghuangTypeRepository.findByCode(pl.getId(), code);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderLianghuang", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderLianghuang(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String preNo = Params.LIANGHUANG + "-" + sessions + "-";
					
					String serialNo = orLianghuangRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersLianghuang ol = new OrdersLianghuang();
					ol.setOrdersId(o.getId());
					ol.setSerialNo(serialNo);
					ol.setCustomersId(Integer.parseInt(os.getString("custId")));
					ol.setAge(Integer.parseInt(os.getString("age")));
					ol.setAmount(Integer.parseInt(os.getString("amount")));
					orLianghuangRepository.save(ol);
					
					//已出數量
					ProLianghuang pl = lianghuangRepository.findBySessions(sessions.split("-")[0]);
					ProLianghuangType type = lianghuangTypeRepository.findByCode(pl.getId(), sessions.split("-")[1]);
					type.setQuantity(type.getQuantity()+1);
					lianghuangTypeRepository.save(type);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 解冤法會
	 * @return
	 */
	@RequestMapping(value = "/getProJieyuan", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProJieyuan(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProJieyuan> list = jieyuanRepository.findByEndRegDate(this.getToday());
		for (ProJieyuan pj : list) {
			mm.put(pj.getSessions(), pj.getSessions() + ",剩餘"+(pj.getInventory()-pj.getQuantity())+"人");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getJieyuanBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getJieyuanBySession(@RequestParam("session")String session, @RequestParam("lunarYear")String lunarYear){
		ProJieyuan pj = jieyuanRepository.findBySessions(session);
		ProJieyuanPrice type = jieyuanPriceRepository.findByName(pj.getId(), lunarYear);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderJieyuan", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderJieyuan(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String n[] = os.getString("sessions").split("\\.");
					String preNo = Params.JIEYUAN + "-" + n[0] + "-" + n[1]+n[2] + "-";
					
					String serialNo = orJieyuanRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersJieyuan oj = new OrdersJieyuan();
					oj.setOrdersId(o.getId());
					oj.setSerialNo(serialNo);
					oj.setCustomersId(Integer.parseInt(os.getString("custId")));
					oj.setAge(Integer.parseInt(os.getString("age")));
					oj.setAmount(Integer.parseInt(os.getString("amount")));
					orJieyuanRepository.save(oj);
					
					//已出數量
					ProJieyuan pj = jieyuanRepository.findBySessions(sessions);
					pj.setQuantity(pj.getQuantity()+1);
					jieyuanRepository.save(pj);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 補財庫
	 * @return
	 */
	@RequestMapping(value = "/getProBucaiku", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProBucaiku(){
		Map<String, String> mm = new LinkedHashMap<String, String>();
		List<ProBucaiku> list = bucaikuRepository.findByEndRegDate(this.getToday());
		for (ProBucaiku pb : list) {
			mm.put(pb.getSessions(), pb.getSessions() + ",剩餘"+(pb.getInventory()-pb.getQuantity())+"人");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("maps", mm);
	    return map;
	}
	
	@RequestMapping(value = "/getBucaikuBySession", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getBucaikuBySession(@RequestParam("session")String session, @RequestParam("lunarYear")String lunarYear){
		ProBucaiku pb = bucaikuRepository.findBySessions(session);
		ProBucaikuPrice type = bucaikuPriceRepository.findByName(pb.getId(), lunarYear);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("type", type);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderBucaiku", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderBucaiku(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				//明細
				JSONArray ary = obj.getJSONArray("list");
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					String sessions = os.getString("sessions");
					String n[] = os.getString("sessions").split("\\.");
					String preNo = Params.BUCAIKU + "-" + n[0] + "-" + n[1]+n[2] + "-";
					
					String serialNo = orBucaikuRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersBucaiku ob = new OrdersBucaiku();
					ob.setOrdersId(o.getId());
					ob.setSerialNo(serialNo);
					ob.setCustomersId(Integer.parseInt(os.getString("custId")));
					ob.setAge(Integer.parseInt(os.getString("age")));
					ob.setAmount(Integer.parseInt(os.getString("amount")));
					orBucaikuRepository.save(ob);
					
					//已出數量
					ProBucaiku pb = bucaikuRepository.findBySessions(sessions);
					pb.setQuantity(pb.getQuantity()+1);
					bucaikuRepository.save(pb);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 金牌捐獻
	 * @return
	 */
	@RequestMapping(value = "/editOrderDonateGold", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderDonateGold(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				JSONArray ary = obj.getJSONArray("list");
				String preNo = Params.DONATE_GOLD + "-" + this.getToday().substring(0,3) + "-00-";
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					
					String serialNo = orDonateGoldRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersDonateGold odg = new OrdersDonateGold();
					odg.setOrdersId(o.getId());
					odg.setSerialNo(serialNo);
					odg.setCustomersId(Integer.parseInt(os.getString("custId")));
					odg.setAge(Integer.parseInt(os.getString("age")));
					odg.setLiang(Integer.parseInt(os.getString("liang")));
					odg.setChien(Integer.parseInt(os.getString("chien")));
					odg.setFen(Integer.parseInt(os.getString("fen")));
					odg.setLi(Integer.parseInt(os.getString("li")));
					orDonateGoldRepository.save(odg);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	/**
	 * 物品捐獻
	 * @return
	 */
	@RequestMapping(value = "/getProDonateItems", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProDonateItems(){
		List<ProDonateItems> list = donateItemsRepository.findAll();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("list", list);
	    return map;
	}
	
	@RequestMapping(value = "/getProDonateItemsById", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getProDonateItemsById(@RequestParam("id")int id){
		ProDonateItems pd = donateItemsRepository.findOne(id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("items", pd);
	    return map;
	}
	
	@RequestMapping(value = "/editOrderDonateItems", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editOrderDonateItems(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			if(!StringUtils.isEmpty(jsonStr)){
				JSONObject obj = new JSONObject(jsonStr);
				Orders o = this.setOrders(obj, 11111);

				JSONArray ary = obj.getJSONArray("list");
				String preNo = Params.DONATE_ITEMS + "-" + this.getToday().substring(0,3) + "-00-";
				for(int i=0; i<ary.length(); i++){
					JSONObject os = ary.getJSONObject(i);
					
					String serialNo = orDonateItemsRepository.findByMaxNo(preNo);
					if(StringUtils.isEmpty(serialNo)){
						serialNo = preNo + "0000001";
					}else{
						int no = Integer.parseInt(serialNo.replace(preNo, ""))+1;
						serialNo = preNo + String.format("%07d", no);
					}
					
					OrdersDonateItems odi = new OrdersDonateItems();
					odi.setOrdersId(o.getId());
					odi.setSerialNo(serialNo);
					odi.setCustomersId(Integer.parseInt(os.getString("custId")));
					odi.setAge(Integer.parseInt(os.getString("age")));
					odi.setSummary(os.getString("summary"));
					orDonateItemsRepository.save(odi);
				}
				message = "設定成功";
			}
		} catch (JSONException e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	private Orders setOrders(JSONObject obj, int createUserId){
		Orders o = new Orders();
		o.setReceiptNo(this.getLastOrderNo());
		o.setCustomersId(Integer.parseInt(obj.getString("custId")));
		o.setGroupId(Integer.parseInt(obj.getString("groupId")));
		o.setTotalAmount(Integer.parseInt(obj.getString("total")));
		o.setPayType(obj.getString("pay"));
		o.setRemark(obj.getString("remark"));
		o.setIsActive("N");
		o.setCreateDate(new Date());
		o.setCreateUserId(createUserId);
		ordersRepository.save(o);
		return o;
	}
	
	
	/**
	 * 訂單主表 收據編號
	 * @return
	 */
	private String getLastOrderNo(){
		String date = this.getToday().substring(0,5);
		String receiptNo = ordersRepository.findByMaxNo(Params.ORDERS + date);
		if(StringUtils.isEmpty(receiptNo)){
			receiptNo = Params.ORDERS + date + "0000001";
		}else{
			int no = Integer.parseInt(receiptNo.replace(Params.ORDERS + date, ""))+1;
			receiptNo = Params.ORDERS + date + String.format("%07d", no);
		}
		return receiptNo;
	}
	
	
	/**
	 * 民國 年月日 - yyyMMdd
	 * @return
	 */
	private String getToday(){
		Calendar cal = Calendar.getInstance();
		String yy = (cal.get(Calendar.YEAR)-1911)+"";
		String mm = String.format("%02d", cal.get(Calendar.MONTH)+1);
		String dd = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
		return yy+mm+dd;
	}
}