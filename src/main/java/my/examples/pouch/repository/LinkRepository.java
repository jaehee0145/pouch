package my.examples.pouch.repository;

import my.examples.pouch.domain.Link;
import my.examples.pouch.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT B FROM Link B WHERE B.email=:email AND B.repository=0")
    List<Link> boardByEmail(@Param("email") String email);

    @Query("SELECT B FROM Link B WHERE B.email=:email AND B.repository=1")
    List<Link> repositoryByEmail(@Param("email") String email);

    @Query("SELECT B.tags FROM Link B where B.id=:id")
    List<Tag> getTagByBoardId(@Param("id") Long id);

    @Query("SELECT L FROM Link L inner join fetch L.category" +
           " WHERE L.category.id=:categoryId and L.email=:email")
    List<Link> getMyPouchByCategory(@Param("categoryId") Long categoryId,@Param("email") String email);
}