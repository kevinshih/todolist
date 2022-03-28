package todolist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

import todolist.model.PrintListAndItem;
import todolist.repository.ItemRepository;
import todolist.repository.ListRepository;
import todolist.repository.UserRepository;

@Service
public class ApiService extends BaseService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * **List** contains muliple **Item**. Item can only belong to one List.
	 * complexity O(N^2) 
	 * @param userid
	 * @apiurl http://localhost:9003/todolist/api/getMyAllListAndItemByUserid/?userid=1
	 * @return
	 */
	public List<PrintListAndItem>  getMyAllListAndItemByUserid(int userid){
		logger.info("getMyAllListAndItemByUserid userid: "+userid);
		List<PrintListAndItem> pliList = new ArrayList<>();
		//First, get all list id which belong to the user.
		List<todolist.model.List> getList =  listRepository.findByUseridOrderByDuedate(userid);
		for(todolist.model.List l:getList) {
			//According each list id to get its item.
			List<todolist.model.Item> getItems =  itemRepository.findByListidOrderBySortid(l.getListid());
			for(todolist.model.Item i:getItems) {
				try {
					PrintListAndItem p = new PrintListAndItem(l.getName(), l.getDuedate(), i.getName(), i.getDescription(), i.getDeadline(), i.getSortid());
					pliList.add(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return pliList;
	}
	
	/**
	 * **List** belongs to one **User** but can be shared with other **User**
	 * complexity O(1) without any loop
	 * @param list
	 * @return
	 */
	public JsonObject shareListToOtherUsers(todolist.model.List list) {
		Date nowDateTime = new Date();
		list.setCreatedate(nowDateTime);
		list.setModifieddate(nowDateTime);
		JsonObject jsonObject = new JsonObject();
		try {
			List<todolist.model.List> getList =  listRepository.findByUseridAndListid(list.getUserid(), list.getListid());
			if( getList.size()<1) {//had not been shared
				listRepository.save(list);
				jsonObject.addProperty("success", SUCCESS);
			}else {
				String name = userRepository.findById(list.getUserid()).get(0).getUsername();
				jsonObject.addProperty("fail", "This list is already shared to "+name);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			jsonObject.addProperty("fail", e.getMessage());
            return jsonObject;
		}
		
        
        return jsonObject;
	}
	
	/**
	 * **List** should have a due date that can be changed
	 * complexity O(N)
	 * @param list
	 * @return
	 */
	public JsonObject updateListDuedate(todolist.model.List list) {
		Date nowDateTime = new Date();
		JsonObject jsonObject = new JsonObject();
		try {
			List<todolist.model.List> getList =  listRepository.findByListid(list.getListid());
			for(todolist.model.List oneList: getList) {
				oneList.setDuedate(list.getDuedate());
				oneList.setModifieddate(nowDateTime);
			}
			listRepository.saveAll(getList);// update the list duedate.
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			jsonObject.addProperty("fail", e.getMessage());
            return jsonObject;
		}
		
		jsonObject.addProperty("success", SUCCESS);
        return jsonObject;
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
	public JsonObject moveItem(int oriListid, int oriSortid, int targetListid, int targetSortid, String direction) {
		JsonObject jsonObject = new JsonObject();
		if(!StringUtils.equals(direction, "up") && !StringUtils.equals(direction, "down")) {
			jsonObject.addProperty("fail", "Direction wrong");
			return jsonObject;
		}
		/**
		 * if oriListid and targetListid are the same, oriSortid and targetSortid are the same, means no move.
		 * if oriListid==targetListid && oriSortid==targetSortid++ && direction.equals("down"), means no move.
		 * if oriListid==targetListid && oriSortid==targetSortid-- && direction.equals("up"), means no move.
		 */
		if((oriListid==targetListid && oriSortid==targetSortid) || (oriListid==targetListid && oriSortid+1==targetSortid && direction.equals("up")) || (oriListid==targetListid && oriSortid-1==targetSortid && direction.equals("down"))) {
			logger.info("without changed");
			jsonObject.addProperty("keep", "without changed");
	        return jsonObject;
		}
		
		try {
			if(oriListid==targetListid) moveItemInSameList(oriListid,oriSortid,targetSortid,direction);
			else moveItemToDifferentList(oriListid,oriSortid,targetListid,targetSortid,direction);

		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			jsonObject.addProperty("fail", e.getMessage());
            return jsonObject;
		}
		logger.info("SUCCESS");
		jsonObject.addProperty("success", SUCCESS);
        return jsonObject;
		
	}
	
	private void moveItemInSameList(int oriListid, int oriSortid, int targetSortid, String direction) {
		logger.info("moveItemInSameList");
		Date nowDateTime = new Date();
		List<todolist.model.Item> getItems =  itemRepository.findByListidOrderBySortid(oriListid);
		int getItemsSize = getItems.size();
		if(targetSortid==0 && direction.equals("up")) {//1.
			logger.info("targetSortid==0 && direction.equals(\"up\")");
			int count = 1;
			for(todolist.model.Item i:getItems) {
					if(i.getSortid()<oriSortid) i.setSortid(count++);
					else if(i.getSortid()==oriSortid) i.setSortid(0);
					else continue;
					i.setModifieddate(nowDateTime);
			}
		}else if(direction.equals("up") && oriSortid<targetSortid) {//2.
			logger.info("direction.equals(\"up\") && oriListid<targetSortid");
			int count = oriSortid;
			for(todolist.model.Item i:getItems) {
				if(i.getSortid()<oriSortid) continue;
				else if(i.getSortid()==oriSortid) i.setSortid(targetSortid-1);
				else if(i.getSortid()>oriSortid && i.getSortid()<targetSortid)i.setSortid(count++);
				else continue;
				i.setModifieddate(nowDateTime);
			}
		}else if(direction.equals("up") && oriSortid>targetSortid) {//3.
			logger.info("direction.equals(\"up\") && oriListid>targetSortid");
			int count = targetSortid+1;
			for(todolist.model.Item i:getItems) {
				if(i.getSortid()<targetSortid) continue;
				else if(i.getSortid()==oriSortid) i.setSortid(targetSortid);
				else if(i.getSortid()>=targetSortid) i.setSortid(count++);
				else continue;
				i.setModifieddate(nowDateTime);
			}
		}else if( (getItemsSize-1==targetSortid) && direction.equals("down")) {//4.
			logger.info("(getItemsSize-1==targetSortid) && direction.equals(\"down\")");
			int count = oriSortid;
			for(todolist.model.Item i:getItems) {
				if(i.getSortid()>oriSortid) i.setSortid(count++);
				else if(i.getSortid()<oriSortid) continue;
				else if(i.getSortid()==oriSortid) i.setSortid(targetSortid);
				i.setModifieddate(nowDateTime);
			}
		}else if(direction.equals("down") && oriSortid<targetSortid) {//5.
			logger.info("direction.equals(\"down\") && oriListid<targetSortid");
			int count = oriSortid;
			for(todolist.model.Item i:getItems) {
				if(i.getSortid()<oriSortid) continue;
				else if(i.getSortid()==oriSortid) i.setSortid(targetSortid);
				else if(i.getSortid()>oriSortid && i.getSortid()<=targetSortid) i.setSortid(count++);
				i.setModifieddate(nowDateTime);
			}
		}else if(direction.equals("down") && oriSortid>targetSortid) {//6.
			logger.info("direction.equals(\"down\") && oriListid>targetSortid");
			int count = targetSortid+2;
			for(todolist.model.Item i:getItems) {
				if(i.getSortid()<=targetSortid) continue;
				else if(i.getSortid()>targetSortid && i.getSortid()<oriSortid ) i.setSortid(count++);
				else if(i.getSortid()==oriSortid) i.setSortid(targetSortid+1);
				else continue;
				i.setModifieddate(nowDateTime);
			}
		}
		itemRepository.saveAll(getItems);
	}
	
	private void moveItemToDifferentList(int oriListid, int oriSortid, int targetListid, int targetSortid, String direction) {
		logger.info("moveItemToDifferentList");
		Date nowDateTime = new Date();
		List<todolist.model.Item> oriItems =  itemRepository.findByListidOrderBySortid(oriListid);
		List<todolist.model.Item> targetItems =  itemRepository.findByListidOrderBySortid(targetListid);
		int targetItemsSize = targetItems.size();
		int count = oriSortid;
		//1.handle oriItems
		for(todolist.model.Item i: oriItems) {
			if(i.getSortid()<oriSortid) continue;
			else if(i.getSortid()==oriSortid) {
				//2.handle targetItems
				int exactlySortid = moveItemToDifferentListHandleTargetItems(targetItems,targetItemsSize,targetSortid,direction,nowDateTime);
				i.setListid(targetListid);
				i.setSortid(exactlySortid);
			}
			else if(i.getSortid()>oriSortid) i.setSortid(count++);
			i.setModifieddate(nowDateTime);
		}
		itemRepository.saveAll(oriItems);
		
		
	}
	
	private int moveItemToDifferentListHandleTargetItems(List<todolist.model.Item> targetItems, int targetItemsSize, int targetSortid, String direction, Date nowDateTime) {
		logger.info("moveItemToDifferentListHandleTargetItems");
		if(targetSortid==0 && direction.equals("up")) {//7.
			logger.info("targetSortid==0 && direction.equals(\"up\")");
			int count = 1;
			for(todolist.model.Item i:targetItems) {
					i.setSortid(count++);
					i.setModifieddate(nowDateTime);
			}
			itemRepository.saveAll(targetItems);
			return 0;
		}else if( (targetItemsSize-1==targetSortid) && direction.equals("down")) {//8.
			logger.info("(targetItemsSize-1==targetSortid) && direction.equals(\"down\")");
			return targetItemsSize;
		}else if(direction.equals("up")) {//9.
			logger.info("direction.equals(\"up\")");
			int count = targetSortid+1;
			for(todolist.model.Item i:targetItems) {
				if(i.getSortid()<targetSortid) continue;
				else i.setSortid(count++);
				i.setModifieddate(nowDateTime);
			}
			itemRepository.saveAll(targetItems);
			return targetSortid;
		}else if(direction.equals("down")) {//10.
			logger.info("direction.equals(\"down\")");
			int count = targetSortid+2;
			for(todolist.model.Item i:targetItems) {
				if(i.getSortid()<=targetSortid) continue;
				else i.setSortid(count++);
				i.setModifieddate(nowDateTime);
			}
			itemRepository.saveAll(targetItems);
			return targetSortid+1;
		}else {
			logger.info("NoSuchElementException");
			throw new NoSuchElementException();
		}
			
	}
}
