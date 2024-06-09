package online.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Author;
import online.library.entities.Permission;
import online.library.entities.Role;

public interface PermissionRepository  extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

	List<Permission> findByRole(Role role);

}
