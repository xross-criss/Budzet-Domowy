package pl.dev.household.budget.manager.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import pl.dev.household.budget.manager.dao.Household;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.repository.HouseholdRepository;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dictionaries.BalanceMapType;
import pl.dev.household.budget.manager.dictionaries.UserRole;
import pl.dev.household.budget.manager.domain.BalanceDTO;
import pl.dev.household.budget.manager.domain.HouseholdDTO;
import pl.dev.household.budget.manager.domain.UserDTO;
import pl.dev.household.budget.manager.utils.MockDataUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseholdRepository householdRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BankService bankService;

    @InjectMocks
    private UserService userService;

    private Household household;
    private User user;

    @Before
    public void setup() {
        household = MockDataUtil.initHousehold();
        user = MockDataUtil.initUserMock(household);
    }

    @Test
    public void getUserDetails_givenServices_shouldReturnWithoutVisiblePassword() throws Exception {
        //given

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserDetails(1);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getPassword()).isEqualTo("********");
        assertThat(result.getUserRole()).isEqualTo(UserRole.FAMILY_GUY);

    }

    @Test
    public void getUser_givenServices_shouldReturnWithVisiblePasswordSuccess() throws Exception {
        //given

        //when
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserDTO result = userService.getUser(1);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getPassword()).isEqualTo("test");
        assertThat(result.getUserRole()).isEqualTo(UserRole.FAMILY_GUY);
    }

}
