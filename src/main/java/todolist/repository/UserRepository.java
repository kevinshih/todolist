package todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
		public List<User> findById(int userid);
}
