package todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.service.ItemService;


@RestController
public class ItemController extends BaseController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/getItemList")
	public List<todolist.model.Item> getItemList(int listid) {
		List<todolist.model.Item> itemList = null;
		StringBuilder sb = new StringBuilder("");
		try {
			itemList = itemService.getItemListByListid(listid);
			for (todolist.model.Item item : itemList) {
				logger.info(item.getName()+","+item.getDescription());
				sb.append("Name:"+item.getName()+",Description:"+item.getDescription()+"\n");
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return itemList;
	}

	
}