package com.ftg.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import com.ftg.dao.Params;
import com.ftg.dao.entity.Address;
import com.ftg.dao.entity.Customers;
import com.ftg.dao.entity.Orders;
import com.ftg.dao.entity.SysParameter;
import com.ftg.repository.AddressRepository;
import com.ftg.repository.CustomersRepository;
import com.ftg.repository.OrdersRepository;
import com.ftg.repository.SysParameterRepository;

@Controller
public class CounterController {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private CustomersRepository customersRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private SysParameterRepository sysParameterRepository;
	
	
	

	@RequestMapping(value = "/counterwork", method=RequestMethod.GET)
	public ModelAndView counterwork(){
		List<String> years = new ArrayList<String>();
		Date date = new Date();
		List<SysParameter> lis = sysParameterRepository.findByType(Params.END_DATE);
		if(lis != null && lis.size() > 0){
			SysParameter s1 = lis.get(0);
			String s[] = s1.getValue().split("/");
			int y = Integer.parseInt(s[0]) + 1911;
			int m = Integer.parseInt(s[1]) - 1;
			int d = Integer.parseInt(s[2]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, y);
			cal.set(Calendar.MONTH, m);
			cal.set(Calendar.DATE, d);
			System.out.println(cal.getTime());
			if(date.compareTo(cal.getTime()) <= 0){
				years.add((Integer.parseInt(s[0])-1) + this.getLunarYear((Integer.parseInt(s[0])-1)+"") + "年");
			}
		}
		
		List<SysParameter> lis2 = sysParameterRepository.findByType(Params.START_DATE);
		if(lis2 != null && lis2.size() > 0){
			SysParameter s2 = lis2.get(0);
			String s[] = s2.getValue().split("/");
			int y = Integer.parseInt(s[0]) + 1911;
			int m = Integer.parseInt(s[1]) - 1;
			int d = Integer.parseInt(s[2]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, y);
			cal.set(Calendar.MONTH, m);
			cal.set(Calendar.DATE, d);
			System.out.println(cal.getTime());
			if(date.compareTo(cal.getTime()) > 0){
				years.add((Integer.parseInt(s[0])+1) + this.getLunarYear((Integer.parseInt(s[0])+1)+"") + "年");
			}
		}
		
		ModelAndView model = new ModelAndView();
		model.setViewName("counterwork");
		model.addObject("years", years);
	    return model;
	}
	
	
	@RequestMapping(value = "/saveCust", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> saveCust(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		int addrId = 0, custId = 0;
		String lunarNow = "";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lunarNow = obj.get("lunarNow").toString();
			String addr = obj.get("address").toString();
			
			Address a = addressRepository.findByAddress(addr);
			if (a == null) {
				if(StringUtils.isEmpty(obj.get("addrId"))){
					Address adr = new Address();
					adr.setAddress(addr);
					adr.setIsMail(obj.get("isMail").toString());
					adr.setCreateDate(new Date());
					addressRepository.save(adr);
					
					addrId = adr.getId();
				}else{
					addrId = Integer.parseInt(obj.get("addrId").toString());
				}
			}else{
				addrId = a.getId();
			}
			
			Customers c = customersRepository.findNameByGroupId(obj.get("name").toString(), addrId);
			if(c == null){
				Customers cust = new Customers();
				cust.setCustType(obj.get("custType").toString());
				cust.setName(obj.get("name").toString());
				cust.setAdNum(obj.get("adNum").toString());
				cust.setSex(obj.get("sex").toString());
				cust.setGroupId(addrId);
				cust.setPhone1(obj.get("phone1").toString());
				cust.setPhone2(obj.get("phone2").toString());
				cust.setGroupSort(1);
				cust.setLunarBirth(obj.get("lunarBirth").toString());
				cust.setSolarBirth(obj.get("solarBirth").toString());
				cust.setBirthHour(obj.get("birthHour").toString());
				if(!StringUtils.isEmpty(cust.getLunarBirth())){
					String ly = this.getLunarYear(cust.getLunarBirth());
					cust.setLunarYear(ly);
					cust.setZodiac(this.getZodiac(ly.substring(1,2)));
					cust.setSevenStar(this.getDdoufu(ly.substring(1,2)));
				}
				cust.setEmail(obj.get("email").toString());
				cust.setCardNum(obj.get("cardNum").toString());
				cust.setNotes(obj.get("notes").toString());
				cust.setIsActive(obj.get("isActive").toString());
				cust.setCreateDate(new Date());
				cust.setCreateUserId(111);
				cust.setAge(this.getAge(lunarNow, cust.getLunarBirth()));
				customersRepository.save(cust);
				message = "設定成功";
				
				custId = cust.getId();
			}else{
				status = "error";
				message = "會員姓名已存在此戶別";
			}
		}
		
		//新增後的客戶列表
		List<Customers> custs = customersRepository.findByGroupId(addrId);
		for (Customers c : custs) {
			if(!StringUtils.isEmpty(c.getLunarYear())){
				c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
				c.setZhiHua(this.getZhiHua(c.getAge()));
			}
		}
		//農曆生日排序後
		List<Customers> list = this.sortCustGroup(custs, "y");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custId", custId);
		map.put("addrId", addrId);
		map.put("custs", list);
	    return map;
	}
	
	
	@RequestMapping(value = "/editCust", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editCust(@RequestParam("jsonStr")String jsonStr) throws Exception {
		String status = "success", message = "";
		
		int addrId = 0, custId = 0;
		String lunarNow = "";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lunarNow = obj.get("lunarNow").toString();
			addrId = Integer.parseInt(obj.get("addrId").toString());
			custId = Integer.parseInt(obj.get("custId").toString());
			
			Customers cust = this.setCustomers(jsonStr);
			customersRepository.save(cust);
			message = "設定成功";
		}
		
		List<Customers> custs = customersRepository.findByGroupId(addrId);
		for (Customers c : custs) {
			if(!StringUtils.isEmpty(c.getLunarYear())){
				c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
				c.setZhiHua(this.getZhiHua(c.getAge()));
			}
		}
		//農曆生日排序後
		List<Customers> list = this.sortCustGroup(custs, "y");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custId", custId);
		map.put("addrId", addrId);
		map.put("custs", list);
	    return map;
	}
	
