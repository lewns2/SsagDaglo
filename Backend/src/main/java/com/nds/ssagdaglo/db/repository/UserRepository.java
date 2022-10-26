package com.nds.ssagdaglo.db.repository;

import com.nds.ssagdaglo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
// extends 하고 있는 JpaRepository 들어갔을 때 이미 구현된 기능이라면 그냥 사용 / 별도로 구현하고 싶은게 있다면 아래에 작성하여 사용.
    public List<User> findPasswordByUserEmail(String userEmail);

    public Optional<User> findByUserNickName(String userNickName);

    public List<User> findNicknameByUserEmail(String userEmail);

}
