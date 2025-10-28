package com.mychat.chat_backend.repository.friendship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mychat.chat_backend.model.User;
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
    List<FriendshipRequest> findAllByAddressee(User addressee);

    /**
     * Finds all friendship requests where the specified user is the requester.
     *
     * @param requester the requester user
     * @return a list of FriendshipRequest entities
     */
    List<FriendshipRequest> findAllByRequester(User requester);

    @Query("SELECT fr FROM FriendshipRequest fr WHERE fr.requester = :user OR fr.addressee = :user")
    /**
     * Finds all friendship requests involving the specified user, either as
     * requester or addressee.
     *
     * @param user the user
     * @return a list of FriendshipRequest entities
     */
    List<FriendshipRequest> findAllByUser(User user);

}
