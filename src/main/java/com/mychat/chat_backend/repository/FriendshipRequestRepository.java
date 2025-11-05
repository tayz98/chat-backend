package com.mychat.chat_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mychat.chat_backend.model.friendship.FriendshipRequest;

/**
 * Repository interface for managing FriendshipRequest entities.
 */
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    /**
     * Finds all friendship requests where the specified user is the addressee.
     *
     * @param addressee the addressee user
     * @return a list of FriendshipRequest entities
     */
    List<FriendshipRequest> findAllByAddresseeId(Long addresseeId);

    /**
     * Finds all friendship requests where the specified user is the requester.
     *
     * @param requester the requester user
     * @return a list of FriendshipRequest entities
     */
    List<FriendshipRequest> findAllByRequesterId(Long requesterId);

    /**
     * Finds all friendship requests involving the specified user, either as
     * requester or addressee.
     *
     * @param userId the user ID
     * @return a list of FriendshipRequest entities
     */
    @Query("SELECT fr FROM FriendshipRequest fr WHERE fr.requester.id = :userId OR fr.addressee.id = :userId")
    List<FriendshipRequest> findAllByUserId(@Param("userId") Long userId);

}
