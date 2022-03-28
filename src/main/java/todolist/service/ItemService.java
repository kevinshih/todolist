package todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todolist.repository.ItemRepository;
import todolist.repository.ListRepository;


@Service
public class ItemService extends BaseService {

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<todolist.model.Item> getItemListByListid(int listid) {
		List<todolist.model.Item> itemList = itemRepository.findByListidOrderBySortid(listid);
		return itemList;
	}

}