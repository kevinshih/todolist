package todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<todolist.model.Item, Integer> {
	public List<todolist.model.Item> findByListidOrderBySortid(int listid);
	
}