package todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ListRepository extends JpaRepository<todolist.model.List, Integer> {
		public List<todolist.model.List> findByUseridOrderByDuedate(int userid);
		public List<todolist.model.List> findByUseridAndListid(int userid, int listid);
		public List<todolist.model.List> findByListid(int listid);
		
}
