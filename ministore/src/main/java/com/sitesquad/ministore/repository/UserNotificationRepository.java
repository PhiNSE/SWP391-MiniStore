package com.sitesquad.ministore.repository;

import com.sitesquad.ministore.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
     UserNotification findOneByUserNotificationId(Long id);
     List<UserNotification> findByUser_EmailIgnoreCaseOrderByDate(String email);
     List<UserNotification> findByUserId(Long id);
}
