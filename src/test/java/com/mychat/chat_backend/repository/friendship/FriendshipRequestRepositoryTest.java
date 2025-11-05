package com.mychat.chat_backend.repository.friendship;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.friendship.FriendshipRequest;
import com.mychat.chat_backend.repository.FriendshipRequestRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
/**
 * Test class for FriendshipRequestRepository
 */
class FriendshipRequestRepositoryTest {

        @Autowired
        private FriendshipRequestRepository friendshipRequestRepository;

        @Autowired
        private TestEntityManager entityManager;

        User requester;
        User requester2;
        User addressee;
        User addressee2;

        @BeforeEach
        void setUp() {
                requester = new User.Builder()
                                .username("requester")
                                .password("password")
                                .email("requester@example.com")
                                .build();

                addressee = new User.Builder()
                                .username("addressee")
                                .password("password")
                                .email("addressee@example.com")
                                .build();

                requester2 = new User.Builder()
                                .username("requester2")
                                .password("password")
                                .email("requester2@example.com")
                                .build();
                addressee2 = new User.Builder()
                                .username("addressee2")
                                .password("password")
                                .email("addressee2@example.com")
                                .build();

                requester = entityManager.persist(requester);
                addressee = entityManager.persist(addressee);
                requester2 = entityManager.persist(requester2);
                addressee2 = entityManager.persist(addressee2);

                FriendshipRequest friendshipRequest = FriendshipRequest.createFriendshipRequest(requester, addressee);
                FriendshipRequest friendshipRequest2 = FriendshipRequest.createFriendshipRequest(requester2, addressee);
                FriendshipRequest friendshipRequest3 = FriendshipRequest.createFriendshipRequest(requester, addressee2);
                entityManager.persist(friendshipRequest);
                entityManager.persist(friendshipRequest2);
                entityManager.persist(friendshipRequest3);
                entityManager.flush();
        }

        @Test
        void testFindByAddressee_ShouldReturnFriendshipRequestsWithGivenAddressee() {

                // When
                List<FriendshipRequest> results = friendshipRequestRepository.findAllByAddresseeId(addressee.getId());
                // Then
                Assertions.assertThat(results).hasSize(2).extracting(FriendshipRequest::getRequester)
                                .containsExactlyInAnyOrder(requester, requester2);

        }

        @Test
        void testFindByRequester_ShouldReturnFriendshipRequestsWithGivenRequester() {

                // When
                List<FriendshipRequest> results = friendshipRequestRepository.findAllByRequesterId(requester.getId());
                // Then
                Assertions.assertThat(results).hasSize(2).extracting(FriendshipRequest::getAddressee)
                                .containsExactlyInAnyOrder(addressee, addressee2);
        }

        @Test
        void testFindByUser_ShouldReturnFriendshipRequestsWithGivenUser() {
                // When
                List<FriendshipRequest> results = friendshipRequestRepository.findAllByUserId(requester.getId());
                // Then
                Assertions.assertThat(results).hasSize(2)
                                .extracting(fr -> fr.getRequester().equals(requester) ? fr.getAddressee()
                                                : fr.getRequester())
                                .containsExactlyInAnyOrder(addressee, addressee2);
        }

}
