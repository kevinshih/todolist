package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import todolist.service.ListService;


@RestController
public class ListController extends BaseController {
	
	@Autowired
	private ListService listService;
	
	// TODO
	@GetMapping("/getListByUser")
	public String getListByUser(int userId) {
		JsonObject jsonObject = listService.getListByUser(userId);
		return jsonObject.toString();
	}
}