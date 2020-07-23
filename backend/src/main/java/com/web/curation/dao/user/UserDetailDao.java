
package com.web.curation.dao.user;


import com.web.curation.model.user.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailDao extends JpaRepository<UserDetail, String> {

}
