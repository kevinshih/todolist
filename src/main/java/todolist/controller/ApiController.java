package todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import todolist.model.PrintListAndItem;
import todolist.service.ApiService;

@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

	@Autowired
	private ApiService apiService;
	
	/**
	 * **List** contains muliple **Item**. Item can only belong to one List.
	 * complexity O(N^2) 
	 * @param userid
	 * @return
	 */
	@GetMapping("/getMyAllListAndItemByUserid")
	public List<PrintListAndItem>  getMyAllListAndItemByUserid(int userid){
		return apiService.getMyAllListAndItemByUserid(userid);
	}
	
	/**
	 * **List** belongs to one **User** but can be shared with other **User**
	 * complexity O(1) without any loop
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/shareListToOtherUsers", consumes = "application/json", produces = "application/json")
	public String shareListToOtherUsers(@RequestBody todolist.model.List list) {
		JsonObject jsonObject = apiService.shareListToOtherUsers(list);
		return jsonObject.toString();
	}
	
	/**
	 * **List** should have a due date that can be changed
	 * complexity O(N)
	 * @param list
	 * @return
	 */
	@PostMapping(value = "/updateListDuedate", consumes = "application/json", produces = "application/json")
	public String updateListDuedate(@RequestBody todolist.model.List list) {
		JsonObject jsonObject = apiService.updateListDuedate(list);
		return jsonObject.toString();
	}
	
	/**
	 * **Item** can be moved between different or within the same **List** (ordering matters)
	 * Time complexity : O(N)
	 * @param oriListid
	 * @param oriSortid
	 * @param targetListid
	 * @param targetSortid
	 * @param direction
	 * @return
	 */
	@GetMapping("/moveItem")
	public String moveItem(int oriListid, int oriSortid, int targetListid, int targetSortid, String direction) {
		logger.info("oriListid:{}, oriSortid:{}, targetListid:{}, targetSortid:{}, direction:{}",oriListid, oriSortid, targetListid, targetSortid, direction);
		JsonObject jsonObject = apiService.moveItem(oriListid, oriSortid, targetListid, targetSortid, direction);
		return jsonObject.toString();
	}
}
