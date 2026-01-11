package com.mychat.chat_backend.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mychat.chat_backend.dto.friendship.FriendshipDto;
import com.mychat.chat_backend.dto.friendship.FriendshipRequestDto;
import com.mychat.chat_backend.mapper.FriendshipMapper;
import com.mychat.chat_backend.model.User;
import com.mychat.chat_backend.model.enums.FriendshipStatus;
import com.mychat.chat_backend.model.friendship.Friendship;
import com.mychat.chat_backend.model.friendship.FriendshipRequest;
import com.mychat.chat_backend.repository.FriendshipRepository;
import com.mychat.chat_backend.repository.FriendshipRequestRepository;
import com.mychat.chat_backend.repository.UserRepository;
import com.mychat.chat_backend.service.impl.FriendshipServiceImpl;
import com.mychat.chat_backend.dto.user.UserDto;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {
        @Mock
        private FriendshipRepository friendshipRepository;

        @Mock
        private FriendshipRequestRepository friendshipRequestRepository;

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private FriendshipServiceImpl friendshipService;

        private Friendship testFriendship;
        private FriendshipRequest testFriendshipRequest;
        private FriendshipDto testFriendshipDto;
        private User testUser1;
        private User friendOfUser1;
        private User requestedFriendOfUser1;
        Field idUserField;
        Field idFieldFriendship;

        @BeforeEach
        void setUp() throws Exception {
                testUser1 = new User.Builder()
                                .username("Testuser")
                                .password("1234")
                                .email("test@example.com")
                                .isAdmin(false)
                                .build();
                idUserField = User.class.getDeclaredField("id");
                idUserField.setAccessible(true);
                idUserField.set(testUser1, 1L);
                friendOfUser1 = new User.Builder()
                                .username("Testuser2")
                                .password("1234")
                                .email("test@example2.com")
                                .isAdmin(false)
                                .build();
                idUserField.set(friendOfUser1, 2L);
                requestedFriendOfUser1 = new User.Builder()
                                .username("Testuser3")
                                .password("1234")
                                .email("test@example3.com")
                                .isAdmin(false)
                                .build();
                idUserField.set(requestedFriendOfUser1, 3L);
                testFriendship = Friendship.createFriendship(testUser1, friendOfUser1);
                idFieldFriendship = Friendship.class.getDeclaredField("id");
                idFieldFriendship.setAccessible(true);
                idFieldFriendship.set(testFriendship, 1L);
                testFriendshipDto = FriendshipMapper.toFriendshipDto(testFriendship);
                testFriendshipRequest = FriendshipRequest.createFriendshipRequest(testUser1,
                                requestedFriendOfUser1);
                Field idFieldRequest = FriendshipRequest.class.getDeclaredField("id");
                idFieldRequest.setAccessible(true);
                idFieldRequest.set(testFriendshipRequest, 1L);
        }

        @Test
        void getFriendshipById_WhenFriendshipExists_ReturnsFriendshipDto() {
                when(friendshipRepository.findById(testFriendship.getId()))
                                .thenReturn(Optional.ofNullable(testFriendship));
                FriendshipDto result = friendshipService.getFriendshipById(testFriendship.getId());
                Assertions.assertEquals(testFriendshipDto, result);
                verify(friendshipRepository, times(1)).findById(testFriendship.getId());
        }

        @Test
        void getFriends_WhenFriendsExist_ReturnsListOfFriends() throws Exception {
                User anotherFriendOfUser1 = new User.Builder()
                                .username("Testuser4")
                                .password("1234")
                                .email("kdsalfjl@example.com")
                                .isAdmin(false)
                                .build();
                idUserField.set(anotherFriendOfUser1, 4L);
                when(friendshipRepository.findAllFriendIdsByUser(testUser1.getId()))
                                .thenReturn(List.of(friendOfUser1.getId(), anotherFriendOfUser1.getId()));
                when(userRepository.findById(friendOfUser1.getId()))
                                .thenReturn(Optional.ofNullable(friendOfUser1));
                when(userRepository.findById(anotherFriendOfUser1.getId()))
                                .thenReturn(Optional.ofNullable(anotherFriendOfUser1));
                List<UserDto> result = friendshipService.getFriends(testUser1.getId());
                Assertions.assertEquals(2, result.size());
                verify(friendshipRepository, times(1)).findAllFriendIdsByUser(testUser1.getId());
                verify(userRepository, times(1)).findById(friendOfUser1.getId());
                verify(userRepository, times(1)).findById(anotherFriendOfUser1.getId());
        }

        @Test
        void getAllFriendships_WhenFriendshipsExist_ReturnsListOfFriendshipDtos() {
                when(friendshipRepository.findAll())
                                .thenReturn(List.of(testFriendship));
                List<FriendshipDto> result = friendshipService.getAllFriendships();
                Assertions.assertEquals(1, result.size());
                Assertions.assertEquals(testFriendshipDto, result.get(0));
                verify(friendshipRepository, times(1)).findAll();
        }

        @Test
        void getAllFriendshipsByUserId_WhenFriendshipsExist_ReturnsListOfFriendshipDtos() {
                when(friendshipRepository.findAllByUserId(testUser1.getId()))
                                .thenReturn(List.of(testFriendship));
                List<FriendshipDto> result = friendshipService.getAllFriendshipsByUserId(testUser1.getId());
                Assertions.assertEquals(1, result.size());
                Assertions.assertEquals(testFriendshipDto, result.get(0));
                verify(friendshipRepository, times(1)).findAllByUserId(testUser1.getId());
        }

        @Test
        void createFriendship_WhenUsersExist_ReturnsCreatedFriendshipDto() throws Exception {
                User newFriendOfUser1 = new User.Builder()
                                .username("Testuser5")
                                .password("1234")
                                .email("kldsfjd@example.com")
                                .isAdmin(false)
                                .build();
                idUserField.set(newFriendOfUser1, 5L);
                when(userRepository.findById(testUser1.getId()))
                                .thenReturn(Optional.ofNullable(testUser1));
                when(userRepository.findById(newFriendOfUser1.getId()))
                                .thenReturn(Optional.ofNullable(newFriendOfUser1));
                when(friendshipRepository.save(ArgumentMatchers.any(Friendship.class)))
                                .thenReturn(testFriendship);
                FriendshipDto result = friendshipService.createFriendship(testUser1.getId(), newFriendOfUser1.getId());
                Assertions.assertNotEquals(testFriendshipDto, result);
                Assertions.assertEquals(testUser1.getId(), result.getUserId());
                Assertions.assertEquals(newFriendOfUser1.getId(), result.getFriendId());
                verify(userRepository, times(1)).findById(testUser1.getId());
                verify(userRepository, times(1)).findById(newFriendOfUser1.getId());
                verify(friendshipRepository, times(1)).save(ArgumentMatchers.any(Friendship.class));
        }

        @Test
        void removeFriendship_WhenFriendshipExists_DeletesFriendship() {
                when(friendshipRepository.findById(testFriendship.getId()))
                                .thenReturn(Optional.ofNullable(testFriendship));
                friendshipService.removeFriendship(testFriendship.getId());
                verify(friendshipRepository, times(1)).findById(testFriendship.getId());
                verify(friendshipRepository, times(1)).delete(testFriendship);
        }

        @Test
        void sendFriendshipRequest_WhenUsersExistAndNotFriends_ReturnsFriendshipRequestDto() {
                when(userRepository.findById(testUser1.getId()))
                                .thenReturn(Optional.ofNullable(testUser1));
                when(userRepository.findById(requestedFriendOfUser1.getId()))
                                .thenReturn(Optional.ofNullable(requestedFriendOfUser1));
                when(friendshipRepository.areFriends(testUser1.getId(), requestedFriendOfUser1.getId()))
                                .thenReturn(false);
                when(friendshipRequestRepository.save(ArgumentMatchers.any(FriendshipRequest.class)))
                                .thenReturn(testFriendshipRequest);
                FriendshipRequestDto result = friendshipService.sendFriendshipRequest(testUser1.getId(),
                                requestedFriendOfUser1.getId());
                Assertions.assertEquals(testFriendshipRequest.getRequester().getId(), result.getRequesterId());
                Assertions.assertEquals(testFriendshipRequest.getAddressee().getId(), result.getAddresseeId());
                verify(friendshipRepository, times(1)).areFriends(testUser1.getId(), requestedFriendOfUser1.getId());
                verify(friendshipRequestRepository, times(1)).save(ArgumentMatchers.any(FriendshipRequest.class));
        }

        @Test
        void getPendingRequestsForUser_WhenRequestsExist_ReturnsListOfFriendshipRequestDtos() {
                when(userRepository.existsById(testUser1.getId()))
                                .thenReturn(true);
                when(friendshipRequestRepository.findAllByUserId(testUser1.getId()))
                                .thenReturn(List.of(testFriendshipRequest));
                List<FriendshipRequestDto> result = friendshipService.getPendingRequestsForUser(testUser1.getId());
                Assertions.assertEquals(1, result.size());
                Assertions.assertEquals(testFriendshipRequest.getId(), result.get(0).getId());
                verify(friendshipRequestRepository, times(1)).findAllByUserId(testUser1.getId());

        }

        @Test
        void respondToFriendshipRequest_WhenAcceptingRequest_CreatesFriendship() {
                final User newFriendOfUser1 = new User.Builder()
                                .username("Testuser6")
                                .password("1234")
                                .email("aklsdfj@gmail.com")
                                .isAdmin(false)
                                .build();
                try {
                        idUserField.set(newFriendOfUser1, 6L);
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                }
                FriendshipRequest friendshipRequest = FriendshipRequest.createFriendshipRequest(testUser1,
                                newFriendOfUser1);
                try {
                        Field idFieldRequest = FriendshipRequest.class.getDeclaredField("id");
                        idFieldRequest.setAccessible(true);
                        idFieldRequest.set(friendshipRequest, 2L);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                }
                when(friendshipRequestRepository.findById(friendshipRequest.getId()))
                                .thenReturn(Optional.ofNullable(friendshipRequest));
                when(friendshipRequestRepository.save(ArgumentMatchers.any(FriendshipRequest.class)))
                                .thenReturn(friendshipRequest);
                FriendshipRequestDto acceptedRequest = friendshipService
                                .acceptFriendshipRequest(friendshipRequest.getId());
                Assertions.assertEquals(friendshipRequest.getId(), acceptedRequest.getId());
                Assertions.assertEquals(true, friendshipRequest.getStatusOfRequest() == FriendshipStatus.ACCEPTED);
        }

        @Test
        void respondToFriendshipRequest_WhenDecliningRequest_UpdatesRequestStatus() {
                FriendshipRequest friendshipRequest = FriendshipRequest.createFriendshipRequest(testUser1,
                                requestedFriendOfUser1);
                try {
                        Field idFieldRequest = FriendshipRequest.class.getDeclaredField("id");
                        idFieldRequest.setAccessible(true);
                        idFieldRequest.set(friendshipRequest, 3L);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                }
                when(friendshipRequestRepository.findById(friendshipRequest.getId()))
                                .thenReturn(Optional.ofNullable(friendshipRequest));
                when(friendshipRequestRepository.save(ArgumentMatchers.any(FriendshipRequest.class)))
                                .thenReturn(friendshipRequest);
                FriendshipRequestDto declinedRequest = friendshipService
                                .declineFriendshipRequest(friendshipRequest.getId());
                Assertions.assertEquals(friendshipRequest.getId(), declinedRequest.getId());
                Assertions.assertEquals(true, friendshipRequest.getStatusOfRequest() == FriendshipStatus.DECLINED);
        }
}