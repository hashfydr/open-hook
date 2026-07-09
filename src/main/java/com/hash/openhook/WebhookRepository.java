package com.hash.openhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WebhookRepository extends JpaRepository<WebhookRequest, Long> {
    List<WebhookRequest> findByUrlIdOrderByTimestampDesc(String urlId);
}
