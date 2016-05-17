package Main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, String> {
	Issue findById(String id);
	List<Issue> findByProject_id(int id);
	List<Issue> findByUser_id(int id);
}
