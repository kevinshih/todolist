package todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import todolist.repository.ListRepository;


@Service
public class ListService extends BaseService {

	@Autowired
	private ListRepository listRepository;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public JsonObject getListByUser(int userId) {
		List<todolist.model.List> listList = listRepository.findByUseridOrderByDuedate(userId);
		JsonObject jsonObject = new JsonObject();
		for (todolist.model.List list : listList) {
			jsonObject.addProperty(Integer.toString(list.getId()), list.getName());
		}
		return jsonObject;

	}
	

}