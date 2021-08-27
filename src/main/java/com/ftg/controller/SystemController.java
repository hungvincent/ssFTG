package com.ftg.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
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

import com.ftg.dao.entity.Orders;
import com.ftg.dao.entity.SysParameter;
import com.ftg.dao.entity.Sysadmin;
import com.ftg.repository.OrdersRepository;
import com.ftg.repository.SysParameterRepository;
import com.ftg.repository.SysadminRepository;

@Controller
public class SystemController {

	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private SysadminRepository sysadminRepository;
	
	@Autowired
	private SysParameterRepository sysParameterRepository;
	
	
	
	
	@RequestMapping(value = "/sysParams", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String,Object> sysParams(){
		List<SysParameter> list = sysParameterRepository.findAll();
		Map<String, SysParameter> sm = new HashMap<String, SysParameter>();
		for (SysParameter sp : list) {
			sm.put(sp.getType(), sp);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "success");
		map.put("sysMap", sm);
	    return map;
	}
	
	
	@RequestMapping(value = "/editSysparams", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editSysparams(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) {
		String status = "success", message = "";
		try {
			List<SysParameter> list = sysParameterRepository.findAll();
			Map<String, SysParameter> sm = new HashMap<String, SysParameter>();
			for (SysParameter sp : list) {
				sm.put(sp.getType(), sp);
			}
			
			if(!StringUtils.isEmpty(jsonStr)){
				JSONArray ary = new JSONArray(jsonStr);
				for(int i=0; i<ary.length(); i++){
					JSONObject obj = ary.getJSONObject(i);
					String type = obj.getString("type");
					String value = obj.getString("value");
					
					SysParameter sp = null;
					if(sm.containsKey(type)){
						sp = sm.get(type);
					}else{
						sp = new SysParameter();
						sp.setType(type);
					}
					sp.setValue(value);
					sp.setUpdateDate(new Date());
					sysParameterRepository.save(sp);
				}
			}
			message = "設定成功";
		} catch (Exception e) {
			status = "error";
			message = e.getMessage();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	

	@RequestMapping(value = "/authority", method=RequestMethod.GET)
	public ModelAndView authority(){
		Sort sort = new Sort(Direction.ASC, "id");
		List<Sysadmin> list = sysadminRepository.findAll(sort);
		
		ModelAndView model = new ModelAndView();
		model.addObject("authList", list);
		model.setViewName("authority");
	    return model;
	}
	
	
	@RequestMapping(value = "/editSysadmin", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> editSysadmin(@RequestParam("jsonStr")String jsonStr, HttpServletRequest request) throws Exception {
		String status = "success", message = "";
		
		if(!StringUtils.isEmpty(jsonStr)){
			JSONObject obj = new JSONObject(jsonStr);
			
			Sysadmin sa = null;
			if(!StringUtils.isEmpty(obj.get("id"))){
				sa = sysadminRepository.findOne(Integer.parseInt(obj.get("id").toString()));
				sa.setUpdateDate(new Date());
			}else{
				Sysadmin admin = sysadminRepository.findByName(obj.get("name").toString());
				if(admin != null){
					status = "error";
					message = "姓名已存在";
				}else{
					sa = new Sysadmin();
					sa.setName(obj.get("name").toString());
					sa.setCreateDate(new Date());
				}
			}
			//沒有錯誤訊息才儲存
			if(StringUtils.isEmpty(message)){
				sa.setPassword(obj.get("password").toString());
				sa.setAuthCount(obj.get("authCount").toString());
				sa.setAuthOfferingBox(obj.get("authOfferingBox").toString());
				sa.setAuthParamSet(obj.get("authParamSet").toString());
				sa.setAuthReportPrint(obj.get("authReportPrint").toString());
				sa.setAuthQuery(obj.get("authQuery").toString());
				sa.setIsActive(obj.get("isActive").toString());
				sysadminRepository.save(sa);
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
	
	
	@RequestMapping(value = "/getSysadminById", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> getSysadminById(@RequestParam("id")int id) throws Exception {
		String status = "success";
		Sysadmin sa = sysadminRepository.findOne(id);		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("admin", sa);
	    return map;
	}
	
	
	@RequestMapping(value = "/deleteSysadmin", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> deleteSysadmin(@RequestParam("id")int id) throws Exception {
		String status = "success", message = "";
		List<Orders> os = ordersRepository.findByCreator(id);
		if(os != null && os.size() > 0){
			status = "error";
			message = "有交易記錄資料, 不可刪除";
		}else{
			sysadminRepository.delete(id);
		}
				
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
	    return map;
	}
}