	@RequestMapping(value = "/editCustByAddr", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editCustByAddr(@RequestParam("jsonStr")String jsonStr) throws Exception {
		String status = "success", message = "";
		
		int addrId = 0, custId = 0;
		String lunarNow = "";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lunarNow = obj.get("lunarNow").toString();
			addrId = Integer.parseInt(obj.get("addrId").toString());
			custId = Integer.parseInt(obj.get("custId").toString());
			
			Customers cust = this.setCustomers(jsonStr);
			customersRepository.save(cust);
			
			Address addr = addressRepository.findOne(addrId);
			if(addr != null){
				addr.setAddress(obj.get("address").toString());
				addressRepository.save(addr);
			}
			message = "設定成功";
		}
		
		List<Customers> custs = customersRepository.findByGroupId(addrId);
		for (Customers c : custs) {
			if(!StringUtils.isEmpty(c.getLunarYear())){
				c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
				c.setZhiHua(this.getZhiHua(c.getAge()));
			}
		}
		//農曆生日排序後
		List<Customers> list = this.sortCustGroup(custs, "y");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custId", custId);
		map.put("addrId", addrId);
		map.put("custs", list);
	    return map;
	}
	
	@RequestMapping(value = "/editCustByPhone", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editCustByPhone(@RequestParam("jsonStr")String jsonStr) throws Exception {
		String status = "success", message = "";
		
		int addrId = 0, custId = 0;
		String lunarNow = "", phone1 = "";
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			lunarNow = obj.get("lunarNow").toString();
			addrId = Integer.parseInt(obj.get("addrId").toString());
			custId = Integer.parseInt(obj.get("custId").toString());
			phone1 = obj.get("phone1").toString();
			
			Customers cust = this.setCustomers(jsonStr);
			customersRepository.save(cust);
		}
		
		List<Customers> custs = customersRepository.findByGroupId(addrId);
		for (Customers c : custs) {
			if(!StringUtils.isEmpty(c.getLunarYear())){
				c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
				c.setZhiHua(this.getZhiHua(c.getAge()));
				//整批修改電話
				c.setPhone1(phone1);
				customersRepository.save(c);
			}
		}
		//農曆生日排序後
		List<Customers> list = this.sortCustGroup(custs, "y");
		message = "設定成功";
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custId", custId);
		map.put("addrId", addrId);
		map.put("custs", list);
	    return map;
	}
	
	
	@RequestMapping(value = "/deleteCust", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> deleteCust(@RequestParam("lunarNow")String lunarNow, @RequestParam("custId")int custId) throws Exception {
		String status = "success", message = "";
		List<Customers> list = null;
		
		List<Orders> os = ordersRepository.findByCust(custId);
		if(os != null && os.size() > 0){
			status = "error";
			message = "有交易記錄資料, 不可刪除";
		}else{
			Customers cust = customersRepository.findOne(custId);
			customersRepository.delete(cust);
			
			List<Customers> custs = customersRepository.findByGroupId(cust.getGroupId());
			for (Customers c : custs) {
				if(!StringUtils.isEmpty(c.getLunarYear())){
					c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
					c.setZhiHua(this.getZhiHua(c.getAge()));
				}
			}
			//農曆生日排序後
			list = this.sortCustGroup(custs, "y");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custs", list);
	    return map;
	}
	
	
	@RequestMapping(value = "/queryCust", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryCust(@RequestParam("name")String name, @RequestParam("phone")String phone, @RequestParam("address")String address) {
		String status = "success", message = "";
		Map<Integer, String> addrMap = new LinkedHashMap<Integer, String>();
		
		List<Customers> list = null;
		if(!StringUtils.isEmpty(name)){
			list = customersRepository.findByName(name);
			if(list != null && list.size() > 0){
				List<Integer> ids = new LinkedList<Integer>();
				for (Customers c : list) {
					ids.add(c.getGroupId());
				}
				
				List<Address> as = addressRepository.findIdIn(ids);
				for (Address a : as) {
					addrMap.put(a.getId(), a.getAddress());
				}
			}
		}else{
			if(!StringUtils.isEmpty(phone)){
				list = customersRepository.findByPhone1(phone);
				if(list != null && list.size() > 0){
					List<Integer> ids = new LinkedList<Integer>();
					for (Customers c : list) {
						ids.add(c.getGroupId());
					}
					
					List<Address> as = addressRepository.findIdIn(ids);
					for (Address a : as) {
						addrMap.put(a.getId(), a.getAddress());
					}
				}
			}else{
				if(!StringUtils.isEmpty(address)){
					Address a = addressRepository.findByAddress(address);
					if(a != null){
						addrMap.put(a.getId(), a.getAddress());
					}
				}
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("addrMap", addrMap);
	    return map;
	}
	
	@RequestMapping(value = "/queryCustById", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryCustById(@RequestParam("id")int id) {
		String status = "success";
		Customers cust = customersRepository.findOne(id);
		Address addr = addressRepository.findOne(cust.getGroupId());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("cust", cust);
		map.put("addr", addr);
	    return map;
	}
	
	@RequestMapping(value = "/queryCustByGroup", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> queryCustByGroup(@RequestParam("lunarNow")String lunarNow, @RequestParam("groupId")int groupId) {
		String status = "success", message = "";
		
		List<Customers> list = customersRepository.findByGroupId(groupId);
		for (Customers c : list) {
			if(!StringUtils.isEmpty(c.getLunarYear())){
				c.setAge(this.getAge(lunarNow, c.getLunarBirth()));
				c.setZhiHua(this.getZhiHua(c.getAge()));
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("custs", list);
	    return map;
	}
	
	
	
	/**
	 * 年齡（虛歲）
	 * 農曆民國年 – 出生農曆民國年 + 1
	 * 
	 *	#民國前1年 = 0年
	 * @return
	 */
	private int getAge(String now, String lunar){
		int a = 0;
		if(!StringUtils.isEmpty(lunar)){
			int y1 = Integer.parseInt(now.split("\\.")[0]);
			int y2 = Integer.parseInt(lunar.split("\\.")[0]);
			a = y1 -y2 + 1;
		}
		return a;
	}
	
	
	/**
	 * 換算公式
	 * 歲次：天干 + 地支
	 * 
	 * 天干
	 * （ 農曆民國年 – 2 ）/ 10 餘數0 = 癸、 1 = 甲、2 = 乙、、、。
	 * 民國前要變 -1
	 * 
  	 * 地支
  	 * （ 農曆民國年 ）/ 12 餘數0 = 亥、 1 = 子、2 = 丑、、、。
  	 * 民國前要變 + 1
	 */
	private String getLunarYear(String lunar){
		String tiangan[] = {"癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬"};
		String dizhi[] = {"亥", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌"};
		
		int year = Integer.parseInt(lunar.split("\\.")[0]);
		int idx = (year-2) % 10;
		int idx2 = year % 12;
		return tiangan[idx] + dizhi[idx2];
	}
	
	/**
	 * 生肖：出生年的地支
	 */
	private String getZodiac(String val){
		String dizhi[] = {"", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
		String zodiac[] = {"", "鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊", "猴", "雞", "狗", "豬"};

		int idx = Arrays.asList(dizhi).indexOf(val);
		return zodiac[idx];
	}
	
	/**
	 * 斗府：出生年的地支
	 */
	private String getDdoufu(String val){
		String dizhi[] = {"", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
		String doufu[] = {"", "天樞宮", "天璇宮", "天璣宮", "天權宮", "天衡宮", "闓天宮", "瑤光宮", "闓天宮", "天衡宮", "天權宮", "天璣宮", "天璇宮"};

		int idx = Arrays.asList(dizhi).indexOf(val);
		return doufu[idx];
	}
	
	/**
	 * 流年制化：年齡 / 12  餘數 1 = 安太歲、2 = 太陽拱照、、、
	 */
	private String getZhiHua(int age){
		String doufu[] = {"", "安太歲", "太陽拱照", "制喪門", "制桃花", "制五鬼", "制死符", "安太歲", "紫微星高照", "制白虎", "福德照臨", "制天狗", "制病符"};
		int idx = age % 12;
		return doufu[idx];
	}
	
	
	
	private Customers setCustomers(String jsonStr){
		JSONObject obj = new JSONObject(jsonStr);
		
		Customers cust = new Customers();
		cust.setId(Integer.parseInt(obj.get("custId").toString()));
		cust.setCustType(obj.get("custType").toString());
		cust.setName(obj.get("name").toString());
		cust.setAdNum(obj.get("adNum").toString());
		cust.setSex(obj.get("sex").toString());
		cust.setGroupId(Integer.parseInt(obj.get("addrId").toString()));
		cust.setPhone1(obj.get("phone1").toString());
		cust.setPhone2(obj.get("phone2").toString());
		cust.setGroupSort(Integer.parseInt(obj.get("groupSort").toString()));
		cust.setLunarBirth(obj.get("lunarBirth").toString());
		cust.setSolarBirth(obj.get("solarBirth").toString());
		cust.setBirthHour(obj.get("birthHour").toString());
		if(!StringUtils.isEmpty(cust.getLunarBirth())){
			String ly = this.getLunarYear(cust.getLunarBirth());
			cust.setLunarYear(ly);
			cust.setZodiac(this.getZodiac(ly.substring(1,2)));
			cust.setSevenStar(this.getDdoufu(ly.substring(1,2)));
		}
		cust.setEmail(obj.get("email").toString());
		cust.setCardNum(obj.get("cardNum").toString());
		cust.setNotes(obj.get("notes").toString());
		cust.setIsActive(obj.get("isActive").toString());
		cust.setCreateDate(new Date());
		cust.setCreateUserId(111);
		
		return cust;
	}
	
	
	/**
	 * group排序
	 * @param custs
	 * @param type y, m, d
	 */
	private List<Customers> sortCustGroup(List<Customers> custs, String type){
//		for (Customers c : custs) {
//			int date = 0;
//			if(!StringUtils.isEmpty(c.getLunarBirth())){
//				String birth[] = c.getLunarBirth().split(".");
//				if("y".equals(type)){
//					date = Integer.parseInt(birth[0]);
//				}
//				if("m".equals(type)){
//					//閏月
//					if(birth[1].indexOf("+") != -1){
//						date = Integer.parseInt(birth[0] + Integer.parseInt(birth[1].replace("+", ""))+1);
//					}else{
//						date = Integer.parseInt(birth[0] + Integer.parseInt(birth[1]));
//					}
//				}
//				if("d".equals(type)){
//					//閏月
//					if(birth[1].indexOf("+") != -1){
//						date = Integer.parseInt(birth[0] + Integer.parseInt(birth[1].replace("+", ""))+1);
//					}else{
//						date = Integer.parseInt(birth[0] + Integer.parseInt(birth[1]));
//					}
//				}
//			}
//		}
		
		Map<Integer, Customers> map = new TreeMap<Integer, Customers>();
		for (Customers c : custs) {
			int year = !StringUtils.isEmpty(c.getLunarBirth())?Integer.parseInt(c.getLunarBirth().replace(".", "")):0;
			map.put(year, c);
		}
		System.out.println(map.size());
		System.out.println(map.keySet());
		
		List<Customers> res = new LinkedList<Customers>();
		int no = 1;
		for (Map.Entry<Integer, Customers> m : map.entrySet()) {
			Customers cust = m.getValue();
			cust.setGroupSort(no);
			customersRepository.save(cust);
			
			res.add(cust);
			no++;
		}
		return res;
	}
}