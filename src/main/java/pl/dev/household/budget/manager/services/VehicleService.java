package pl.dev.household.budget.manager.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.dev.household.budget.manager.dao.Vehicle;
import pl.dev.household.budget.manager.dao.repository.VehicleRepository;
import pl.dev.household.budget.manager.domain.VehicleDTO;

@Slf4j
@Service
public class VehicleService {

    private ModelMapper modelMapper;
    private VehicleRepository vehicleRepository;

    public VehicleService(ModelMapper modelMapper, VehicleRepository vehicleRepository) {
        this.modelMapper = modelMapper;
        this.vehicleRepository = vehicleRepository;
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

}
