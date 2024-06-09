package online.library.shiroConfiguration;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import online.library.entities.Permission;
import online.library.entities.User;
import online.library.repositories.PermissionRepository;
import online.library.repositories.RoleRepository;
import online.library.repositories.UserRepository;


public class CustomJDBCRealm extends JdbcRealm {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PermissionRepository permissionRepository;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uToken = (UsernamePasswordToken) token;

		userRepository.findUserByUsernameAndPassword(uToken.getUsername(), new String(uToken.getPassword()));
		
		return new SimpleAuthenticationInfo(uToken.getUsername(), new String(uToken.getPassword()), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//logger.info("doGetAuthorizationInfo+"+principals.toString());
		User user = userRepository.findUserByUsername((String) principals.getPrimaryPrincipal());

	    SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(user.getRole().getRoleName());
		for(Permission permission:permissionRepository.findByRole(user.getRole())){
			info.addStringPermission(permission.getPermission());
  }
		return info;
		

	}

}
