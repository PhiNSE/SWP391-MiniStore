package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    public List<UserNotification> findByUserId(Long id);
}
