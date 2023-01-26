package springboot.study.springbootblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import springboot.study.springbootblog.dto.ReplySaveRequestDto;
import springboot.study.springbootblog.model.Reply;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // 네이티브 쿼리로 영속화 (BoardService - writerReply 메서드에서 사용)
    @Modifying
    @Query(value = "INSET INTO reply(userId, boardId, content, createDate VALUES(?1,?2,?3,now()",nativeQuery = true)
    // 업데이트된 행의 개수를 반환
    int mSave(int userId, int boardId, String content);
}
