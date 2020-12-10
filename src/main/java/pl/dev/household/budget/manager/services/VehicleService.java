package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.User;
import pl.dev.household.budget.manager.dao.Vehicle;
import pl.dev.household.budget.manager.dao.repository.UserRepository;
import pl.dev.household.budget.manager.dao.repository.VehicleRepository;
import pl.dev.household.budget.manager.domain.VehicleDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class VehicleService {

    private ModelMapper modelMapper;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;

    public VehicleService(ModelMapper modelMapper, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public VehicleDTO getVehicleDTOById(Integer vehicleId) {
        return modelMapper.map(getVehicleById(vehicleId), VehicleDTO.class);
    }

    public Vehicle getVehicleById(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId).orElse(null);
    }

    public VehicleDTO saveVehicle(VehicleDTO dto) {
        return modelMapper.map(vehicleRepository.save(modelMapper.map(dto, Vehicle.class)), VehicleDTO.class);
    }

    public void deleteVehicle(Integer vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    public List<VehicleDTO> getVehiclesList(Integer householdId) throws Exception {
        List<User> usersInHousehold = userRepository.findAllByHousehold_Id(householdId).orElse(Collections.emptyList());

        if (usersInHousehold.isEmpty()) {
            throw new Exception("Household not found!");
        }

        List<VehicleDTO> vehiclesInHousehold = new ArrayList<>();

        usersInHousehold.forEach(user -> {
            List<Vehicle> vehiclesTmp = vehicleRepository.findVehiclesByUserId(user.getId()).orElse(Collections.emptyList());
            if (!vehiclesTmp.isEmpty()) {
                vehiclesInHousehold.addAll(modelMapper.map(vehiclesTmp, new TypeToken<List<VehicleDTO>>() {
                }.getType()));
            }
        });

        return vehiclesInHousehold;
    }

    public void updateVehicle(VehicleDTO vehicleDTO) throws Exception {
        Vehicle vehicleTmp = vehicleRepository.findById(vehicleDTO.getId()).orElse(null);

        if (vehicleTmp == null) {
            throw new Exception("Vehicle not found!");
        }

        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        vehicle.setUser(vehicleTmp.getUser());

        vehicleRepository.save(vehicle);
    }
}
