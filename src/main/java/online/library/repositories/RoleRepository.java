package online.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Role;

public interface RoleRepository  extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	Role findRoleByRoleName(String roleName);

}
