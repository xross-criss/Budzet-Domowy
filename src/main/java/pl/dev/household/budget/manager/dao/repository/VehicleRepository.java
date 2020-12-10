package pl.dev.household.budget.manager.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dev.household.budget.manager.dao.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    public Optional<Vehicle> findById(Integer vehicleId);

    public Optional<List<Vehicle>> findVehiclesByUserId(Integer userId);

}
