package com.S2T.Share_2_Teach;

import com.S2T.Share_2_Teach.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long>{
    List<FileEntity> findByStatus(String status);
}
